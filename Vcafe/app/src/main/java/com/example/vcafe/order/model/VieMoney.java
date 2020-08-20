package com.example.vcafe.order.model;

public class VieMoney implements IMoneyType{
    private final  char separator='.';
    private final String symbol= " VNĐ";

    @Override
    public String change(int money) {
        String strMoney=String.valueOf(money);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(strMoney);

        int size=stringBuilder.length();
        int mod=size%3;
        for(int i=0;i<size/3;i++){
            int index=mod+i*3 +i;
            System.out.println(index);
            stringBuilder.insert( index,separator);
        }
        if(stringBuilder.charAt(0)=='.'){
            stringBuilder.deleteCharAt(0);
        }
        stringBuilder.append(symbol);
        return stringBuilder.toString();
    }


}
