package com.example.vcafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Quan_ly_chung extends AppCompatActivity {
    Button nut_chuc_nang_quan_ly_nvien,nut_chuc_nang_tkbh,nut_chuc_nang_qlban,nut_chuc_nang_qlnuoc,nut_chuc_nang_quan_ly_discount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_chung);

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


        //setup nut chuc nang
        nut_chuc_nang_quan_ly_nvien = (Button) findViewById(R.id.nut_chuc_nang_quan_ly_nvien);
        nut_chuc_nang_tkbh = (Button) findViewById(R.id.nut_chuc_nang_thong_ke_ls_banhang);
        nut_chuc_nang_qlban = (Button) findViewById(R.id.nut_chuc_nang_quan_ly_ds_ban);
        nut_chuc_nang_qlnuoc = (Button) findViewById(R.id.nut_chuc_nang_quan_ly_ds_nuoc_uong);
        nut_chuc_nang_quan_ly_discount = (Button) findViewById(R.id.nut_chuc_nang_quan_ly_discount);


        nut_chuc_nang_quan_ly_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuanlyDiscountActivity();
            }
        });
        nut_chuc_nang_qlnuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuanlyNuocActivity();
            }
        });
        nut_chuc_nang_qlban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuanlyBanActivity();
            }
        });

        nut_chuc_nang_tkbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuanlyTKBHActivity();
            }
        });
        nut_chuc_nang_quan_ly_nvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuanlynhanvienActivity();
            }
        });
    }

    private void openQuanlyDiscountActivity() {
        Intent intent = new Intent(this, QuanLyDiscount.class);
        startActivity(intent);
    }

    private void openQuanlyNuocActivity() {
        Intent intent = new Intent(this, Quan_ly_nuoc_uong.class);
        startActivity(intent);
    }

    private void openQuanlyBanActivity() {
        Intent intent = new Intent(this, Quan_ly_ban.class);
        startActivity(intent);
    }

    private void openQuanlyTKBHActivity() {
        Intent intent = new Intent(this, ThongKeBanHang.class);
        startActivity(intent);
    }

    private void openQuanlynhanvienActivity() {
        Intent intent = new Intent(this, Quan_ly_nhan_vien.class);
        startActivity(intent);
    }

}