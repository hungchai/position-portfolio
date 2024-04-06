package com.option.portfolio.portfolio;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

final public class Util {
    public static Instant convertMaturityStrToInstant(String maturityStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy", Locale.ENGLISH);
        //it can parse Oct-2020 format only
        YearMonth yearMonth = YearMonth.parse(maturityStr.substring(0, 1).toUpperCase() + maturityStr.substring(1).toLowerCase(), formatter);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        // Convert to Instant object representing the end of the month
        return endOfMonth.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant();
    }
}
