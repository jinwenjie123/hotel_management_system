package ca.ubc.cs304.model;

import java.util.Date;

public class WorkerWorks {
    private int wID;
    private int dID;
    private String name;
    private Date birthDate;
    private String sex;
    private String department;
    private Date contractStartTime;

    public WorkerWorks(int wID, int dID, String name, Date birthDate, String sex, String department, Date contractStartTime) {
        this.wID = wID;
        this.dID = dID;
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.department = department;
        this.contractStartTime = contractStartTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public int getwID() {
        return wID;
    }

    public void setwID(int wID) {
        this.wID = wID;
    }

    public int getdID() {
        return dID;
    }

    public void setdID(int dID) {
        this.dID = dID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
