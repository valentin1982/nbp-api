package pl.nbp.api.util.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.apache.log4j.Logger;
import org.apache.poi.impl.workbook.WorkbookContextFactory;
import pl.nbp.api.beans.Currency;
import pl.nbp.api.beans.ExchangeRates;
import pl.nbp.api.database.DBService;
import pl.nbp.api.domain.Rate;
import pl.nbp.api.exeption.NbpException;
import pl.nbp.api.util.create_excel.FileWriterUtil;
import pl.nbp.api.util.ShowDescUtil;
import pl.nbp.api.util.url.URLForParsersUtil;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonExchangeRatesParser {

    private Logger logger = Logger.getLogger(JsonExchangeRatesParser.class);

    private final String currency;

    private final LocalDate from;

    private final LocalDate to;

    private final ExchangeRates exchangeRates = new ExchangeRates();

    private DBService service = new DBService();

    private final WorkbookContextFactory ctxFactory;

    /**
     *
     * Construct a Parser object. If find any incorrect in dates throws an
     * exception.
     *
     * @param ctxFactory
     * @param currency
     * @param from
     * @param to
     * @throws NbpException
     */
    public JsonExchangeRatesParser(WorkbookContextFactory ctxFactory, String currency, LocalDate from, LocalDate to) throws NbpException {
        this.ctxFactory = ctxFactory;

        if (currency == null || currency.isEmpty()) {
            throw new NbpException("Currency code can not be empty.");
        }

        if (from == null || to == null) {
            throw new NbpException("Dates can not be empty.");
        }

        if (!from.isBefore(to)) {
            throw new NbpException(" The 'From' date must be before the 'to' date.");
        }

        this.currency = currency;
        this.from = from;
        this.to = to;
    }

    /**
     * Parsing a json data downloaded from <i>api.nbe.pl</i> service.
     *
     * @throws Exception
     */
    public void jsonParse() throws Exception {
        // prepare an url
        String _url = getUrlAndDate();
        JsonFactory jsonFactory = new JsonFactory();
        // get data stream from url
        try {
            logger.info("Retrieve an url: " + _url);
            JsonParser jsonParser = jsonFactory.createJsonParser(new URL(_url));
            // A currency result object
            Currency curr = new Currency();
            // Ratings list object
            List<Rate> rates = new ArrayList<>();
            SaveToJsonUtil.readAndSaveJson(jsonParser, curr, rates);
            ShowDescUtil.showDesc(rates);
            FileWriterUtil.writeToFleExel(rates);
            logger.info("JsonExchangeRatesParser create exel " + rates);
            exchangeRates.setCurrency(curr);
            exchangeRates.setRates(rates);
            System.out.println(exchangeRates);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NbpException("Found an error while parsing data: " + e.getMessage());
        }
    }

    /**
     * Prepare Date snd get URL from Json parser
     *
     * @return
     */
    private String getUrlAndDate() {
        return URLForParsersUtil.URL_JSON.replaceAll("_CURRENCY_", currency)
                .replaceAll("_FROM_", from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replaceAll("_TO_", to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public ExchangeRates getExchangeRates() {
        return exchangeRates;
    }
}
