package com.github.jmick414.demo.bigtools2go.checkout;

import com.github.jmick414.demo.bigtools2go.tool.Tool;

import java.time.LocalDate;

public final class CheckoutBuilder {
    Tool tool;
    int rentalDays;
    int discountPercent;
    LocalDate checkoutDate;

    CheckoutBuilder(Tool tool) {
        this.tool = tool;
    }

    public static CheckoutBuilder newBuilder(Tool tool) {
        return new CheckoutBuilder(tool);
    }

    public CheckoutBuilder rentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
        return this;
    }

    public CheckoutBuilder discountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public CheckoutBuilder checkoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
        return this;
    }

    public Checkout build() {
        return new Checkout(this);
    }
}
