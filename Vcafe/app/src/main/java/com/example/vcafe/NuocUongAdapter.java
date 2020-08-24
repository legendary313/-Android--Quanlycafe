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

public class NuocUongAdapter extends BaseAdapter {
    private List<NuocUong> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    DatabaseReference mDatabase;
    public NuocUongAdapter(Context aContext,  List<NuocUong> listData) {
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
        NuocUongAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_nuoc_item, null);
            holder = new NuocUongAdapter.ViewHolder();
            holder.category = (TextView) convertView.findViewById(R.id.listnuoc_item_loainuoc);
            holder.description = (TextView) convertView.findViewById(R.id.listnuoc_item_motaNuoc);
            holder.discountMoney = (TextView) convertView.findViewById(R.id.listnuoc_item_discount);
            holder.name = (TextView) convertView.findViewById(R.id.listnuoc_item_tenNuoc);
            holder.price = (TextView) convertView.findViewById(R.id.listnuoc_item_giaNuoc);
            convertView.setTag(holder);
        } else {
            holder = (NuocUongAdapter.ViewHolder) convertView.getTag();
        }

        final NuocUong nuoc = this.listData.get(position);
        holder.category.setText("Loại: " + nuoc.getCategory());
        holder.price.setText("Giá: " + nuoc.getPrice());
        holder.name.setText(nuoc.getName());
        holder.discountMoney.setText("Discount: " + nuoc.getDiscountMoney());
        holder.description.setText(nuoc.getDescription());

        ImageView btnSuaXoaNuoc = (ImageView) convertView.findViewById(R.id.nut_suaxoa_nuoc);
        btnSuaXoaNuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popup_sua:
                                Intent intent = new Intent(context,QLNuocUong_sua_nuoc.class);
                                intent.putExtra("NuocUong",nuoc);
                                context.startActivity(intent);
                                return true;
                            case R.id.popup_xoa:
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("ITEM").child(nuoc.getId()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());
                popup.show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView category;
        TextView description;
        TextView name;
        TextView discountMoney;
        TextView price;
    }

}
