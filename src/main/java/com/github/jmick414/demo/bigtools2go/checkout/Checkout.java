package com.github.jmick414.demo.bigtools2go.checkout;

import com.github.jmick414.demo.bigtools2go.tool.Tool;

import java.time.LocalDate;

public final class Checkout {

    private Tool tool;
    private int rentalDays;
    private int discountPercent;
    private LocalDate checkoutDate;

    Checkout(CheckoutBuilder builder) {
        this.tool = builder.tool;
        this.rentalDays = builder.rentalDays;
        this.discountPercent = builder.discountPercent;
        this.checkoutDate = builder.checkoutDate;
    }

    public static CheckoutBuilder newBuilder(Tool tool) {
        return new CheckoutBuilder(tool);
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

}
