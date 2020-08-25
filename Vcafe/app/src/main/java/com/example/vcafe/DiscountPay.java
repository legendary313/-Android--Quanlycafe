package com.example.vcafe;

import java.io.Serializable;
import java.util.Date;

public class DiscountPay implements Serializable {
    private int byMoney;
    private int byPercent;
    private Date deadline;
    private Date startDate;

    public DiscountPay(){

    }

    public DiscountPay(int byMoney, int byPercent, Date deadline, Date startDate) {
        this.byMoney = byMoney;
        this.byPercent = byPercent;
        this.deadline = deadline;
        this.startDate = startDate;
    }

    public int getByMoney() {
        return byMoney;
    }

    public void setByMoney(int byMoney) {
        this.byMoney = byMoney;
    }

    public int getByPercent() {
        return byPercent;
    }

    public void setByPercent(int byPercent) {
        this.byPercent = byPercent;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
