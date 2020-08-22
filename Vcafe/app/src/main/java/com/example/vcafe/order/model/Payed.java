package com.example.vcafe.order.model;

import java.util.Date;

public class Payed {
    private String code;
    private String stuff;
    private Date date;
    private String customer;
    private int service_charge;

    public Payed(String stuff, Date date, String customer, int service_charge) {
        this.stuff = stuff;
        this.date = date;
        this.customer = customer;
        this.service_charge = service_charge;
    }

    public Payed() {
    }

    public Payed(String code, String stuff, Date date, String customer, int service_charge) {
        this.code = code;
        this.stuff = stuff;
        this.date = date;
        this.customer = customer;
        this.service_charge = service_charge;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStuff() {
        return stuff;
    }

    public void setStuff(String stuff) {
        this.stuff = stuff;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getService_charge() {
        return service_charge;
    }

    public void setService_charge(int service_charge) {
        this.service_charge = service_charge;
    }
}
