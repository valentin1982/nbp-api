package pl.nbp.api.util.parser;

import org.apache.log4j.Logger;
import org.apache.poi.impl.workbook.WorkbookContextFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.nbp.api.abstractparser.SaxParser;
import pl.nbp.api.beans.Currency;
import pl.nbp.api.beans.ExchangeRates;
import pl.nbp.api.database.DBService;
import pl.nbp.api.domain.Rate;
import pl.nbp.api.exeption.NbpException;
import pl.nbp.api.util.ShowDescUtil;
import pl.nbp.api.util.create_excel.FileWriterUtil;
import pl.nbp.api.util.date.DateHelperUtil;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class SaxExchangeRatesSaxParser extends SaxParser {

    private static final Logger logger = Logger.getLogger(SaxExchangeRatesSaxParser.class);

    private DBService service = new DBService();

    /**
     *
     *
     * @param ctxFactory
     * @param currency
     * @param from
     * @param to
     * @throws NbpException
     */
    public SaxExchangeRatesSaxParser(WorkbookContextFactory ctxFactory, String currency, LocalDate from, LocalDate to) throws NbpException {

        super(ctxFactory, currency, from, to);

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
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
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
        super.endElement(uri, localName, qName);
    }

    /**
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    private final ExchangeRates exchangeRates = new ExchangeRates();

    /**
     * Parsing a xml data downloaded from <i>api.nbe.pl</i> service.
     */
    @Override
    public void parse() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            javax.xml.parsers.SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxExchangeRatesSaxParser handler = new SaxExchangeRatesSaxParser(ctxFactory, currency, from, to);
            String _url = getUrlAndDateFromXml();
            saxParser.parse(new InputSource(_url), handler);
            List<Rate> rates = handler.getRates();
            Currency curr = handler.getCurr();
            logger.info("Retrieve an url: " + _url);
            for (Rate rate : rates) {
                service.addAll(rate.getEffectivedate(), rate.getMid());
                service.getData();
                logger.info(DateHelperUtil.LOCAL_DATE + " " + " effectiveDate = " + rate.getEffectivedate() + " mid = " + rate.getMid());

            }
            ShowDescUtil.showDesc(rates);
            FileWriterUtil.writeToFleExel(rates);
            exchangeRates.setCurrency(curr);
            exchangeRates.setRates(rates);
        } catch (ParserConfigurationException | SAXException | IOException | NbpException e) {
            logger.error("ParserConfigurationException : " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Parser Exception : " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public ExchangeRates getExchangeRates() {
        return exchangeRates;
    }

}
