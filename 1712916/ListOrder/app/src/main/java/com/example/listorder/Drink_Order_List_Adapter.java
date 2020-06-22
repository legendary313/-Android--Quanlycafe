package com.example.listorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Drink_Order_List_Adapter extends RecyclerView.Adapter<Drink_Order_List_Adapter.ViewHolder>{
    private List<Drink_Oder_Item> items;

    public Drink_Order_List_Adapter(List<Drink_Oder_Item> items){
        this.items=items;
    }


    @Override
    public Drink_Order_List_Adapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);

        View itemView=inflater.inflate(R.layout.order_item,parent,false);

        ViewHolder viewHolder=new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( Drink_Order_List_Adapter.ViewHolder holder, int position) {
        Drink_Oder_Item item=items.get(position);


        ImageView anh=holder.anh;
        TextView ten=holder.ten;
        TextView gia=holder.gia;
        TextView ghiChu=holder.ghiChu;
        TextView soLuong=holder.soLuong;


        anh.setImageResource(item.getAnh());
        ten.setText(item.getTen());
        gia.setText(item.getGia()+"");
        ghiChu.setText(item.getGhiChu());
        soLuong.setText("x"+item.getSoLuong());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView anh;
        public TextView ten;
        public TextView gia;
        public TextView ghiChu;
        public TextView soLuong;


        public ViewHolder( View itemView) {
            super(itemView);

            anh=(ImageView)itemView.findViewById(R.id.anh);
            ten=(TextView)itemView.findViewById(R.id.ten);
            gia=(TextView)itemView.findViewById(R.id.gia);
            ghiChu=(TextView)itemView.findViewById(R.id.ghi_chu);
            soLuong=(TextView)itemView.findViewById(R.id.so_luong);
        }
    }
}
