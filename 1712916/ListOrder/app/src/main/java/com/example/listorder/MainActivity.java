package com.example.listorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Drink_Order_List_Adapter.OnItemOrderClickListener{
    ArrayList<Drink_Oder_Item> items=new ArrayList<>();
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
        setContentView(R.layout.activity_main);


        textView=(TextView)findViewById(R.id.tong_tien);



        RecyclerView dsOrder=(RecyclerView)findViewById(R.id.danh_sach_order);

        items=Drink_Oder_Item.generates(5);


        adapter=new Drink_Order_List_Adapter(this,items,this);

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
        items.remove(position);
        adapter.notifyDataSetChanged();
        int tong_tien=tongTien();

        textView.setText(tong_tien+"");
    }

    public int tongTien(){
        int tong_tien=0;
        for(int i=0;i<items.size();i++){
            tong_tien+=items.get(i).getGia()*items.get(i).getSoLuong();
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


        dialogAnh.setImageResource(items.get(position).getAnh());
        dialogTen.setText(items.get(position).getTen());
        dialogGia.setText(items.get(position).getGia()+"");
        dialogSoLuong.setText(items.get(position).getSoLuong()+"");
        dialogGhiChu.setText(items.get(position).getGhiChu());


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

                items.get(position).setSoLuong(sl);
                items.get(position).setGhiChu(ghiChu);

                adapter.notifyDataSetChanged();
                int tong_tien=tongTien();

                textView.setText(tong_tien+"");
                itemDialog.dismiss();
            }
        });
        itemDialog.show();


    }
}
