package com.example.vcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyDiscount extends AppCompatActivity {

    ListView listviewDiscountPay,listViewDiscountCode;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference myDatabase;
    ImageView nut_them_discountCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_discount);

        nut_them_discountCode = (ImageView) findViewById(R.id.nut_them_discount_code);
        nut_them_discountCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuanLyDiscount.this,QLDiscount_them_discountcode.class));
            }
        });

        //setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionbar.setIcon(R.drawable.discount_big);
        actionbar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //setup listview Discount Pay
        listviewDiscountPay = (ListView)findViewById(R.id.listDiscountPay);
        final ArrayList<DiscountPay> listDiscountPay = new ArrayList<DiscountPay>();
        final DiscountPayAdapter discountPayArrayAdapter
                = new DiscountPayAdapter(this,  listDiscountPay);
        listviewDiscountPay.setAdapter(discountPayArrayAdapter);

        //setup listview Discount Code
        listViewDiscountCode = (ListView)findViewById(R.id.listDiscountCode);
        final ArrayList<DiscountCode> listDiscountCode = new ArrayList<DiscountCode>();
        final DiscountCodeAdapter discountCodeArrayAdapter
                = new DiscountCodeAdapter(this,  listDiscountCode);
        listViewDiscountCode.setAdapter(discountCodeArrayAdapter);



        //authentication and database
        auth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference();


        //set list discountPay
        myDatabase.child("DISCOUNT").child("DISCOUNT_PAY").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDiscountPay.clear();
                listDiscountPay.add(snapshot.getValue(DiscountPay.class)) ;
                discountPayArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //set list discountCode
        myDatabase.child("DISCOUNT").child("CODE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDiscountCode.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    listDiscountCode.add(postSnapshot.getValue(DiscountCode.class)) ;
                }
                discountCodeArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}