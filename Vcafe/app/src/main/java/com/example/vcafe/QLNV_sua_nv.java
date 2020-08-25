package com.example.vcafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QLNV_sua_nv extends AppCompatActivity {

    private EditText sua_tennv,sua_luongnv;
    private Button nutXacNhanSua, nutHuySua;
    private RadioButton r_sua_tkAdmin, r_sua_tkNV;
    private NhanVien nhanvienSua;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_n_v_sua_nv);

        //setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionbar.setIcon(R.drawable.manage_staffes);
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
        sua_tennv = (EditText) findViewById(R.id.sua_ten_nhan_vien);
        sua_luongnv = (EditText) findViewById(R.id.sua_luong_nhan_vien);

        r_sua_tkAdmin = findViewById(R.id.sua_taikhoanAdmin);
        r_sua_tkNV = findViewById(R.id.sua_taikhoanNhanVien);

        nutXacNhanSua = (Button) findViewById(R.id.nut_xac_nhan_sua);
        nutHuySua = (Button) findViewById(R.id.nut_huy_sua);

        //lay thong tin sua
        Intent intent = getIntent();
        nhanvienSua = (NhanVien) intent.getSerializableExtra("NhanVien");
        if(nhanvienSua != null)
        {
            sua_tennv.setText(nhanvienSua.getTenNV());
            sua_luongnv.setText(Integer.toString(nhanvienSua.getLuong()));
            if(nhanvienSua.getLoaiNV().equals("01"))
            {
                r_sua_tkAdmin.setChecked(true);
            }
            else r_sua_tkNV.setChecked(true);
        }
        else{
            Toast.makeText(this,"Lỗi khi load dữ liệu nhân viên",Toast.LENGTH_LONG).show();
        }

        nutHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nhanvienSua != null)
                {
                    sua_tennv.setText(nhanvienSua.getTenNV());
                    sua_luongnv.setText(Integer.toString(nhanvienSua.getLuong()));
                    if(nhanvienSua.getLoaiNV().equals("01"))
                    {
                        r_sua_tkAdmin.setChecked(true);
                    }
                    else r_sua_tkNV.setChecked(true);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Lỗi khi load dữ liệu nhân viên",Toast.LENGTH_LONG).show();
                }
            }
        });

        nutXacNhanSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanVien thongtinMoi = new NhanVien();
                thongtinMoi.setTenNV(sua_tennv.getText().toString());
                if(!(sua_luongnv.getText().toString().equals("")))
                thongtinMoi.setLuong(Integer.parseInt(sua_luongnv.getText().toString()));
                if(r_sua_tkAdmin.isChecked())
                {
                    thongtinMoi.setLoaiNV("01");
                }
                else
                {
                    thongtinMoi.setLoaiNV("02");
                }
                thongtinMoi.setMaNV(nhanvienSua.getMaNV());
                mDatabase.child("NhanVien").child(nhanvienSua.getMaNV()).setValue(thongtinMoi);
                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
            }
        });
    }
}