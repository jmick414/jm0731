package com.github.jmick414.demo.bigtools2go.checkout;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HolidayUtilTest {

    @Test
    void testIsHoliday() {
        // Known (Observed) Independence Days
        // Map key=year, value=day Independence Day is observed (month July is assumed)
        Map<Integer, Integer> observedIndependenceDay = Map.of(
                2020, 3,
                2021, 5,
                2022, 4,
                2023, 4,
                2024, 4,
                2025, 4,
                2026, 3,
                2027, 5,
                2028, 4
        );
        observedIndependenceDay.forEach((year, day) -> {
            for(int dayOfMonth = 1; dayOfMonth <= Month.JULY.length(false); dayOfMonth++) {
                assertEquals(dayOfMonth == day, HolidayUtil.isHoliday(LocalDate.of(year, Month.JULY, dayOfMonth)));
            }
        });

        // Known Labor Days
        // Map key=year, value=day of Labor Day (month September is assumed)
        Map<Integer,Integer> laborDay = Map.of(
                2020, 7,
                2021, 6,
                2022, 5,
                2023, 4,
                2024, 2,
                2025, 1,
                2026, 7,
                2027, 6,
                2028, 4
        );
        laborDay.forEach((year, day) -> {
            for(int dayOfMonth = 1; dayOfMonth <= Month.SEPTEMBER.length(false); dayOfMonth++) {
                assertEquals(dayOfMonth == day, HolidayUtil.isHoliday(LocalDate.of(year, Month.SEPTEMBER, dayOfMonth)));
            }
        });

        List<Month> monthsWithoutHolidays = new LinkedList<Month>(Arrays.asList(Month.values()));
        monthsWithoutHolidays.remove(Month.JULY);
        monthsWithoutHolidays.remove(Month.SEPTEMBER);

        for(int year = 1990; year <= GregorianCalendar.getInstance().get(Calendar.YEAR); year++) {
            final Year finalYear = Year.of(year);
            monthsWithoutHolidays.forEach(month -> {
                assertFalse(HolidayUtil.isHoliday(LocalDate.of(finalYear.getValue(), month, month.length(finalYear.isLeap()))));
            });
        }
    }

}
