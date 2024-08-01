package com.github.jmick414.demo.bigtools2go.checkout;

import com.github.jmick414.demo.bigtools2go.tool.Tool;
import com.github.jmick414.demo.bigtools2go.tool.ToolType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

class RentalAgreementTest {

    @Test
    void test() {
        Tool chainsaw = new Tool();
        chainsaw.setToolCode("CHNS");
        chainsaw.setBrand("Stihl");

        ToolType toolType = new ToolType();
        toolType.setDescription("Chainsaw");
        toolType.setDailyChargeAmount(new BigDecimal("3.99"));

        chainsaw.setToolType(toolType);

        Checkout invalidCheckout = CheckoutBuilder.newBuilder(chainsaw)
                .rentalDays(1)
                .checkoutDate(LocalDate.now(ZoneId.systemDefault()))
                .discountPercent(50)
                .build();
        RentalAgreement ra = new RentalAgreement(invalidCheckout);

        System.out.println(ra.getSummary());
    }
}
