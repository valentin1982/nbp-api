package pl.nbp.api.util;

import pl.nbp.api.domain.Rate;

import java.util.Comparator;
import java.util.List;

public class ShowDescUtil {
    /**
     * sort descending by Date
     *
     * @param rates
     */
    public static void showDesc(List<Rate> rates) {
        rates.sort(Comparator.comparing(Rate::getEffectivedate, Comparator.reverseOrder()));
    }

}
