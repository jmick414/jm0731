package com.github.jmick414.demo.bigtools2go.checkout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Predicate;

public class HolidayUtil {

    private HolidayUtil() {
        // Utility class not mean to be initialized
    }

    private static final Predicate<LocalDate> INDEPENDENCE_DAY_PREDICATE = (LocalDate date) -> {
        if(date.getMonth() != Month.JULY || date.getDayOfMonth() < 3 || date.getDayOfMonth() > 5) {
            return false;
        }

        Calendar cal = new GregorianCalendar(date.getYear(), Calendar.JULY, 4);
        return switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY -> date.getDayOfMonth() == 3;
            case Calendar.SUNDAY -> date.getDayOfMonth() == 5;
            default -> date.getDayOfMonth() == 4;
        };
    };

    private static final Predicate<LocalDate> LABOR_DAY_PREDICATE = (LocalDate date) -> {
        if(date.getMonth() != Month.SEPTEMBER && date.getDayOfWeek() != DayOfWeek.MONDAY) {
            return false;
        }

        Calendar cal = new GregorianCalendar(date.getYear(), Calendar.SEPTEMBER, 1);
        while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            cal.roll(Calendar.DAY_OF_MONTH, true);
        }

        return cal.get(Calendar.DAY_OF_MONTH) == date.getDayOfMonth();
    };

    public static boolean isHoliday(LocalDate date) {
        return INDEPENDENCE_DAY_PREDICATE.test(date) || LABOR_DAY_PREDICATE.test(date);
    }
}
