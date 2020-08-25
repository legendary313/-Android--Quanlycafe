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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DiscountCodeAdapter extends BaseAdapter {
    private List<DiscountCode> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    DatabaseReference mDatabase;
    public DiscountCodeAdapter(Context aContext,  List<DiscountCode> listData) {
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
        DiscountCodeAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_discountcode_item, null);
            holder = new DiscountCodeAdapter.ViewHolder();
            holder.tiengiam = (TextView) convertView.findViewById(R.id.listdiscountCode_item_tiengiam);
            holder.percentgiam = (TextView) convertView.findViewById(R.id.listdiscountCode_item_phantramgiam);
            holder.code = (TextView) convertView.findViewById(R.id.listdiscountCode_item_code);
            convertView.setTag(holder);
        } else {
            holder = (DiscountCodeAdapter.ViewHolder) convertView.getTag();
        }

        final DiscountCode discountCode = this.listData.get(position);
        holder.tiengiam.setText("Tiền giảm: " + discountCode.getByMoney());
        holder.percentgiam.setText("Giảm: " + discountCode.getByPercent() + "%");
        holder.code.setText("Code: " + discountCode.getCode());

        ImageView btnSuaXoaDiscountCode = (ImageView) convertView.findViewById(R.id.nut_suaxoa_discountCode);
        btnSuaXoaDiscountCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popup_sua:
                                Intent intent = new Intent(context,QLDiscount_sua_discountcode.class);
                                intent.putExtra("DiscountCode",discountCode);
                                context.startActivity(intent);
                                return true;
                            case R.id.popup_xoa:
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("DISCOUNT").child("CODE").orderByChild("code").equalTo(discountCode.getCode()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot childSnapshot: snapshot.getChildren())
                                            mDatabase.child("DISCOUNT").child("CODE").child(childSnapshot.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

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
        TextView tiengiam;
        TextView percentgiam;
        TextView code;
    }

}

