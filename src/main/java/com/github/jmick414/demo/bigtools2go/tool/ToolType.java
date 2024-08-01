package com.github.jmick414.demo.bigtools2go.tool;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name="TOOL_TYPE")
public class ToolType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DAILY_CHARGE_AMOUNT")
    private BigDecimal dailyChargeAmount;

    @Column(name = "WEEKDAY_CHARGE")
    private boolean weekdayCharge;

    @Column(name = "WEEKEND_CHARGE")
    private boolean weekendCharge;

    @Column(name = "HOLIDAY_CHARGE")
    private boolean holidayCharge;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDailyChargeAmount() {
        return dailyChargeAmount;
    }

    public void setDailyChargeAmount(BigDecimal dailyChargeAmount) {
        this.dailyChargeAmount = dailyChargeAmount;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }

    @Override
    public String toString() {
        return "ToolType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dailyChargeAmount=" + dailyChargeAmount +
                ", weekdayCharge=" + weekdayCharge +
                ", weekendCharge=" + weekendCharge +
                ", holidayCharge=" + holidayCharge +
                '}';
    }
}
