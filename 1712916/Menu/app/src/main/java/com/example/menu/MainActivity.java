package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView menu_view;
    private ArrayList<Menu_Item> dsNuocUong;
    private Menu_Adapter menu_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tạo dữ liệu giả
        anhXa();
        menu_adapter=new Menu_Adapter(this, R.layout.menu_item,dsNuocUong);

        menu_view.setAdapter(menu_adapter);

    }

    private void anhXa(){
        menu_view=findViewById(R.id.Menu);
        dsNuocUong=new ArrayList<>();
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_1));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_2));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_3));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_4));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_5));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_6));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_7));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_8));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_9));

        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_1));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_2));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_3));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_4));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_5));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_6));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_7));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_8));
        dsNuocUong.add(new Menu_Item("Nước uống 1","20 000 đ",R.drawable.nuoc_9));

    }
}
