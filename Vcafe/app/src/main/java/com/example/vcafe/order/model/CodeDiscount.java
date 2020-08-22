package com.example.vcafe.order.model;

public class CodeDiscount extends Discount {
    private String code;

    @Override
    public int getMoneyDiscout(int money) {
        return 0;
    }
}
