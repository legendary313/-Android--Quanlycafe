package com.example.vcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QLDiscount_them_discountcode extends AppCompatActivity {
    private EditText them_tiengiam, them_phantramgiam, them_code;
    private Button nutXacNhanThemDiscountCode;
    private  String keyDiscountCode;
    private DiscountCode discountCodeThem;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_discount_them_discountcode);

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

        //setup database
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //setup layout
        them_tiengiam = (EditText) findViewById(R.id.them_discountcode_tien_giam);
        them_phantramgiam = (EditText) findViewById(R.id.them_discountcode_phantram_giam);
        them_code = (EditText) findViewById(R.id.them_discountcode_code);
        nutXacNhanThemDiscountCode = (Button) findViewById(R.id.nut_xac_nhan_them_discountcode);



        nutXacNhanThemDiscountCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DiscountCode discountCodeMoi = new DiscountCode();
                discountCodeMoi.setCode(them_code.getText().toString());
                if (!them_tiengiam.getText().toString().equals("")) {
                    discountCodeMoi.setByMoney(Integer.parseInt(them_tiengiam.getText().toString()));
                } else {
                    discountCodeMoi.setByMoney(0);
                }
                if (!them_phantramgiam.getText().toString().equals("")) {
                    discountCodeMoi.setByPercent(Integer.parseInt(them_phantramgiam.getText().toString()));
                } else {
                    discountCodeMoi.setByPercent(0);
                }

                mDatabase.child("DISCOUNT").child("CODE").child(mDatabase.push().getKey()).setValue(discountCodeMoi);

                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
            }
        });
    }
}