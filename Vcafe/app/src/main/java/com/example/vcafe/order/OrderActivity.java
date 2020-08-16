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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static List<OrderItem> dsOrder=new ArrayList<>();
    private List<String> lCategory=new ArrayList<>();
    private List<ItemCategory> menu=new ArrayList<>();
    private ViewPager viewPager=null;
    private MenuViewpagerAdapter menu_viewpager_adapter;

    private LinearLayout cart;

    private static TextView  txtSoLuong,txtTongTien;

    public List<OrderItem> getDsOrder() {
        return dsOrder;
    }

    public void setDsOrder(ArrayList<OrderItem> dsOrder) {
        this.dsOrder = dsOrder;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_category);
        dsOrder=new ArrayList<>();
        //Tạo dữ liệu giả
        anhXa();

       loadData();



        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Mo ra ds order",Toast.LENGTH_SHORT).show();
                Intent cartIntent=new Intent(getApplicationContext(),CartActivity.class);
                startActivity(cartIntent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateCard();
    }

    private   static void updateCard(){
        int soLuong=0;
        for(int i=0;i<dsOrder.size();i++){
            soLuong+=dsOrder.get(i).getQuantity();
        }
        int tongTien=0;
        for(int i=0;i<dsOrder.size();i++){
            tongTien+=dsOrder.get(i).getQuantity()*dsOrder.get(i).getPrice();
        }
//
       txtTongTien.setText(tongTien+"");
        txtSoLuong.setText("x"+soLuong);
//




    }

    private void anhXa(){
        menu=new ArrayList<>();
        viewPager=(ViewPager)findViewById(R.id.menu_category);

        txtSoLuong=(TextView)findViewById(R.id.cart_so_luong);
        txtTongTien=(TextView)findViewById(R.id.cart_tong_tien);
        cart=(LinearLayout)findViewById(R.id.cart_in_menu);

    }

    public static void addToCart(Item drink_item){

        int condition= Calculator.validateAddOrder(OrderActivity.dsOrder,drink_item);

        if(condition>=0){
            com.example.vcafe.order.OrderActivity.dsOrder.get(condition).increase(1);
        }else if(condition==-1){

            com.example.vcafe.order.OrderActivity.dsOrder.add(new OrderItem(drink_item));
        }

        updateCard();

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
                menu_viewpager_adapter=new MenuViewpagerAdapter(getSupportFragmentManager(),menu);
                viewPager.setAdapter(menu_viewpager_adapter);
                TabLayout tabLayout = findViewById(R.id.tab_layout);
                tabLayout.setupWithViewPager(viewPager);
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

    void addItemToMenu(Item item){

        for(int i=0;i<menu.size();i++){
            if(menu.get(i).getCategory().equals(item.getCategory())){
                menu.get(i).addItem(item);
                break;
            }
        }
    }
}

