package pl.nbp.api.parsers;

import org.junit.Assert;
import org.junit.Test;
import pl.nbp.api.dao.SaxExchangeRatesDAO;
import pl.nbp.api.exeption.NbpException;

import java.time.LocalDate;

/**
 * Created by valik on 14.05.2017.
 */
public class SaxParser {


    private LocalDate from = LocalDate.parse("2017-05-01");
    private LocalDate to = LocalDate.now();

    @Test
    public void isData() throws NbpException {
        SaxExchangeRatesDAO ratesDAO = new SaxExchangeRatesDAO("USD", from, to);
        ratesDAO.parse();
        Assert.assertNotNull(ratesDAO);

    }

}
