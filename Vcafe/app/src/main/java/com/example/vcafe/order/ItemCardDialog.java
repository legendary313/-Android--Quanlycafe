package com.example.vcafe.order;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.vcafe.R;
import com.example.vcafe.order.adapter.OrderRecyclerViewAdapter;

public class ItemCardDialog extends Dialog {
    private int position;
    OrderRecyclerViewAdapter adapter;
    TextView tongTien;
    ImageView dialogAnh;
    TextView dialogTen;
    TextView dialogGia;
    TextView dialogSoLuong;
    EditText dialogGhiChu;
    Button dialogSLTang;
    Button dialogSLGiam;
    Button dialogOke;
    Button dialogCancle;
    public ItemCardDialog(@NonNull Context context, int position, OrderRecyclerViewAdapter adapter, TextView tongTien) {
        super(context);
        this.setContentView(R.layout.dialog_order_item);
        this.position=position;
        this.adapter=adapter;
        this.tongTien=tongTien;
    }

    public void setUp(){
        dialogAnh=(ImageView)this.findViewById(R.id.dialog_item_anh);
        dialogTen=(TextView)this.findViewById(R.id.dialog_item_ten);
        dialogGia=(TextView)this.findViewById(R.id.dialog_item_gia);
        dialogSoLuong=(TextView)this.findViewById(R.id.dialog_item_so_luong);
        dialogSLTang=(Button)this.findViewById(R.id.dialog_item_so_luong_tang);
        dialogSLGiam=(Button)this.findViewById(R.id.dialog_item_so_luong_giam);
        dialogOke=(Button)this.findViewById(R.id.dialog_oke);

        dialogGhiChu=(EditText)this.findViewById(R.id.dialog_item_ghi_chu);


        Glide.with(getContext())
                .load(OrderActivity.dsOrder.get(position).getImg_link())
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into( dialogAnh);

        dialogTen.setText( OrderActivity.dsOrder.get(position).getName());
        dialogGia.setText( OrderActivity.dsOrder.get(position).getPrice()+"");
        dialogSoLuong.setText( OrderActivity.dsOrder.get(position).getQuantity()+"");
        dialogGhiChu.setText( OrderActivity.dsOrder.get(position).getNote());


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






        dialogOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl=Integer.parseInt(dialogSoLuong.getText().toString());
                String ghiChu=dialogGhiChu.getText().toString();

                OrderActivity.dsOrder.get(position).setQuantity(sl);
                OrderActivity.dsOrder.get(position).setNote(ghiChu);

                adapter.notifyDataSetChanged();
                int tong_tien=tongTien();

                tongTien.setText(tong_tien+"");
                dismiss();
            }
        });



    }

    public int tongTien(){
        int tong_tien=0;
        for(int i=0;i< OrderActivity.dsOrder.size();i++){
            tong_tien+= OrderActivity.dsOrder.get(i).getPrice()* OrderActivity.dsOrder.get(i).getQuantity();
        }
        return tong_tien;

    }

}
