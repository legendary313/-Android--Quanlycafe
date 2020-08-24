package com.example.vcafe;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
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

import java.io.Console;
import java.util.List;

public class BanAdapter extends BaseAdapter {
    private List<Ban> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private String keyban ;
    DatabaseReference mDatabase;
    public BanAdapter(Context aContext,  List<Ban> listData) {
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
        BanAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_ban_item, null);
            holder = new BanAdapter.ViewHolder();
            holder.TenBan = (TextView) convertView.findViewById(R.id.listBan_item_TenBan);
            convertView.setTag(holder);
        } else {
            holder = (BanAdapter.ViewHolder) convertView.getTag();
        }

        final Ban ban = this.listData.get(position);
        holder.TenBan.setText(ban.getName());

        ImageView btnSuaXoaBan = (ImageView) convertView.findViewById(R.id.nut_suaxoa_ban);
        btnSuaXoaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popup_sua:
                                Intent intent = new Intent(context,QLBan_sua_ban.class);
                                intent.putExtra("Ban",ban);
                                context.startActivity(intent);
                                return true;
                            case R.id.popup_xoa:
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("TABLE").orderByChild("name").equalTo(ban.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot childSnapshot: snapshot.getChildren())
                                        mDatabase.child("TABLE").child(childSnapshot.getKey()).removeValue(new DatabaseReference.CompletionListener() {
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
        TextView TenBan;
    }
}
