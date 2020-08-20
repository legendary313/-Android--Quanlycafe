package com.example.vcafe.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vcafe.R;
import com.example.vcafe.order.model.Item;

public class ItemProfileDialog extends Dialog {
    Item drink_item;
    ImageView dialogAnh;
    TextView dialogTen;
    TextView dialogGia;
    TextView dialogMoTa;
    public ItemProfileDialog(Context context, Item drink_item) {
        super(context);
        this.drink_item=drink_item;
        this.setContentView(R.layout.dialog_profile_item);
    }
    public void setUp(){
        dialogAnh=(ImageView)this.findViewById(R.id.imv_dial_image);
        dialogTen=(TextView)this.findViewById(R.id.txtv_dial_name);
        dialogGia=(TextView)this.findViewById(R.id.txtv_dial_price);
        dialogMoTa=(TextView)this.findViewById(R.id.txtv_dial_decriptions);


        Glide.with(getContext())
                .load(drink_item.getImg_link())
                .into( dialogAnh);
        dialogTen.setText(  drink_item.getName());
        dialogGia.setText(  drink_item.getPrice()+"");
        dialogMoTa.setText(  drink_item.getDescription());


    }


}
