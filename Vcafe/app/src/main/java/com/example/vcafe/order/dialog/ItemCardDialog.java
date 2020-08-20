package com.example.vcafe.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.vcafe.R;
import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.adapter.CardRecyclerViewAdapter;
import com.example.vcafe.order.fragment.CardFragment;
import com.example.vcafe.order.model.Calculator;
import com.example.vcafe.order.model.IUpdateView;
import com.example.vcafe.order.model.VieMoney;

public class ItemCardDialog extends Dialog implements IUpdateView {
    private int position;
    IUpdateView iUpdateView;


    ImageView dialogAnh;
    TextView dialogTen;
    TextView dialogGia;
    TextView dialogSoLuong;
    EditText dialogGhiChu;
    Button dialogSLTang;
    Button dialogSLGiam;
    Button dialogOke;

    public ItemCardDialog(@NonNull Context context, int position,IUpdateView iUpdateView    ) {
        super(context);
        this.setContentView(R.layout.dialog_order_item);
        this.position=position;
        this.iUpdateView=iUpdateView;


    }

    public void setUp(){
        dialogAnh=(ImageView)this.findViewById(R.id.imv_dial_image);
        dialogTen=(TextView)this.findViewById(R.id.txtv_dial_name);
        dialogGia=(TextView)this.findViewById(R.id.txtv_dial_price);
        dialogSoLuong=(TextView)this.findViewById(R.id.edit_dial_quantity);
        dialogSLTang=(Button)this.findViewById(R.id.btn_dial_increase);
        dialogSLGiam=(Button)this.findViewById(R.id.btn_dial_decrease);
        dialogOke=(Button)this.findViewById(R.id.btn_dial_oke);

        dialogGhiChu=(EditText)this.findViewById(R.id.edit_dial_note);


        Glide.with(getContext())
                .load(OrderActivity.orders.get(position).getImg_link())
                .into( dialogAnh);

        dialogTen.setText( OrderActivity.orders.get(position).getName());
        dialogGia.setText(new VieMoney().change( OrderActivity.orders.get(position).getPrice()) );
        dialogSoLuong.setText( OrderActivity.orders.get(position).getQuantity()+"");
        dialogGhiChu.setText( OrderActivity.orders.get(position).getNote());


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

                OrderActivity.orders.get(position).setQuantity(sl);
                OrderActivity.orders.get(position).setNote(ghiChu);
                updateView();
                dismiss();

            }
        });



    }


    @Override
    public void updateView() {
        iUpdateView.updateView();
    }
}
