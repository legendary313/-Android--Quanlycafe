package com.example.listorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import java.util.List;

public class Drink_Order_List_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Drink_Oder_Item> items;
    private OnItemOrderClickListener mOnItemOrderClickListener;
    private int type;


    public Drink_Order_List_Adapter(Context context,List<Drink_Oder_Item> items,OnItemOrderClickListener onItemOrderClickListener){
        this.context=context;
        this.items=items;
        this.mOnItemOrderClickListener= onItemOrderClickListener;
        this.type=0;
    }
    public Drink_Order_List_Adapter(Context context,List<Drink_Oder_Item> items,OnItemOrderClickListener onItemOrderClickListener,int type){
        this.context=context;
        this.items=items;
        this.mOnItemOrderClickListener= onItemOrderClickListener;
        this.type=type;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView;
        RecyclerView.ViewHolder viewHolder=null;
        if(type==0){
            itemView=inflater.inflate(R.layout.order_item,parent,false);

            viewHolder=new ViewHolder(itemView,mOnItemOrderClickListener);
        }else if(type==1) {
            itemView=inflater.inflate(R.layout.order_item_2,parent,false);

            viewHolder=new ViewHolder2(itemView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(type==0){
            ViewHolder holder1=(ViewHolder) holder;
        Drink_Oder_Item item=items.get(position);

        ImageView anh=holder1.anh;
        TextView ten=holder1.ten;
        TextView gia=holder1.gia;
        TextView ghiChu=holder1.ghiChu;
        TextView soLuong=holder1.soLuong;


        Glide.with(context)
                .load(item.getAnh())
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into( anh);
        ten.setText(item.getTen());
        gia.setText(item.getGia()+"");
        ghiChu.setText(item.getGhiChu());
        soLuong.setText("x"+item.getSoLuong());
        }else if(type==1){
            ViewHolder2 holder2=(ViewHolder2)holder;

            Drink_Oder_Item item=items.get(position);


            TextView ten=holder2.ten;
            TextView gia=holder2.gia;
            TextView soLuong=holder2.soLuong;
            TextView tong=holder2.tong;

            ten.setText(item.getTen());
            gia.setText(item.getGia()+"");
            tong.setText(item.getSoLuong()*item.getGia()+"");
            soLuong.setText("x"+item.getSoLuong());


        }

    }

//    @Override
//    public void onBindViewHolder( Drink_Order_List_Adapter.ViewHolder holder, int position) {
//        Drink_Oder_Item item=items.get(position);
//
//
//        ImageView anh=holder.anh;
//        TextView ten=holder.ten;
//        TextView gia=holder.gia;
//        TextView ghiChu=holder.ghiChu;
//        TextView soLuong=holder.soLuong;
//
//
//        anh.setImageResource(item.getAnh());
//
//        Glide.with(context)
//                .load(item.getAnh())
//                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
//                .into( holder.anh);
//        ten.setText(item.getTen());
//        gia.setText(item.getGia()+"");
//        ghiChu.setText(item.getGhiChu());
//        soLuong.setText("x"+item.getSoLuong());
//    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView anh;
        public TextView ten;
        public TextView gia;
        public TextView ghiChu;
        public TextView soLuong;

        OnItemOrderClickListener onItemOrderClickListener;

        public ViewHolder( View itemView, OnItemOrderClickListener onItemOrderClickListener) {
            super(itemView);

            anh=(ImageView)itemView.findViewById(R.id.anh);
            ten=(TextView)itemView.findViewById(R.id.ten);
            gia=(TextView)itemView.findViewById(R.id.gia);
            ghiChu=(TextView)itemView.findViewById(R.id.ghi_chu);
            soLuong=(TextView)itemView.findViewById(R.id.so_luong);


            this.onItemOrderClickListener=onItemOrderClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener((View.OnLongClickListener) this);
        }

        @Override
        public void onClick(View v) {
                onItemOrderClickListener.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemOrderClickListener.onLongClick(getAdapterPosition());
            return true;
        }



    }


    public class ViewHolder2 extends RecyclerView.ViewHolder{
        public TextView ten;
        public TextView soLuong;
        public TextView gia;
        public TextView tong;

        public ViewHolder2(View itemView) {
            super(itemView);
            ten=(TextView)itemView.findViewById(R.id.order_item_ten_2);
            soLuong=(TextView)itemView.findViewById(R.id.order_item_so_luong_2);
            gia=(TextView)itemView.findViewById(R.id.order_item_gia_2);
            tong=(TextView)itemView.findViewById(R.id.order_item_tong_2);
        }
    }

    public interface OnItemOrderClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
}
