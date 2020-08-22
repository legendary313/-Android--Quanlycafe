package com.example.vcafe.order.model;

public class ItemDiscount extends Discount {
    private int itemKey;

    @Override
    public int getMoneyDiscout(int money) {
        return 0;
    }
}
