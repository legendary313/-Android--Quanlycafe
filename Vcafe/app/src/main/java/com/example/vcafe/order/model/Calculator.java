package com.example.vcafe.order.model;

import java.util.List;

public class Calculator {
    public static int totalMoney(List<OrderItem> orderItems){
        int total=0;
        for(int i=0;i<orderItems.size();i++){
            total+=orderItems.get(i).getQuantity()*(orderItems.get(i).getPrice()-orderItems.get(i).getDiscountMoney());
        }
        return total;
    }
    public static int totalQuantity(List<OrderItem> orderItems){
        int total=0;
        for(int i=0;i<orderItems.size();i++){
            total+=orderItems.get(i).getQuantity();
        }
        return total;
    }
    public static int validateAddOrder(List<OrderItem> orderItems,Item item){
        int i=0;
        //Check data
        while (i< orderItems.size()){
            if(item.getName().equals(orderItems.get(i).getName())){
                return i;
            }
            i++;
        }


        return -1;
    }
}
