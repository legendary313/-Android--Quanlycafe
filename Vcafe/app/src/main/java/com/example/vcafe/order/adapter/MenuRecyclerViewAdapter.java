package com.example.vcafe.order.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.vcafe.R;
import com.example.vcafe.order.model.Item;

import java.util.List;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {
    private Context context;

    private List<Item> list;
    private OnItemOrderClickListener mOnItemOrderClickListener;

    public MenuRecyclerViewAdapter(Context context, List<Item> list, OnItemOrderClickListener mOnItemOrderClickListener) {
        this.context = context;
        this.list = list;
        this.mOnItemOrderClickListener = mOnItemOrderClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("CHECK-DATA","Ở ĐÂY LÀ 1 cái menu nè : "+list.size());
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView;
        RecyclerView.ViewHolder viewHolder=null;
        itemView=inflater.inflate(R.layout.menu_item,parent,false);

        viewHolder=new ViewHolder(itemView,mOnItemOrderClickListener);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder holder1=(ViewHolder) holder;
        Item item=list.get(position);

        ImageView anh=holder1.anh;
        TextView ten=holder1.ten;
        TextView gia=holder1.gia;


        Glide.with(context)
                .load(item.getImg_link())
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into( anh);
        ten.setText(item.getName());
        gia.setText(item.getPrice()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView anh;
        TextView ten;
        TextView gia;
        OnItemOrderClickListener onItemOrderClickListener;

        public ViewHolder( View itemView,OnItemOrderClickListener onItemOrderClickListener) {
            super(itemView);

            this.onItemOrderClickListener=onItemOrderClickListener;

            ten=(TextView)itemView.findViewById(R.id.ten_mon);
            gia=(TextView)itemView.findViewById(R.id.giaMon);
            anh=(ImageView)itemView.findViewById(R.id.anh_mon);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


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
