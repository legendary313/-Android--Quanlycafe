package com.example.vcafe.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vcafe.R;
import com.example.vcafe.order.model.OrderItem;
import com.example.vcafe.order.model.VieMoney;

import java.util.List;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<OrderItem> items;
    private OnItemOrderClickListener mOnItemOrderClickListener;



    public CardRecyclerViewAdapter(Context context, List<OrderItem> items, OnItemOrderClickListener onItemOrderClickListener){
        this.context=context;
        this.items=items;
        this.mOnItemOrderClickListener= onItemOrderClickListener;

    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView;
        RecyclerView.ViewHolder viewHolder=null;

            itemView=inflater.inflate(R.layout.card_item,parent,false);

            viewHolder=new ViewHolder(itemView,mOnItemOrderClickListener);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder holder1=(ViewHolder) holder;
        OrderItem item=items.get(position);

        ImageView anh=holder1.anh;
        TextView ten=holder1.ten;
        TextView gia=holder1.gia;
        TextView ghiChu=holder1.ghiChu;
        TextView soLuong=holder1.soLuong;


        Glide.with(context)
                .load(item.getImg_link())
                .into( anh);
        ten.setText(item.getName());
        gia.setText(new VieMoney().change(item.getPrice()-item.getDiscountMoney()));
        ghiChu.setText(item.getNote());
        soLuong.setText("x"+item.getQuantity());


    }



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




    public interface OnItemOrderClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
}
