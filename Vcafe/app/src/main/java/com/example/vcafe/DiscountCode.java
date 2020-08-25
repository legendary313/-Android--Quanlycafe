package com.example.vcafe;

import java.io.Serializable;
import java.util.Date;

public class DiscountCode implements Serializable {
    private int byMoney;
    private int byPercent;
    private String code;

    public DiscountCode()
    {

    }

    public DiscountCode(int byMoney, int byPercent, String code) {
        this.byMoney = byMoney;
        this.byPercent = byPercent;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
