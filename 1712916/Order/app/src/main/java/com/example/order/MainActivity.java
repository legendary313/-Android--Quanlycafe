package com.example.order;


import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Category_Menu> dsMenu;
    private ViewPager viewPager=null;
    private Menu_Viewpager_Adapter menu_viewpager_adapter;
    public static ArrayList<Drink_Oder_Item> dsOrder;
    LinearLayout cart;


    private static TextView  txtSoLuong,txtTongTien;

    public List<Drink_Oder_Item> getDsOrder() {
        return dsOrder;
    }

    public void setDsOrder(ArrayList<Drink_Oder_Item> dsOrder) {
        this.dsOrder = dsOrder;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_category);
        dsOrder=new ArrayList<>();
        //Tạo dữ liệu giả
        anhXa();

        menu_viewpager_adapter=new Menu_Viewpager_Adapter(getSupportFragmentManager(),dsMenu);
        viewPager.setAdapter(menu_viewpager_adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Mo ra ds order",Toast.LENGTH_SHORT).show();
                Intent cartIntent=new Intent(getApplicationContext(),CartActivity.class);
                startActivity(cartIntent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateCard();
    }

    private   static void updateCard(){
        int soLuong=0;
        for(int i=0;i<dsOrder.size();i++){
            soLuong+=dsOrder.get(i).getSoLuong();
        }
        int tongTien=0;
        for(int i=0;i<dsOrder.size();i++){
            tongTien+=dsOrder.get(i).getSoLuong()*dsOrder.get(i).getGia();
        }
//
       txtTongTien.setText(tongTien+"");
        txtSoLuong.setText("x"+soLuong);
//




    }

    private void anhXa(){
        dsMenu=new ArrayList<>();
        viewPager=(ViewPager)findViewById(R.id.menu_category);
//
//        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//        cartFragment= CartFragment.newInstance("Hello");
//        ft.replace(R.id.hello,cartFragment);
//        ft.commit();
        txtSoLuong=(TextView)findViewById(R.id.cart_so_luong);
        txtTongTien=(TextView)findViewById(R.id.cart_tong_tien);
        cart=(LinearLayout)findViewById(R.id.cart_in_menu);

        ArrayList<Drink_Item> dsNuocUong2;
        ArrayList<Drink_Item> dsNuocUong;
        dsNuocUong=new ArrayList<>();
        dsNuocUong.add(new Drink_Item("So da",21000,R.drawable.nuoc_1));
        dsNuocUong.add(new Drink_Item("Nước cam",22000,R.drawable.nuoc_2));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_3));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_4));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_5));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_6));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_7));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_8));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_9));


        dsNuocUong2=new ArrayList<>();
        dsNuocUong2.add(new Drink_Item("So da",23000,R.drawable.nuoc_1));
        dsNuocUong2.add(new Drink_Item("Nước cam",24000,R.drawable.nuoc_2));
        dsNuocUong2.add(new Drink_Item("Trà đào",25000,R.drawable.nuoc_3));
        dsNuocUong2.add(new Drink_Item("Nước ép táo",20000,R.drawable.nuoc_4));
        dsNuocUong2.add(new Drink_Item("Con sư tử",20000,R.drawable.nuoc_5));
        dsNuocUong2.add(new Drink_Item("Dừa trái",20000,R.drawable.nuoc_6));
        dsNuocUong2.add(new Drink_Item("Nước ép dưa hấu",20000,R.drawable.nuoc_7));
        dsNuocUong2.add(new Drink_Item("Nước cam ép",20000,R.drawable.nuoc_8));
        dsNuocUong2.add(new Drink_Item("Trà đá",20000,R.drawable.nuoc_9));

        dsMenu.add(new Category_Menu("Trà Sữa",dsNuocUong));
        dsMenu.add(new Category_Menu("Cà Phê",dsNuocUong2));
        dsMenu.add(new Category_Menu("Soda",dsNuocUong));
        dsMenu.add(new Category_Menu("Sinh Tố",dsNuocUong2));
        dsMenu.add(new Category_Menu("Nước Ép",dsNuocUong2));
    }

    public static void addToCart(Drink_Item drink_item){


        Drink_Oder_Item drink_oder_item=new Drink_Oder_Item(drink_item.getTen(),drink_item.getGia(),drink_item.getAnh(),drink_item.getMoTa(),1,"");
        int i=0;

        while (i<MainActivity.dsOrder.size()){
            if(drink_oder_item.getTen().equals(dsOrder.get(i).getTen())){
                int sl= dsOrder.get(i).getSoLuong();
                dsOrder.get(i).setSoLuong(sl+drink_oder_item.getSoLuong());
                break;
            }

            i++;
        }
        if(i==MainActivity.dsOrder.size()){
            MainActivity.dsOrder.add(drink_oder_item);
        }
        updateCard();

    }



}
