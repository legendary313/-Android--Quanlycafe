package com.example.vcafe.order.model;

import java.util.List;

public class Calculator {
    public static double total(List<OrderItem> orderItems){
        return 0.0;
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
