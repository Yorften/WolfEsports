package app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static LocalDate parseDate(String date) throws Exception {
        LocalDate now = LocalDate.now();

        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (parsedDate.isAfter(now)) {
            throw new Exception("The date provided is not valid please input a date before : " + now);
        }
        return parsedDate;
    }
}
