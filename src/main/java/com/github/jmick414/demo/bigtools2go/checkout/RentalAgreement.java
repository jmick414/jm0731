package com.github.jmick414.demo.bigtools2go.checkout;

import com.github.jmick414.demo.bigtools2go.tool.Tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;

public class RentalAgreement {

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");
    private static final Set<Integer> WEEKEND_DAYS = Set.of(Calendar.SATURDAY, Calendar.SUNDAY);

    private final Checkout checkout;

    private LocalDate dueDate;

    private int chargeDays;

    private BigDecimal preDiscountCharge = BigDecimal.ZERO;

    private BigDecimal discountAmount;

    private BigDecimal finalCharge;

    public RentalAgreement(Checkout checkout) {
        this.checkout = checkout;
        calculate();
    }

    private void calculate() {
        GregorianCalendar calendar = GregorianCalendar.from(checkout.getCheckoutDate().atStartOfDay(ZoneId.systemDefault()));

        boolean holidayCharge = checkout.getTool().getToolType().isHolidayCharge();
        boolean weekendCharge = checkout.getTool().getToolType().isWeekendCharge();
        boolean weekdayCharge = checkout.getTool().getToolType().isWeekdayCharge();

        for(int rentalDay = 0; rentalDay < checkout.getRentalDays(); rentalDay++) {
            calendar.add(Calendar.DATE, 1);

            boolean chargeDay = true;

            if(!holidayCharge && HolidayUtil.isHoliday(LocalDate.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()))) {
                chargeDay = false;
            } else if(!weekendCharge && isWeekend(calendar.get(Calendar.DAY_OF_WEEK))) {
                chargeDay = false;
            } else if(!weekdayCharge && !isWeekend(calendar.get(Calendar.DAY_OF_WEEK))) {
                chargeDay = false;
            }

            if(chargeDay) {
                preDiscountCharge = preDiscountCharge.add(checkout.getTool().getToolType().getDailyChargeAmount());
                chargeDays++;
            }
        }

        dueDate = LocalDate.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());

        discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf((double) checkout.getDiscountPercent()/100)).setScale(2, RoundingMode.HALF_UP);

        finalCharge = preDiscountCharge.subtract(discountAmount);

    }

    private boolean isWeekend(int dayOfWeek) {
        return WEEKEND_DAYS.contains(dayOfWeek);
    }

    public String getSummary() {
        StringBuilder summary = new StringBuilder();

        Tool tool = checkout.getTool();

        appendSummaryLine(summary, "Tool code", tool.getToolCode());
        appendSummaryLine(summary, "Tool type", tool.getToolType().getDescription());
        appendSummaryLine(summary, "Tool brand", tool.getBrand());
        appendSummaryLine(summary, "Rental days", Integer.toString(checkout.getRentalDays()));
        appendSummaryLine(summary, "Checkout date", DATE_TIME_FORMATTER.format(checkout.getCheckoutDate()));
        appendSummaryLine(summary, "Due date", DATE_TIME_FORMATTER.format(dueDate));
        appendSummaryLine(summary, "Daily rental charge", CURRENCY_FORMAT.format(tool.getToolType().getDailyChargeAmount().doubleValue()));
        appendSummaryLine(summary, "Charge days", Integer.toString(chargeDays));
        appendSummaryLine(summary, "Pre-discount charge", CURRENCY_FORMAT.format(preDiscountCharge.doubleValue()));
        appendSummaryLine(summary, "Discount percent", String.format("%d%%", checkout.getDiscountPercent()));
        appendSummaryLine(summary, "Discount amount", CURRENCY_FORMAT.format(discountAmount.doubleValue()));
        appendSummaryLine(summary, "Final charge", CURRENCY_FORMAT.format(finalCharge.doubleValue()));

        return summary.toString();
    }

    private void appendSummaryLine(StringBuilder sb, String fieldName, String fieldValue) {
        sb.append(fieldName);
        sb.append(": ");
        sb.append(fieldValue);
        sb.append(System.lineSeparator());
    }



}
