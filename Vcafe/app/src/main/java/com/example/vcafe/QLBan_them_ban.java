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

public class QLBan_them_ban extends AppCompatActivity {

    private EditText them_tenban;
    private Button nutXacNhanThemBan;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_ban_them_ban);

        //setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionbar.setIcon(R.drawable.manage_table);
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
        them_tenban = (EditText) findViewById(R.id.them_ten_ban);

        nutXacNhanThemBan = (Button) findViewById(R.id.nut_xac_nhan_them_ban);

        nutXacNhanThemBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ban banMoi = new Ban();
                banMoi.setName(them_tenban.getText().toString());
                banMoi.setLastChange(man_hinh_chinh.nhanviensudung);
                banMoi.setStatus(1);

                mDatabase.child("TABLE").child(mDatabase.push().getKey()).setValue(banMoi);
                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
            }
        });
    }
}