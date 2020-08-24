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

public class Quan_ly_ban extends AppCompatActivity {
    ListView listviewBan;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference myDatabase;
    ImageView nut_them_ban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_ban);

        nut_them_ban = (ImageView) findViewById(R.id.nut_them_ban);
        nut_them_ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Quan_ly_ban.this,QLBan_them_ban.class));
            }
        });

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

        //setup listview
        listviewBan = (ListView)findViewById(R.id.listBan);
        final ArrayList<Ban> listBan = new ArrayList<Ban>();
        final BanAdapter BanArrayAdapter
                = new BanAdapter(this,  listBan);
        listviewBan.setAdapter(BanArrayAdapter);

        //authentication and database
        auth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference();

        myDatabase.child("TABLE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBan.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    listBan.add(postSnapshot.getValue(Ban.class)) ;
                }
                BanArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}