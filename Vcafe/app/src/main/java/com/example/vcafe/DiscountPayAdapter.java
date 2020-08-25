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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.vcafe.R.menu.popup_menu_2;

public class DiscountPayAdapter extends BaseAdapter {
    private List<DiscountPay> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    DatabaseReference mDatabase;
    public DiscountPayAdapter(Context aContext,  List<DiscountPay> listData) {
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
        DiscountPayAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_discountpay_item, null);
            holder = new DiscountPayAdapter.ViewHolder();
            holder.tiengiam = (TextView) convertView.findViewById(R.id.listdiscountPay_item_tiengiam);
            holder.percentgiam = (TextView) convertView.findViewById(R.id.listdiscountPay_item_phantramgiam);
            convertView.setTag(holder);
        } else {
            holder = (DiscountPayAdapter.ViewHolder) convertView.getTag();
        }

        final DiscountPay dp = this.listData.get(position);
        holder.tiengiam.setText("Giảm tiền: " + dp.getByMoney());
        holder.percentgiam.setText("Giảm: " + dp.getByPercent() + "%");

        ImageView btnSuaDiscountPay = (ImageView) convertView.findViewById(R.id.nut_sua_discountPay);
        btnSuaDiscountPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popup_sua_2:
                                Intent intent = new Intent(context,QLDiscount_sua_discountpay.class);
                                intent.putExtra("DiscountPay",dp);
                                context.startActivity(intent);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popup.getMenuInflater().inflate(R.menu.popup_menu_2,popup.getMenu());
                popup.show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tiengiam;
        TextView percentgiam;
    }
}
