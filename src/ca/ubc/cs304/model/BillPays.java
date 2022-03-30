package ca.ubc.cs304.model;

import java.util.Date;

public class BillPays {
    private int bID;
    private int cID;
    private double price;
    private String state;
    private Date paymentDate;
    private String paymentMethod;

    public BillPays(int bID, int cID, double price, String state, Date paymentDate, String paymentMethod) {
        this.bID = bID;
        this.cID = cID;
        this.price = price;
        this.state = state;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public int getbID() {
        return bID;
    }

    public void setbID(int bID) {
        this.bID = bID;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
