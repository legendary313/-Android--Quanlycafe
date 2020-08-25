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

public class QLDiscount_sua_discountpay extends AppCompatActivity {

    private EditText sua_tiengiam, sua_phantramgiam;
    private Button nutXacNhanSuaDiscountPay;
    private DiscountPay discountPaySua;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_discount_sua_discountpay);

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
        sua_tiengiam = (EditText) findViewById(R.id.sua_discountpay_tien_giam);
        sua_phantramgiam = (EditText) findViewById(R.id.sua_discountpay_phantram_giam);
        nutXacNhanSuaDiscountPay = (Button) findViewById(R.id.nut_xac_nhan_sua_discountpay);

        //lay thong tin sua
        final Intent intent = getIntent();
        discountPaySua = (DiscountPay) intent.getSerializableExtra("DiscountPay");
        if (discountPaySua != null) {
            sua_tiengiam.setText(Integer.toString(discountPaySua.getByMoney()));
            sua_phantramgiam.setText(Integer.toString(discountPaySua.getByPercent()));
        } else {
            Toast.makeText(this, "Lỗi khi load dữ liệu nhân viên", Toast.LENGTH_LONG).show();
        }
        nutXacNhanSuaDiscountPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DiscountPay discountPayMoi = new DiscountPay();
                if (!sua_tiengiam.getText().toString().equals("")) {
                    discountPayMoi.setByMoney(Integer.parseInt(sua_tiengiam.getText().toString()));
                } else {
                    discountPayMoi.setByMoney(0);
                }
                if (!sua_phantramgiam.getText().toString().equals("")) {
                    discountPayMoi.setByPercent(Integer.parseInt(sua_phantramgiam.getText().toString()));
                } else {
                    discountPayMoi.setByPercent(0);
                }
                discountPayMoi.setDeadline(discountPaySua.getDeadline());
                discountPayMoi.setStartDate(discountPaySua.getStartDate());

                mDatabase.child("DISCOUNT").child("DISCOUNT_PAY").setValue(discountPayMoi);

                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

}
