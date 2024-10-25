package app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static LocalDate parseDate(String date) throws Exception {
        LocalDate now = LocalDate.now();

        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (parsedDate.isBefore(now)) {
            throw new Exception("The date provided is not valid please input a date before : " + now);
        }
        return parsedDate;
    }
}
