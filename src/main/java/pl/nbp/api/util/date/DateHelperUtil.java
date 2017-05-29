package pl.nbp.api.util.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelperUtil {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final LocalDate LOCAL_DATE = LocalDate.now();

    /**
     * Parsing result date to LocalDate object.
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(String date) {

        String[] d = date.split("-");
        return LocalDate.of(
                Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2])
        );

    }



}
