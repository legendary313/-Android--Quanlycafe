package com.example.vcafe.order;

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


import com.example.vcafe.order.adapter.TableRecyclerViewAdapter;

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
        Log.i("CHECK-DATA","HELLO!");
        DatabaseReference myRef=database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

              adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        myRef.child(Child.FB_ROOT_TABLE).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Table data=snapshot.getValue(Table.class);

               tables.add(data);
                Log.i("CHECK-DATA",data.getName());

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
        final DatabaseReference myRef=database.getReference();
          currentPosition = position;
        tables.get(currentPosition).setStatus(2);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myRef.child(Child.FB_ROOT_TABLE).child(currentTable).setValue(tables.get(currentPosition));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child(Child.FB_ROOT_TABLE).orderByChild("name").equalTo(tables.get(position).getName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                currentTable=snapshot.getKey();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
    public void onLongClick(int position) {

    }
    void c(){
        //Từ Available
            //nếu ai đó click vào thì nó là PROCCESSING
                        //Nếu click vào mà ko làm gì cả thì trở về AVIable //thanh toán
                        //Nếu click vào mà thay đổi đi ra thì trở thành CHANGE
        //TỪ CHANGE
            //Đi ra mà ko thay đổi thì ko có j
            //ĐI ra mà thay đổi thì update CHANGE
            //Thanh toán đổi thành AVAIABLE

    }
}
