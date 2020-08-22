package com.example.vcafe.order.model;

public class PayDiscount extends Discount {
    public PayDiscount() {
    }

    @Override
    public int getMoneyDiscout(int money) {
        return (int)money*getByPercent()/100;
    }
}
