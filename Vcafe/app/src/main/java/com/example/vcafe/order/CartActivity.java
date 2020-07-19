package com.example.vcafe.order;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcafe.R;

public class CartActivity extends AppCompatActivity implements Drink_Order_List_Adapter.OnItemOrderClickListener{

   // ArrayList<Drink_Oder_Item> items=new ArrayList<>();
    Drink_Order_List_Adapter adapter;
    TextView textView;
    Dialog itemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);


        textView=(TextView)findViewById(R.id.tong_tien);



        RecyclerView dsOrder=(RecyclerView)findViewById(R.id.danh_sach_order);




        adapter=new Drink_Order_List_Adapter(this, OrderActivity.dsOrder,this);

        dsOrder.setAdapter(adapter);

        dsOrder.setLayoutManager(new LinearLayoutManager(this));

        int tong_tien=tongTien();
        textView.setText(tong_tien+"");



    }

    @Override
    public void onClick(int position) {

        xuLyItemDialog2(position);

    }

    @Override
    public void onLongClick(int position) {
        OrderActivity.dsOrder.remove(position);
        adapter.notifyDataSetChanged();
        int tong_tien=tongTien();

        textView.setText(tong_tien+"");
    }

    public int tongTien(){
        int tong_tien=0;
        for(int i=0;i< OrderActivity.dsOrder.size();i++){
            tong_tien+= OrderActivity.dsOrder.get(i).getGia()* OrderActivity.dsOrder.get(i).getSoLuong();
        }
        return tong_tien;

    }
    public void xuLyItemDialog2(final int position){
        ItemCardDialog itemCardDialog2 =new ItemCardDialog(this,position,adapter,textView);
        itemCardDialog2.setUp();
        itemCardDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        itemCardDialog2.show();

    }


}
