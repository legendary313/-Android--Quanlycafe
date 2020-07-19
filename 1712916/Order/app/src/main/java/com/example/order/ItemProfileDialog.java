package com.example.order;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

public class ItemProfileDialog extends Dialog {
    Drink_Item drink_item;
    ImageView dialogAnh;
    TextView dialogTen;
    TextView dialogGia;
    TextView dialogMoTa;
    public ItemProfileDialog(Context context, Drink_Item drink_item) {
        super(context);
        this.drink_item=drink_item;
        this.setContentView(R.layout.dialog_profile_item);
    }
    public void setUp(){
        dialogAnh=(ImageView)this.findViewById(R.id.dialog_item_anh);
        dialogTen=(TextView)this.findViewById(R.id.dialog_item_ten);
        dialogGia=(TextView)this.findViewById(R.id.dialog_item_gia);
        dialogMoTa=(TextView)this.findViewById(R.id.dialog_item_mo_ta);


        Glide.with(getContext())
                .load(drink_item.getAnh())
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into( dialogAnh);
        dialogTen.setText(  drink_item.getTen());
        dialogGia.setText(  drink_item.getGia()+"");
        dialogMoTa.setText(  drink_item.getMoTa());


    }


}
