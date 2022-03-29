package ca.ubc.cs304.model;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

import java.util.Date;

public class Membership {
    private int mbID;
    private int cID;
    private Date joinDate;
    private float discount;
    private long credit;

    public Membership(int mbID, int cID, Date joinDate, float discount, long credit) {
        this.mbID = mbID;
        this.cID = cID;
        this.joinDate = joinDate;
        this.discount = discount;
        this.credit = credit;
    }

    public int getMbID() {
        return mbID;
    }

    public void setMbID(int mbID) {
        this.mbID = mbID;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }
}
