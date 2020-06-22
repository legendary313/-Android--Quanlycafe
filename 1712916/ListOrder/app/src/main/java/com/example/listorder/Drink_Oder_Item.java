package com.example.listorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Drink_Oder_Item extends Drink_Item{
    private int soLuong;
    private String ghiChu;
    //private List<Topping> listTopping;  Cái này thêm sau khi nâng cấp
    public Drink_Oder_Item(String ten, int gia, int anh,int soLuong,String ghiChu) {
        super(ten, gia, anh);
        this.soLuong=soLuong;
        this.ghiChu=ghiChu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }


    public static ArrayList<Drink_Oder_Item> generates(int n){
        ArrayList<Drink_Oder_Item> items=new ArrayList<>();
        Random rand = new Random();
        ArrayList<Integer> anhs=new ArrayList<>();
        anhs.add(R.drawable.nuoc_1);
        anhs.add(R.drawable.nuoc_2);
        anhs.add(R.drawable.nuoc_3);
        anhs.add(R.drawable.nuoc_4);
        anhs.add(R.drawable.nuoc_5);
        anhs.add(R.drawable.nuoc_6);
        anhs.add(R.drawable.nuoc_7);
        anhs.add(R.drawable.nuoc_8);
        anhs.add(R.drawable.nuoc_9);

        for(int i=0;i<n;i++){
            Drink_Oder_Item item=new Drink_Oder_Item("Nước "+i,((rand.nextInt(3)+1)*20),anhs.get(rand.nextInt(anhs.size())),rand.nextInt(10)+1,"Ghi chú: ");
            items.add(item);
        }

        return  items;
    }
}
