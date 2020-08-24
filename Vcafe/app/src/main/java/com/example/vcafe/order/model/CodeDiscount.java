package com.example.vcafe.order.model;

public class CodeDiscount extends Discount {
    private String code;

    public CodeDiscount() {
    }

    public CodeDiscount(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int getMoneyDiscout(int money) {
        return 0;
    }
}
