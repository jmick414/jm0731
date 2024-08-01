package com.github.jmick414.demo.bigtools2go.tool;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TOOL")
public class Tool {

    @Id
    private String toolCode;

    @ManyToOne
    @JoinColumn(name = "TOOL_TYPE_ID")
    private ToolType toolType;

    @Column(name = "BRAND")
    private String brand;

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "toolCode='" + toolCode + '\'' +
                ", toolType=" + toolType +
                ", brand='" + brand + '\'' +
                '}';
    }
}
