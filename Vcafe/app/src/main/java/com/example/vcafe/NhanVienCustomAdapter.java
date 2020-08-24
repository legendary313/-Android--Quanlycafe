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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NhanVienCustomAdapter extends BaseAdapter {
    private List<NhanVien> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    DatabaseReference mDatabase;
    public NhanVienCustomAdapter(Context aContext,  List<NhanVien> listData) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_nv_item, null);
            holder = new ViewHolder();
            holder.TenNV = (TextView) convertView.findViewById(R.id.listnv_item_TenNV);
            holder.LoaiNV = (TextView) convertView.findViewById(R.id.listnv_item_loaiNV);
            holder.LuongNV = (TextView) convertView.findViewById(R.id.listnv_item_luongNV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final NhanVien nv = this.listData.get(position);
        holder.TenNV.setText(nv.getTenNV());
        holder.LuongNV.setText("Lương: " + nv.getLuong());
        if(nv.getLoaiNV().equals("01"))
        {
            holder.LoaiNV.setText("Admin");
        }
        else
        {
            holder.LoaiNV.setText("Nhân Viên");
        }

        ImageView btnSuaXoa = (ImageView) convertView.findViewById(R.id.nut_suaxoa_nv);
        btnSuaXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popup_sua:
                                Intent intent = new Intent(context,QLNV_sua_nv.class);
                                intent.putExtra("NhanVien",nv);
                                context.startActivity(intent);
                                return true;
                            case R.id.popup_xoa:
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("NhanVien").child(nv.getMaNV()).removeValue(new DatabaseReference.CompletionListener() {
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
        TextView TenNV;
        TextView LoaiNV;
        TextView LuongNV;
    }

}
