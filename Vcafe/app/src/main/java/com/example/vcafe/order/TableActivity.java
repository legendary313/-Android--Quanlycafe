package com.example.vcafe.order;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.vcafe.R;
import com.example.vcafe.man_hinh_chinh;
import com.example.vcafe.order.adapter.TableRecyclerViewAdapter;

import com.example.vcafe.order.dialog.TableCombineDialog;
import com.example.vcafe.order.model.Child;

import com.example.vcafe.order.model.Table;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import static com.example.vcafe.R.*;


public class TableActivity extends AppCompatActivity implements TableRecyclerViewAdapter.OnItemTableClickListener {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference tableList;
    List<Table> tables=new ArrayList<>();
    private TableRecyclerViewAdapter adapter;

    String currentTable;
    int currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(layout.table_activity);


        //init firebase
        database=FirebaseDatabase.getInstance();
        tableList=database.getReference().child(Child.FB_ROOT_TABLE);

        recyclerView=(RecyclerView)findViewById(id.rycl_table);

        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new TableRecyclerViewAdapter(tables,getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
        loadData();

    }

    private void loadData(){
        final ProgressDialog mDialog= new ProgressDialog(this);
        mDialog.setTitle("Đang tải các bàn");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        Log.i("CHECK-DATA","HELLO!");
        DatabaseReference myRef=database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
              adapter.notifyDataSetChanged();
              mDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        myRef.child(Child.FB_ROOT_TABLE).addChildEventListener(new ChildEventListener() {



            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String str=String.valueOf( snapshot.getKey());
                Table data=snapshot.getValue(Table.class);
                data.setKey(str);

               tables.add(data);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Table data=snapshot.getValue(Table.class);
                for(int i=0;i<tables.size();i++){
                    if(tables.get(i).getName().equals(data.getName())){
                        tables.get(i).setStatus(data.getStatus());
                        tables.get(i).setLastChange(data.getLastChange());
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onClick(int position) {
        if(tables.get(position).getStatus()==Table.STATUS_PROCESSING){

            Toast.makeText(getApplicationContext(),"Bàn đang bận!",Toast.LENGTH_SHORT).show();
            return;
        }
        updateTableStatus(tables.get(position).getKey(),Table.STATUS_PROCESSING);
        openOrder(position);

    }

    @Override
    public void onLongClick(int position) {
        List<Table> tablesAvaiable=new ArrayList<>();
        if(tables.get(position).getStatus()==Table.STATUS_PROCESSING){

            Toast.makeText(getApplicationContext(),"Bàn đang bận!",Toast.LENGTH_SHORT).show();
            return;
        }


        updateTableStatus(tables.get(position).getKey(),Table.STATUS_PROCESSING);
        for (int i=0;i<tables.size();i++){
            if(tables.get(i).getStatus()!=Table.STATUS_PROCESSING &&i!=position){
                Table iTable=new Table();
                iTable.setKey(tables.get(i).getKey());
                iTable.setName(tables.get(i).getName());
                iTable.setStatus(tables.get(i).getStatus());
                tablesAvaiable.add(iTable);



            }
        }
        TableCombineDialog mDialog=new TableCombineDialog(this,tablesAvaiable,tables.get(position));
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mDialog.setUp();
        mDialog.show();

    }

    void openOrder(int position){
        Intent intent=new Intent(this,OrderActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("TABLE_NAME",tables.get(position).getName());
        bundle.putString("TABLE_KEY",tables.get(position).getKey());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public static void updateTableStatus(String tableKey,int status){
        final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
        myRef.child(Child.FB_ROOT_TABLE).child(tableKey).child("lastChange").setValue(man_hinh_chinh.nhanviensudung);
        myRef.child(Child.FB_ROOT_TABLE).child(tableKey).child("status").setValue(status);

    }
}
