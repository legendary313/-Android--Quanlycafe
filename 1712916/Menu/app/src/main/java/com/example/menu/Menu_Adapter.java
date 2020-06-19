package com.example.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import java.util.List;

public class Menu_Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private java.util.List<Menu_Item> List;

    public Menu_Adapter(Context context, int layout, java.util.List<Menu_Item> list) {
        this.context = context;
        this.layout = layout;
        List = list;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int i) {
        return List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class ViewHolder{
        ImageView anh;
        TextView ten;
        TextView gia;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view==null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder=new ViewHolder();
            //ánh xạ view
            holder.ten=(TextView)view.findViewById(R.id.tenMon);
            holder.gia=(TextView)view.findViewById(R.id.giaMon);
            holder.anh=(ImageView)view.findViewById(R.id.anhMon);
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)view.getTag();
        }


        //gán giá trị
        Menu_Item itemRow=List.get(i);
        Glide.with(context)
                .load(itemRow.getAnh())
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into( holder.anh);
        holder.ten.setText(itemRow.getTen());
        holder.gia.setText(itemRow.getGia());
        return view;
    }
}
