package com.example.vcafe.order;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vcafe.R;
import com.example.vcafe.order.adapter.MenuViewpagerAdapter;
import com.example.vcafe.order.model.Calculator;
import com.example.vcafe.order.model.Child;
import com.example.vcafe.order.model.Item;
import com.example.vcafe.order.model.ItemCategory;
import com.example.vcafe.order.model.OrderItem;
import com.example.vcafe.order.model.VieMoney;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOrderActivity extends AppCompatActivity {
    //Load data menu ve
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static List<String> lCategory=new ArrayList<>();
    private static List<ItemCategory> menu=new ArrayList<>();

    private FloatingActionButton fl_btn_add_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_order_activity);
    //Load data
       loadData();

       //find view by id
        fl_btn_add_order=(FloatingActionButton) findViewById(R.id.fl_btn_add_order);
        fl_btn_add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    void addItemToMenu(Item item){

        for(int i=0;i<menu.size();i++){
            if(menu.get(i).getCategory().equals(item.getCategory())){
                menu.get(i).addItem(item);
                break;
            }
        }
    }
    private void loadItems(){
        //Load data

        for(int i=0;i<lCategory.size();i++){
            ItemCategory itemCategory=new ItemCategory();
            itemCategory.setCategory(lCategory.get(i));
            menu.add(itemCategory);


        }
        Log.i("CHECK-DATA-MENU","MENU SIZE IS "+menu.size());
        DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("CHECK-DATA_DONE","Load xong items data");
                for (int i=0;i<menu.size();i++){
                    Log.i("CHECK-DATA",i+". ____"+menu.get(i).getItems().size());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        myRef.child(Child.FB_ROOT_ITEM).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Item item = dataSnapshot.getValue(Item.class);

                addItemToMenu(item);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadData(){
        //Load category

        DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("CHECK-DATA_DONE","Load xong "+lCategory.size()+"items");
                loadItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        myRef.child(Child.FB_ROOT_CATEGORY).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String data=snapshot.getValue(String.class);
                lCategory.add(data);
                Log.i("CHECK-DATA",lCategory.get(lCategory.size()-1));
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

    public static List<ItemCategory> getMenu() {
        return menu;
    }
}

