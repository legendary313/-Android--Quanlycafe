package com.example.listorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Drink_Oder_Item> items=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView=(TextView)findViewById(R.id.tong_tien);



        RecyclerView dsOrder=(RecyclerView)findViewById(R.id.danh_sach_order);

        items=Drink_Oder_Item.generates(5);


        Drink_Order_List_Adapter adapter=new Drink_Order_List_Adapter(items);

        dsOrder.setAdapter(adapter);

        dsOrder.setLayoutManager(new LinearLayoutManager(this));

        int tong_tien=0;
        for(int i=0;i<items.size();i++){
            tong_tien+=items.get(i).getGia()*items.get(i).getSoLuong();
        }
        textView.setText(tong_tien+"");



    }
}
