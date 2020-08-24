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

public class Quan_ly_nuoc_uong extends AppCompatActivity {

    ListView listviewNuoc;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference myDatabase;
    ImageView nut_them_nuoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nuoc_uong);

        nut_them_nuoc = (ImageView) findViewById(R.id.nut_them_nuoc);
        nut_them_nuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Quan_ly_nuoc_uong.this,QLNuocUong_them_nuoc.class));
            }
        });

        //setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionbar.setIcon(R.drawable.categories_items);
        actionbar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //setup listview
        listviewNuoc = (ListView)findViewById(R.id.listNuoc);
        final ArrayList<NuocUong> listNuoc = new ArrayList<NuocUong>();
        final NuocUongAdapter NuocArrayAdapter
                = new NuocUongAdapter(this,  listNuoc);
        listviewNuoc.setAdapter(NuocArrayAdapter);

        //authentication and database
        auth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference();

        myDatabase.child("ITEM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listNuoc.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    listNuoc.add(postSnapshot.getValue(NuocUong.class)) ;
                }
                NuocArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}