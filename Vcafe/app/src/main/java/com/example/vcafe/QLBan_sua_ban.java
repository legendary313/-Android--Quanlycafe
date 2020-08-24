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

public class QLBan_sua_ban extends AppCompatActivity {

    private EditText sua_tenban;
    private Button nutXacNhanSuaBan;
    private Ban banSua;
    private  String keyban;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_ban_sua_ban);

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
        sua_tenban = (EditText) findViewById(R.id.sua_ten_ban);

        nutXacNhanSuaBan = (Button) findViewById(R.id.nut_xac_nhan_sua_ban);

        //lay thong tin sua
        final Intent intent = getIntent();
        banSua = (Ban) intent.getSerializableExtra("Ban");
        if(banSua != null)
        {
            sua_tenban.setText(banSua.getName());
        }
        else{
            Toast.makeText(this,"Lỗi khi load dữ liệu nhân viên",Toast.LENGTH_LONG).show();
        }
        mDatabase.child("TABLE").orderByChild("name").equalTo(banSua.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren())
                keyban = childSnapshot.getKey();}

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        nutXacNhanSuaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Ban banMoi = new Ban();
                banMoi.setName(sua_tenban.getText().toString());
                banMoi.setLastChange(man_hinh_chinh.nhanviensudung);
                banMoi.setStatus(banSua.getStatus());

                mDatabase.child("TABLE").child(keyban).setValue(banMoi);

                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
            }
        });
    }

}