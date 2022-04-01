package ca.ubc.cs304.model;

import java.util.Date;

public class Customer {
    private int cID;
    private int hotelID;
    private String address;
    private String phoneNumber;
    private String drivingLicense;
    private Date checkinTime;
    private Date checkoutTime;
    private String name;

    public Customer(int cID, int hotelID, String address, String phoneNumber, String drivingLicense, Date checkinTime, Date checkoutTime, String name) {
        this.cID = cID;
        this.hotelID = hotelID;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.drivingLicense = drivingLicense;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }
}
