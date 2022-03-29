package ca.ubc.cs304.model;

import java.util.Date;

public class HotelBelongs {

    private int id;
    private String hotelName;
    private String companyName;
    private double revenue;
    private String address;
    private Date builtTime;
    private float rating;

    public HotelBelongs(int id, String hotelName, String companyName, double revenue, String address, Date builtTime, float rating) {
        this.id = id;
        this.hotelName = hotelName;
        this.companyName = companyName;
        this.revenue = revenue;
        this.address = address;
        this.builtTime = builtTime;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBuiltTime() {
        return builtTime;
    }

    public void setBuiltTime(Date builtTime) {
        this.builtTime = builtTime;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
