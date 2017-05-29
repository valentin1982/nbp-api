package pl.nbp.api.abstractparser;

import org.apache.log4j.Logger;
import org.apache.poi.api.workbook.WorkbookContext;
import org.apache.poi.impl.workbook.WorkbookContextFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.nbp.api.beans.Currency;
import pl.nbp.api.beans.ExchangeRates;
import pl.nbp.api.database.DBService;
import pl.nbp.api.domain.Rate;
import pl.nbp.api.exeption.NbpException;
import pl.nbp.api.util.url.URLForParsersUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class SaxParser extends DefaultHandler {

    private static final Logger logger = Logger.getLogger(SaxParser.class);

    private Rate rate;

    private List<Rate> rates;

    private WorkbookContext workbookCtx;

    private Currency curr;

    protected WorkbookContextFactory ctxFactory ;

    protected String currency ;

    protected LocalDate from ;

    protected LocalDate to ;

    private final ExchangeRates exchangeRates = new ExchangeRates();

    protected boolean no = false;

    protected boolean effectiveDate = false;

    protected boolean name = false;

    protected boolean code = false;

    protected boolean mid = false;

    private Date today;

    private DBService service = new DBService();

    /**
     *
     * @param ctxFactory
     * @param currency
     * @param from
     * @param to
     * @throws NbpException
     */
    public SaxParser(WorkbookContextFactory ctxFactory, String currency, LocalDate from, LocalDate to) throws NbpException {

        checkValidationCurrensy(currency, from, to);

        this.ctxFactory = ctxFactory;
        this.currency = currency;
        this.from = from;
        this.to = to;
    }

    /**
     *
     * @param currency
     * @param from
     * @param to
     * @throws NbpException
     */
    public SaxParser(String currency, LocalDate from, LocalDate to) throws NbpException {

        checkValidationCurrensy(currency, from, to);

        this.currency = currency;
        this.from = from;
        this.to = to;

    }

    /**
     *
     * Construct a Sax Parser objects. If find any incorrect in dates throws an
     * exception.
     *
     * @param currency
     * @param from
     * @param to
     * @throws NbpException
     */
    private void checkValidationCurrensy(String currency, LocalDate from, LocalDate to) throws NbpException {
        if (currency == null || currency.isEmpty()) {
            throw new NbpException("Currency code can not be empty.");

        }

        if (from == null || to == null) {
            throw new NbpException("Dates can not be empty.");
        }

        if (!from.isBefore(to)) {
            throw new NbpException(" The 'From' date must be before the 'to' date.");
        }
    }

    /**
     *
     * @return
     */
    protected String getUrlAndDateFromXml() {
        return URLForParsersUtil.URL_SAX.replaceAll("_CURRENCY_", currency)
                .replaceAll("_FROM_", from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replaceAll("_TO_", to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        today = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
        String date = dateFormat.format(today);
        logger.info("start parse XML... " + date);
        super.startDocument();
    }

    /**
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Currency")) {
            curr = new Currency();
            name = true;
        } else if (qName.equalsIgnoreCase("Code")) {
            code = true;
        }
        if (qName.equalsIgnoreCase("Rate")) {
            rate = new Rate();
            if (rates == null)
                rates = new ArrayList<>();

        } else if (qName.equalsIgnoreCase("No")) {
            no = true;
        } else if (qName.equalsIgnoreCase("EffectiveDate")) {
            effectiveDate = true;
        } else if (qName.equalsIgnoreCase("Mid")) {
            mid = true;
        }

    }

    /**
     *
     * parse date from <i>api.nbe.pl</i> service.
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (name) {
            curr.setName(new String(ch, start, length));
            name = false;
        } else if (code) {
            curr.setCode(new String(ch, start, length));
            code = false;
        } else if (effectiveDate) {
            rate.setEffectivedate(LocalDate.parse(new String(ch, start, length)));
            effectiveDate = false;
        } else if (mid) {
            rate.setMid(Double.parseDouble(new String(ch, start, length)));
            mid = false;
        }

    }

    /**
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Rate")) {
            rates.add(rate);
        }

    }

    protected abstract void parse();

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Currency getCurr() {
        return curr;
    }

    public void setCurr(Currency curr) {
        this.curr = curr;
    }

    public ExchangeRates getExchangeRates() {
        return exchangeRates;
    }
}
