package com.example.vcafe.order.model;

import java.util.Date;

public abstract class Discount {

    protected int byMoney;
    protected int byPercent;
    protected Date startDate;
    protected Date deadline;

    public static float checkCode(String code){
        if(code.equals("vinh")){
            return 15000;
        }else if(code.equals("mei")) {
            return 0.2f;
        }
        return -1.f;
    }

    public abstract int getMoneyDiscout(int money);

    public int getByMoney() {
        return byMoney;
    }

    public void setByMoney(int byMoney) {
        this.byMoney = byMoney;
    }


    public int getByPercent() {
        return  byPercent ;
    }
    public void setByPercent(int byPercent) {
        this.byPercent = byPercent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}


