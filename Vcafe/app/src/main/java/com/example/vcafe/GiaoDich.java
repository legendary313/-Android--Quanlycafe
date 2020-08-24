package com.example.vcafe;

import java.io.Serializable;
import java.util.Date;

public class GiaoDich implements Serializable {
    private String customer;
    private Date date;
    private int service_charge;
    private String stuff;


    public GiaoDich() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getService_charge() {
        return service_charge;
    }

    public void setService_charge(int service_charge) {
        this.service_charge = service_charge;
    }

    public String getStuff() {
        return stuff;
    }

    public void setStuff(String stuff) {
        this.stuff = stuff;
    }

    public GiaoDich(String customer, Date date, int service_charge, String stuff) {
        this.customer = customer;
        this.date = date;
        this.service_charge = service_charge;
        this.stuff = stuff;
    }
}
