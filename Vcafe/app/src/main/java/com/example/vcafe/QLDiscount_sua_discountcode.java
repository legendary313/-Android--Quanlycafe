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

public class QLDiscount_sua_discountcode extends AppCompatActivity {
    private EditText sua_tiengiam, sua_phantramgiam, sua_code;
    private Button nutXacNhanSuaDiscountCode;
    private  String keyDiscountCode;
    private DiscountCode discountCodeSua;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_discount_sua_discountcode);

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
        sua_tiengiam = (EditText) findViewById(R.id.sua_discountcode_tien_giam);
        sua_phantramgiam = (EditText) findViewById(R.id.sua_discountcode_phantram_giam);
        sua_code = (EditText) findViewById(R.id.sua_discountcode_code);
        nutXacNhanSuaDiscountCode = (Button) findViewById(R.id.nut_xac_nhan_sua_discountcode);

        //lay thong tin sua
        final Intent intent = getIntent();
        discountCodeSua = (DiscountCode) intent.getSerializableExtra("DiscountCode");
        if (discountCodeSua != null) {
            sua_tiengiam.setText(Integer.toString(discountCodeSua.getByMoney()));
            sua_phantramgiam.setText(Integer.toString(discountCodeSua.getByPercent()));
            sua_code.setText(discountCodeSua.getCode());
        } else {
            Toast.makeText(this, "Lỗi khi load dữ liệu nhân viên", Toast.LENGTH_LONG).show();
        }

        mDatabase.child("DISCOUNT").child("CODE").orderByChild("code").equalTo(discountCodeSua.getCode()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren())
                    keyDiscountCode = childSnapshot.getKey();}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nutXacNhanSuaDiscountCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DiscountCode discountCodeMoi = new DiscountCode();
                discountCodeMoi.setCode(sua_code.getText().toString());
                if (!sua_tiengiam.getText().toString().equals("")) {
                    discountCodeMoi.setByMoney(Integer.parseInt(sua_tiengiam.getText().toString()));
                } else {
                    discountCodeMoi.setByMoney(0);
                }
                if (!sua_phantramgiam.getText().toString().equals("")) {
                    discountCodeMoi.setByPercent(Integer.parseInt(sua_phantramgiam.getText().toString()));
                } else {
                    discountCodeMoi.setByPercent(0);
                }

                mDatabase.child("DISCOUNT").child("CODE").child(keyDiscountCode).setValue(discountCodeMoi);

                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
            }
        });
    }
}