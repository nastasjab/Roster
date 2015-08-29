package lv.javaguru.java2.domain;

import java.sql.Date;
import java.time.LocalDate;

public class Dates {

    public static long toEpochDay(Date date) {
        return LocalDate.parse(date.toString()).toEpochDay();
    }

    public static Date toSqlDate(long epochDay) {
        return Date.valueOf(LocalDate.ofEpochDay(epochDay));
    }

    public static int getDayOfMonth(long epochDay) {
        return LocalDate.ofEpochDay(epochDay).getDayOfMonth();
    }
}
