package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private ArrayList<Category_Menu> dsMenu;
    private ViewPager viewPager=null;
    private Menu_Viewpager_Adapter menu_viewpager_adapter;
    public  static List<Drink_Item>  dsOrder=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tạo dữ liệu giả
        anhXa();

        menu_viewpager_adapter=new Menu_Viewpager_Adapter(getSupportFragmentManager(),dsMenu);
        viewPager.setAdapter(menu_viewpager_adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void anhXa(){
        dsMenu=new ArrayList<>();
        viewPager=(ViewPager)findViewById(R.id.menu_category);
        ArrayList<Drink_Item> dsNuocUong2;
        ArrayList<Drink_Item> dsNuocUong;
        dsNuocUong=new ArrayList<>();
        dsNuocUong.add(new Drink_Item("So da","20 000 đ",R.drawable.nuoc_1));
        dsNuocUong.add(new Drink_Item("Nước cam","20 000 đ",R.drawable.nuoc_2));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_3));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_4));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_5));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_6));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_7));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_8));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_9));


        dsNuocUong2=new ArrayList<>();
        dsNuocUong2.add(new Drink_Item("So da","20 000 đ",R.drawable.nuoc_1));
        dsNuocUong2.add(new Drink_Item("Nước cam","20 000 đ",R.drawable.nuoc_2));
        dsNuocUong2.add(new Drink_Item("Trà đào","20 000 đ",R.drawable.nuoc_3));
        dsNuocUong2.add(new Drink_Item("Nước ép táo","20 000 đ",R.drawable.nuoc_4));
        dsNuocUong2.add(new Drink_Item("Con sư tử","20 000 đ",R.drawable.nuoc_5));
        dsNuocUong2.add(new Drink_Item("Dừa trái","20 000 đ",R.drawable.nuoc_6));
        dsNuocUong2.add(new Drink_Item("Nước ép dưa hấu","20 000 đ",R.drawable.nuoc_7));
        dsNuocUong2.add(new Drink_Item("Nước cam ép","20 000 đ",R.drawable.nuoc_8));
        dsNuocUong2.add(new Drink_Item("Trà đá","20 000 đ",R.drawable.nuoc_9));

        dsMenu.add(new Category_Menu("Trà Sữa",dsNuocUong));
        dsMenu.add(new Category_Menu("Cà Phê",dsNuocUong2));
        dsMenu.add(new Category_Menu("Soda",dsNuocUong));
        dsMenu.add(new Category_Menu("Sinh Tố",dsNuocUong2));
        dsMenu.add(new Category_Menu("Nước Ép",dsNuocUong2));
    }
}

//public class MainActivity extends AppCompatActivity {
//
//    private GridView menu_view;
//    private ArrayList<Menu_Item> dsNuocUong;
//    private Menu_Adapter menu_adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //Tạo dữ liệu giả
//        anhXa();
//        menu_adapter=new Menu_Adapter(this, R.layout.menu_item,dsNuocUong);
//
//        menu_view.setAdapter(menu_adapter);
//
//    }
//
//    private void anhXa(){
//        menu_view=findViewById(R.id.Menu);
//        dsNuocUong=new ArrayList<>();
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_1));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_2));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_3));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_4));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_5));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_6));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_7));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_8));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_9));
//
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_1));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_2));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_3));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_4));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_5));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_6));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_7));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_8));
//        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_9));
//
//    }
//}
