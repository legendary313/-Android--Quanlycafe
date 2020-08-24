package com.example.vcafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QLNV_them_nv extends AppCompatActivity {

    private EditText them_tennv,them_luongnv;
    private Button nutXacNhanThem;
    private RadioButton r_them_tkAdmin, r_them_tkNV;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_n_v_them_nv);

        //setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionbar.setIcon(R.drawable.management);
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
        them_tennv = (EditText) findViewById(R.id.them_ten_nhan_vien);
        them_luongnv = (EditText) findViewById(R.id.them_luong_nhan_vien);

        r_them_tkAdmin = findViewById(R.id.them_taikhoanAdmin);
        r_them_tkNV = findViewById(R.id.them_taikhoanNhanVien);

        nutXacNhanThem = (Button) findViewById(R.id.nut_xac_nhan_them);


        nutXacNhanThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanVien nvMoi = new NhanVien();
                nvMoi.setTenNV(them_tennv.getText().toString());
                nvMoi.setLuong(Integer.parseInt(them_luongnv.getText().toString()));
                if(r_them_tkAdmin.isChecked())
                {
                    nvMoi.setLoaiNV("01");
                }
                else
                {
                    nvMoi.setLoaiNV("02");
                }
                nvMoi.setMaNV(mDatabase.push().getKey());;
                mDatabase.child("NhanVien").child(nvMoi.getMaNV()).setValue(nvMoi);
                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
            }
        });
    }
}