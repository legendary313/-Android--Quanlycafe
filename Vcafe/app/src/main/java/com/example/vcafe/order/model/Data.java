package com.example.vcafe.order.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vcafe.order.ListOrderActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Data {
    public static boolean isLoaded=false;
    private static List<String> lCategory=new ArrayList<>();
    private static List<ItemCategory> menu=new ArrayList<>();


    public static PayDiscount payDiscount=new PayDiscount();


   private static void addItemToMenu(Item item){

        for(int i=0;i<menu.size();i++){
            if(menu.get(i).getCategory().equals(item.getCategory())){
                menu.get(i).addItem(item);
                break;
            }
        }
    }
    private  static void loadItems(){
        //Load data


        for(int i=0;i<lCategory.size();i++){
            ItemCategory itemCategory=new ItemCategory();
            itemCategory.setCategory(lCategory.get(i));
            menu.add(itemCategory);

        }

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("CHECK-DATA","LOAD XONG");
                isLoaded=true;
                Log.i("CHECK-DATA","LOAD XONG "+isLoaded);
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

    public static void loadData(){
       loadDiscount();

       loadMenu();
    }

    private static void loadDiscount() {
        final  DatabaseReference  myRef=FirebaseDatabase.getInstance().getReference();

    myRef.child(Child.FB_ROOT_DISCOUNT).child("DISCOUNT_PAY").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            PayDiscount payDiscount1=snapshot.getValue(PayDiscount.class);
            payDiscount1.setStartDate(new Date());
            payDiscount1.setDeadline(new Date());

            payDiscount=payDiscount1;
            myRef.child(Child.FB_ROOT_DISCOUNT).child("DISCOUNT_PAY").setValue(payDiscount);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

   }

    private static void loadMenu( ){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
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
