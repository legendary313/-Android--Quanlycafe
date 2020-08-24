package com.example.vcafe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GiaoDichAdapter extends BaseAdapter {
    private List<GiaoDich> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    DatabaseReference mDatabase;
    public GiaoDichAdapter(Context aContext,  List<GiaoDich> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GiaoDichAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_tkbanhang_item, null);
            holder = new GiaoDichAdapter.ViewHolder();
            holder.NgayBH = (TextView) convertView.findViewById(R.id.listtkbh_item_NgayBH);
            holder.TenNVBH = (TextView) convertView.findViewById(R.id.listtkbh_item_tenNV);
            holder.GiaGD = (TextView) convertView.findViewById(R.id.listtkbh_item_giaGD);
            convertView.setTag(holder);
        } else {
            holder = (GiaoDichAdapter.ViewHolder) convertView.getTag();
        }

        final GiaoDich gd = this.listData.get(position);
        holder.NgayBH.setText(gd.getDate().toString());
        holder.GiaGD.setText("Thanh toán: " + gd.getService_charge());
        holder.TenNVBH.setText(gd.getStuff());


        return convertView;
    }

    static class ViewHolder {
        TextView NgayBH;
        TextView TenNVBH;
        TextView GiaGD;
    }
}
