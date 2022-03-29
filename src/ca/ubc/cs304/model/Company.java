package ca.ubc.cs304.model;

import java.util.Date;

public class Company {

    private String name;
    private double marketPrice;
    private Date builtTime;
    private String address;

    public Company(String name, double marketPrice, Date builtTime, String address) {
        this.name = name;
        this.marketPrice = marketPrice;
        this.builtTime = builtTime;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Date getBuiltTime() {
        return builtTime;
    }

    public void setBuiltTime(Date builtTime) {
        this.builtTime = builtTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
