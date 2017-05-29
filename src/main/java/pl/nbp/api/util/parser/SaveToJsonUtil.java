package pl.nbp.api.util.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.log4j.Logger;
import pl.nbp.api.beans.Currency;
import pl.nbp.api.database.DBService;
import pl.nbp.api.domain.Rate;
import pl.nbp.api.util.date.DateHelperUtil;

import java.util.List;

public class SaveToJsonUtil {

    private static Logger logger = Logger.getLogger(SaveToJsonUtil.class);

    private static DBService service = new DBService();

    public static void readAndSaveJson(JsonParser jsonParser, Currency curr, List<Rate> rates) throws Exception {
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            // As first parsed are a currency information
            String currentName = jsonParser.getCurrentName();
            if ("currency".equals(currentName)) {
                jsonParser.nextToken();
                curr.setName(jsonParser.getText());
            } else if ("code".equals(currentName)) {
                jsonParser.nextToken();
                curr.setCode(jsonParser.getText());
            }if ("rates".equals(currentName)) {
                // Starts a rates list
                jsonParser.nextToken();
                // Current rating object
                Rate rate = new Rate();
                // iterate
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    // actual rate field name
                    currentName = jsonParser.getCurrentName();
                    if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
                        // start rate object
                        rate = new Rate();
                    } else if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
                        // end rate
                        rates.add(rate);
                    } else {
                        // adding currency data
                        if ("effectiveDate".equals(currentName)) {
                            jsonParser.nextToken();
                            rate.setEffectivedate(DateHelperUtil.toLocalDate(jsonParser.getText()));
                        } else if ("mid".equals(currentName)) {
                            jsonParser.nextToken();
                            rate.setMid(Double.parseDouble(jsonParser.getText()));
                            logger.info("effectiveDate = " + rate.getEffectivedate() + "mid = " + rate.getMid());
                            service.addAll(rate.getEffectivedate(), rate.getMid());
                        }
                    }
                }
            }
        }
    }

}
