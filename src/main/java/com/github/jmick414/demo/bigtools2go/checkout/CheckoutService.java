package com.github.jmick414.demo.bigtools2go.checkout;

import com.github.jmick414.demo.bigtools2go.tool.Tool;
import com.github.jmick414.demo.bigtools2go.tool.ToolService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class CheckoutService {
    private final ToolService toolService;

    public CheckoutService(ToolService toolService) {
        this.toolService = toolService;
    }

    public Tool getTool(String toolCode) {
        return toolCode == null ? null : toolService.getToolByCode(toolCode);
    }

    public int getRentalDays(String input) {
        int rentalDays;
        try {
            rentalDays = Integer.parseInt(input);
        } catch(NumberFormatException nfe) {
            throw new IllegalArgumentException("Rental days should be a number.");
        }

        if(rentalDays < 1) {
            throw new IllegalArgumentException("Rental days must be >= 1.");
        }

        return rentalDays;
    }

    public int getDiscountPercent(String input) {
        int discountPercent;
        try {
            discountPercent = Integer.parseInt(input);
        } catch(NumberFormatException nfe) {
            throw new IllegalArgumentException("Discount percent should be a number.");
        }

        if(discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Invalid discount percent value; must be in the range of 0-100.");
        }

        return discountPercent;
    }

    public LocalDate getCheckoutDate(String input) {
        if(input == null) {
            throw new IllegalArgumentException(String.format("Unrecognized date \"%s\". Please enter date in M/D/YY format.", input));
        }

        try {
            return LocalDate.parse(input, DateTimeFormatter.ofPattern("M/d/yy"));
        } catch(DateTimeParseException dtpe) {
            throw new IllegalArgumentException(String.format("Unrecognized date \"%s\". Please enter date in M/D/YY format.", input));
        }
    }

}
