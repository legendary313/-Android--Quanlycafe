package com.example.order;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements Drink_Order_List_Adapter.OnItemOrderClickListener{

   // ArrayList<Drink_Oder_Item> items=new ArrayList<>();
    Drink_Order_List_Adapter adapter;
    TextView textView;
    Dialog itemDialog;


    ImageView dialogAnh;
    TextView dialogTen;
    TextView dialogGia;
    TextView dialogSoLuong;
    EditText dialogGhiChu;
    Button dialogSLTang;
    Button dialogSLGiam;
    Button dialogOke;
    Button dialogCancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);


        textView=(TextView)findViewById(R.id.tong_tien);



        RecyclerView dsOrder=(RecyclerView)findViewById(R.id.danh_sach_order);

       // MainActivity.dsOrder=Drink_Oder_Item.generates(5);


        adapter=new Drink_Order_List_Adapter(this, MainActivity.dsOrder,this);

        dsOrder.setAdapter(adapter);

        dsOrder.setLayoutManager(new LinearLayoutManager(this));

        int tong_tien=tongTien();
        textView.setText(tong_tien+"");








    }

    @Override
    public void onClick(int position) {
//        Toast.makeText(getApplicationContext(),"Đây là "+items.get(position).getTen()+"Số lượng"+items.get(position).getSoLuong(),Toast.LENGTH_SHORT).show();
//        items.get(position).setSoLuong(items.get(position).getSoLuong()+1);
//        adapter.notifyDataSetChanged();
//        int tong_tien=tongTien();
//
//        textView.setText(tong_tien+"");
        xuLyItemDialog(position);

    }

    @Override
    public void onLongClick(int position) {
        MainActivity.dsOrder.remove(position);
        adapter.notifyDataSetChanged();
        int tong_tien=tongTien();

        textView.setText(tong_tien+"");
    }

    public int tongTien(){
        int tong_tien=0;
        for(int i=0;i< MainActivity.dsOrder.size();i++){
            tong_tien+= MainActivity.dsOrder.get(i).getGia()* MainActivity.dsOrder.get(i).getSoLuong();
        }
        return tong_tien;

    }

    public void xuLyItemDialog(final int position){
        itemDialog=new Dialog(this);
        itemDialog.setContentView(R.layout.dialog_order_item);

        dialogAnh=(ImageView)itemDialog.findViewById(R.id.dialog_item_anh);
        dialogTen=(TextView)itemDialog.findViewById(R.id.dialog_item_ten);
        dialogGia=(TextView)itemDialog.findViewById(R.id.dialog_item_gia);
        dialogSoLuong=(TextView)itemDialog.findViewById(R.id.dialog_item_so_luong);
        dialogSLTang=(Button)itemDialog.findViewById(R.id.dialog_item_so_luong_tang);
        dialogSLGiam=(Button)itemDialog.findViewById(R.id.dialog_item_so_luong_giam);
        dialogOke=(Button)itemDialog.findViewById(R.id.dialog_oke);
        dialogCancle=(Button)itemDialog.findViewById(R.id.dialog_cancle);
        dialogGhiChu=(EditText)itemDialog.findViewById(R.id.dialog_item_ghi_chu);


        dialogAnh.setImageResource( MainActivity.dsOrder.get(position).getAnh());
        dialogTen.setText( MainActivity.dsOrder.get(position).getTen());
        dialogGia.setText( MainActivity.dsOrder.get(position).getGia()+"");
        dialogSoLuong.setText( MainActivity.dsOrder.get(position).getSoLuong()+"");
        dialogGhiChu.setText( MainActivity.dsOrder.get(position).getGhiChu());


        dialogSLGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl=Integer.parseInt(dialogSoLuong.getText().toString());
                if(sl>1){
                    sl--;
                }
                dialogSoLuong.setText(sl+"");



            }
        });

        dialogSLTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl=Integer.parseInt(dialogSoLuong.getText().toString());
                sl++;
                dialogSoLuong.setText(sl+"");

            }
        });



        dialogCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDialog.dismiss();
            }
        });


        dialogOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl=Integer.parseInt(dialogSoLuong.getText().toString());
                String ghiChu=dialogGhiChu.getText().toString();

                MainActivity.dsOrder.get(position).setSoLuong(sl);
                MainActivity.dsOrder.get(position).setGhiChu(ghiChu);

                adapter.notifyDataSetChanged();
                int tong_tien=tongTien();

                textView.setText(tong_tien+"");
                itemDialog.dismiss();
            }
        });
        itemDialog.show();


    }
}
