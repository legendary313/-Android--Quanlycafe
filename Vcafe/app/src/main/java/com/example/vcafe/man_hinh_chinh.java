package com.example.vcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vcafe.order.ListOrderActivity;

import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.TableActivity;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class man_hinh_chinh extends AppCompatActivity {
    private DrawerLayout menu_draw;
    private Button nut_dang_xuat, nut_chuc_nang_tao_tai_khoan, nut_chuc_nang_thong_ke, nut_chuc_nang_order, nut_chuc_nang_quan_ly_chung, nut_chuc_nang_quan_ly_ban;
    private TextView textTenNV,textLoaiNV;
    private FirebaseAuth auth;
    private TaiKhoan tk = new TaiKhoan();
    private NhanVien nv = new NhanVien();
    private String currentuser;
    DatabaseReference myDatabase;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        nut_dang_xuat = (Button) findViewById(R.id.nut_dang_xuat);
        nut_chuc_nang_tao_tai_khoan = (Button) findViewById(R.id.nut_chuc_nang_tao_tai_khoan);
        nut_chuc_nang_order = (Button) findViewById(R.id.nut_chuc_nang_order);
        nut_chuc_nang_quan_ly_ban = (Button) findViewById(R.id.nut_chuc_nang_quan_ly_ban);
        nut_chuc_nang_quan_ly_chung = (Button) findViewById(R.id.nut_chuc_nang_quan_ly_chung);
        nut_chuc_nang_thong_ke = (Button) findViewById(R.id.nut_chuc_nang_thong_ke);





        //authentication
        auth = FirebaseAuth.getInstance();

        currentuser = auth.getInstance().getCurrentUser().getUid();

        myDatabase = FirebaseDatabase.getInstance().getReference();
        myDatabase.child("TaiKhoan").child(currentuser.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    tk = snapshot.getValue(TaiKhoan.class);

                    HienThiThongTinCaNhan(tk);
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Load Database TaiKhoan Failed !!!", Toast.LENGTH_SHORT).show();
            }
        });



        // đăng xuất khỏi tài khoản hiện hành
        nut_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangxuat();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setIcon(R.drawable.home_large);
        actionbar.setDisplayShowTitleEnabled(false);
        menu_draw = findViewById(R.id.drawer_layout);
        menu_draw = findViewById(R.id.drawer_layout);





        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        menu_draw.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
        menu_draw.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                    }

                    @Override
                    public void onDrawerOpened(@NonNull View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(@NonNull View drawerView) {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                }
        );

        //Setting cac nut chuc nang nhan vien
        nut_chuc_nang_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrderActivity();
            }
        });


        //Setting cac nut chuc nang admin
        nut_chuc_nang_tao_tai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDangkyActivity();
            }
        });

        //Setting cac nut chuc nang nhan vien
        nut_chuc_nang_quan_ly_ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTableActivity();
            }
        });

    }

    private void openTableActivity() {
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);
    }

    private void openOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    private void HienThiThongTinCaNhan(TaiKhoan tk) {
        //Lay thong tin ca nhan user
        myDatabase.child("NhanVien").child(tk.getMaNV()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot != null) {
                        nv = snapshot.getValue(NhanVien.class);
                        String loaiNV = nv.getLoaiNV();
                        //Thong Tin Hien thi
                        if(loaiNV.equals("01")) {
                            textLoaiNV = (TextView) findViewById(R.id.textLoaiTK);
                            textLoaiNV.setText("Admin");
                            nut_chuc_nang_tao_tai_khoan.setEnabled(true);
                            nut_chuc_nang_quan_ly_chung.setEnabled(true);
                            nut_chuc_nang_thong_ke.setEnabled(true);
                        }
                        else
                        {
                            textLoaiNV = (TextView) findViewById(R.id.textLoaiTK);
                            textLoaiNV.setText("Nhân Viên");
                            nut_chuc_nang_tao_tai_khoan.setEnabled(false);
                            nut_chuc_nang_quan_ly_chung.setEnabled(false);
                            nut_chuc_nang_thong_ke.setEnabled(false);
                        }
                        textTenNV = (TextView)findViewById(R.id.textTenNV);
                        textTenNV.setText(nv.getTenNV());
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Load Database NhanVien Failed !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dangxuat() {
        auth.signOut();
        startActivity(new Intent(man_hinh_chinh.this, MainActivity.class));
        finish();
        Toast.makeText(getApplicationContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
    }

    private void openDangkyActivity() {
        Intent intent = new Intent(this,dang_ky.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menu_draw.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}