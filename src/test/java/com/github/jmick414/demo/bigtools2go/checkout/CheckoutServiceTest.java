package com.github.jmick414.demo.bigtools2go.checkout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CheckoutServiceTest {

    // Expected exception messages
    private static final String RENTAL_DAYS_SHOULD_BE_A_NUMBER = "Rental days should be a number.";
    private static final String RENTAL_DAYS_MUST_BE_ONE_OR_GREATER = "Rental days must be >= 1.";
    private static final String DISCOUNT_PERCENT_SHOULD_BE_A_NUMBER = "Discount percent should be a number.";
    private static final String DISCOUNT_PERCENT_MUST_BE_IN_RANGE_0_TO_100 = "Invalid discount percent value; must be in the range of 0-100.";
    private static final String UNPARSEABLE_DATE = "Unrecognized date \"%s\". Please enter date in M/D/YY format.";

    @Autowired
    CheckoutService checkoutService;


    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "ZZZZ", "Invalid tool code"})
    void testGetToolReturnsNullWhenInvalidToolCodeSupplied(String toolCode) {
        assertNull(checkoutService.getTool(toolCode));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "ABC", "Not a number"})
    void testGetRentalDaysThrowsExceptionWhenInputIsNullOrEmptyOrNotANumber(String input) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.getRentalDays(input);
        });

        assertTrue(RENTAL_DAYS_SHOULD_BE_A_NUMBER.equals(exception.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-3", "-1", "0"})
    void testGetRentalDaysThrowsExceptionWhenInputIsLessThanOne(String input) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.getRentalDays(input);
        });

        assertTrue(RENTAL_DAYS_MUST_BE_ONE_OR_GREATER.equals(exception.getMessage()));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "ABC", "Not a number"})
    void testGetDiscountPercentThrowsExceptionWhenInputIsNullOrEmptyOrNotANumber(String input) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.getDiscountPercent(input);
        });

        assertTrue(DISCOUNT_PERCENT_SHOULD_BE_A_NUMBER.equals(exception.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-3", "-1", "101", "202"})
    void testGetDiscountPercentThrowsExceptionWhenInputOutOfRange(String input) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.getDiscountPercent(input);
        });

        assertTrue(DISCOUNT_PERCENT_MUST_BE_IN_RANGE_0_TO_100.equals(exception.getMessage()));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "ABC", "Not a date" , "01/01/1970"})
    void testGetCheckoutDateThrowsExceptionWhenUnparseable(String input) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.getCheckoutDate(input);
        });

        assertTrue(String.format(UNPARSEABLE_DATE, input).equals(exception.getMessage()));
    }

    @Test
    void testGetCheckoutDate() {
        LocalDate actual = checkoutService.getCheckoutDate("07/31/24");
        assertEquals(Month.JULY, actual.getMonth());
        assertEquals(31, actual.getDayOfMonth());
        assertEquals(2024, actual.getYear());
    }
}
