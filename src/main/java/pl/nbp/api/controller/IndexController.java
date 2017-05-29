package pl.nbp.api.controller;

import org.apache.poi.impl.workbook.WorkbookContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.nbp.api.beans.ExchangeRates;
import pl.nbp.api.dao.JsonExchangeRatesDAO;
import pl.nbp.api.beans.Chart;
import pl.nbp.api.dao.SaxExchangeRatesDAO;
import pl.nbp.api.exeption.NbpException;
import pl.nbp.api.util.date.DateHelperUtil;
import pl.nbp.api.util.UserSessionUtil;
import pl.nbp.api.util.parser.JsonExchangeRatesParser;
import pl.nbp.api.util.parser.SaxExchangeRatesSaxParser;
import pl.nbp.api.view.IndexForm;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;


/**
 *
 */
@Controller
public class IndexController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(IndexController.class);

    public static final int MONTHS_TO_SUBTRACT = 3;

    public static final String USD = "USD";

    @Autowired
    private UserSessionUtil userSessionUtil;

    /**
     *
     * @param indexForm
     * @param br
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String indexPost(@Valid IndexForm indexForm, BindingResult br, Model model) throws Exception {
        long lStartTime = getlStartTime();
        prepareDefaults(indexForm);
        if (br.hasErrors()) {
            logger.error("FOUND ERRORS: " + br.getErrorCount());
            return "index";
        }
        updateSession(indexForm);
        fillDataFromNBP_API(indexForm, model);
        long result = getEndTime(lStartTime);
        logger.info("indexPost(); in seconds: " + result);
        return "index";
    }

    /**
     *
     * @param indexForm
     * @param br
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexGet(@Valid IndexForm indexForm, BindingResult br, Model model) throws Exception {
        long lStartTime = getlStartTime();
        prepareDefaults(indexForm);
        updateSession(indexForm);
        fillDataFromNBP_API(indexForm, model);
        long result = getEndTime(lStartTime);
        logger.info("indexGet(); in seconds: " + result);
        return "index";
    }

    /**
     *
     * @param indexForm
     * @param br
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public String saveToDatabase(@Valid IndexForm indexForm, BindingResult br, Model model) throws Exception {
        long lStartTime = getlStartTime();
        prepareDefaults(indexForm);
        if (br.hasErrors()) {
            System.out.println("FOUND ERRORS: " + br.getErrorCount());
            return "index";
        }
        updateSession(indexForm);
        fillDataToDatabaseFromNBP_API(indexForm, model);
        long result = getEndTime(lStartTime);
        logger.info("saveToDatabase(); in seconds: " + result);
        return "index";
    }

    /**
     *
     * @param indexForm
     * @param model
     * @throws Exception
     */
    private void fillDataFromNBP_API(@Valid IndexForm indexForm, Model model) throws Exception {
        ExchangeRates exchangeRates;
        try {
            exchangeRates = getCurrencyexchangeRates(
                    indexForm.getCountry(),
                    indexForm.getListingDateFrom(),
                    indexForm.getListingDateTo());
            // Save model
            toModel(
                    model, exchangeRates,
                    indexForm.getListingDateFrom().format(DateHelperUtil.TIME_FORMATTER),
                    indexForm.getListingDateTo().format(DateHelperUtil.TIME_FORMATTER),
                    indexForm.getCountry());
        } catch (NbpException e) {
            model.addAttribute("country", indexForm.getCountry());
            model.addAttribute("error", e.getMessage());
        }
    }

    /**
     *
     * @param indexForm
     * @param model
     * @throws Exception
     */
    private void fillDataToDatabaseFromNBP_API(@Valid IndexForm indexForm, Model model) throws Exception {
        ExchangeRates exchangeRates;
        try {
            exchangeRates = getCurrencyexchangeRatesToSaveInDatabase(
                    indexForm.getCountry(),
                    indexForm.getListingDateFrom(),
                    indexForm.getListingDateTo());
            // Save model
            toModel(
                    model, exchangeRates,
                    indexForm.getListingDateFrom().format(DateHelperUtil.TIME_FORMATTER),
                    indexForm.getListingDateTo().format(DateHelperUtil.TIME_FORMATTER),
                    indexForm.getCountry());
        } catch (NbpException e) {
            model.addAttribute("country", indexForm.getCountry());
            model.addAttribute("error", e.getMessage());
        }
    }

    /**
     * Preparing a defaults for form. If form values are null method search defaults in
     * session, if session is null too method creates default values.
     * @param indexForm
     */
    private void prepareDefaults(IndexForm indexForm) {
        if (indexForm.getListingDateFrom() == null) {
            if (userSessionUtil.getDateFrom() != null)
                indexForm.setListingDateFrom(userSessionUtil.getDateFrom());
            else indexForm.setListingDateFrom(LocalDate.now().minusMonths(MONTHS_TO_SUBTRACT));
        }
        if (indexForm.getListingDateTo() == null) {
            if (userSessionUtil.getDateTo() != null)
                indexForm.setListingDateTo(userSessionUtil.getDateTo());
            else indexForm.setListingDateTo(LocalDate.now());
        }
        if (indexForm.getCountry() == null) {
            if (userSessionUtil.getCurrency() != null && !userSessionUtil.getCurrency().isEmpty())
                indexForm.setCountry(userSessionUtil.getCurrency());
            else
                indexForm.setCountry(USD);
        }
    }
    /**
     * Method updates a session values to current form values.
     * @param indexForm
     */
    void updateSession(IndexForm indexForm) {
        userSessionUtil.setDateFrom(indexForm.getListingDateFrom());
        userSessionUtil.setDateTo(indexForm.getListingDateTo());
        userSessionUtil.setCurrency(indexForm.getCountry());
    }

    /**
     *
     * @param currency
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    private ExchangeRates getCurrencyexchangeRates(String currency, LocalDate from, LocalDate to) throws Exception {

        // get data from API with xml
        return getExchangeRatesWithXML(currency, from, to);

        //get data from API with Json
        //return getExchangeRatesWithJSON(currency, from, to);
    }

    /**
     *
     * @param currency
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    private ExchangeRates getExchangeRatesWithJSON(String currency, LocalDate from, LocalDate to) throws Exception {
        JsonExchangeRatesParser jsonExchangeRatesParser = new JsonExchangeRatesParser(WorkbookContextFactory.useXls(), currency, from, to);
        jsonExchangeRatesParser.jsonParse();
        return jsonExchangeRatesParser.getExchangeRates();
    }

    /**
     *
     * @param currency
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    private ExchangeRates getExchangeRatesWithXML(String currency, LocalDate from, LocalDate to) throws Exception {
        SaxExchangeRatesSaxParser saxExchangeRatesParser = new SaxExchangeRatesSaxParser(WorkbookContextFactory.useXls(), currency, from, to);
        saxExchangeRatesParser.parse();
        return saxExchangeRatesParser.getExchangeRates();
    }

    /**
     *
     * @param currency
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    private ExchangeRates getCurrencyexchangeRatesToSaveInDatabase(String currency, LocalDate from, LocalDate to) throws Exception {

        return saveWithXML(currency, from, to);

//        return saveWithJSON(currency, from, to);

    }

    /**
     *
     * @param currency
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    private ExchangeRates saveWithXML(String currency, LocalDate from, LocalDate to) throws Exception {
        SaxExchangeRatesDAO ratesDAO = new SaxExchangeRatesDAO(currency, from, to);
        ratesDAO.parse();
        return ratesDAO.getExchangeRates();
    }

    /**
     *
     * @param currency
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    private ExchangeRates saveWithJSON(String currency, LocalDate from, LocalDate to) throws Exception {
        JsonExchangeRatesDAO ratesDAO = new JsonExchangeRatesDAO(currency, from, to);
        ratesDAO.saveToDatabase();
        return ratesDAO.getExchangeRates();
    }

    /**
     * Setting data into model. It is separated from action methods to better manage with attrubites names.
     * @param model
     * @param exchangeRates
     * @param from
     * @param to
     * @param country
     */
    private void toModel(Model model, ExchangeRates exchangeRates, String from, String to, String country) {

        // prepare chart data
        Chart chart = new Chart(exchangeRates);

        // Set model
        model.addAttribute("chartsLabels", chart.getLabels());
        model.addAttribute("chartsDatas", chart.getData());
        model.addAttribute("exchangeRates", exchangeRates);
        model.addAttribute("listingDateFrom", from);
        model.addAttribute("listingDateTo", to);
        model.addAttribute("country", country);
    }

    /**
     *
     * @param lStartTime
     * @return
     */
    private long getEndTime(long lStartTime) {
        long lEndTime = getlStartTime();
        long output = lEndTime - lStartTime;
        return output;
    }

    /**
     *
     * @return
     */
    private long getlStartTime() {
        return new Date().getTime() / 1000;
    }
}
