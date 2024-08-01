package com.github.jmick414.demo.bigtools2go.checkout;

import com.github.jmick414.demo.bigtools2go.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
public class CheckoutRunner {

    private final Scanner scanner = new Scanner(System.in);

    private final CheckoutService checkoutService;

    public CheckoutRunner(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    public void run() {
        while(true) {
            Checkout checkout = checkout();
            RentalAgreement rentalAgreement = new RentalAgreement(checkout);
            System.out.println(rentalAgreement.getSummary());
            System.out.println("----------------------------------------");
        }
    }

    private Checkout checkout() {
        Tool tool;
        int rentalDays = -1;
        int discountPercent = -1;
        LocalDate checkoutDate = null;

        do {
            System.out.print("Tool code: ");
            tool = checkoutService.getTool(scanner.nextLine());
            if(tool == null) {
                System.out.println("Unrecognized tool code!");
            }
        } while(tool == null);

        do {
            System.out.print("Rental days: ");
            try {
                rentalDays = checkoutService.getRentalDays(scanner.nextLine());
            } catch(IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        } while(rentalDays < 1);

        do {
            System.out.print("Discount percent: ");
            try {
                discountPercent = checkoutService.getDiscountPercent(scanner.nextLine());
            } catch(IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        } while(discountPercent < 0);

        do {
            System.out.print("Checkout date: ");
            try {
                checkoutDate = checkoutService.getCheckoutDate(scanner.nextLine());
            } catch(IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        } while(checkoutDate == null);

        System.out.println();

        return Checkout.newBuilder(tool)
            .rentalDays(rentalDays)
            .discountPercent(discountPercent)
            .checkoutDate(checkoutDate)
            .build();
    }
}
