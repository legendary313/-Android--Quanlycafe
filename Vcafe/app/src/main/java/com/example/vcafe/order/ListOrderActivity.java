package com.example.vcafe.order;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.viewpager.widget.ViewPager;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vcafe.R;
import com.example.vcafe.order.adapter.MenuViewpagerAdapter;
import com.example.vcafe.order.model.Calculator;
import com.example.vcafe.order.model.Child;
import com.example.vcafe.order.model.Data;
import com.example.vcafe.order.model.Item;
import com.example.vcafe.order.model.ItemCategory;
import com.example.vcafe.order.model.OrderItem;
import com.example.vcafe.order.model.VieMoney;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOrderActivity extends AppCompatActivity {
    //Load data menu ve



    private FloatingActionButton fl_btn_add_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_order_activity);

        final ProgressDialog mDialog= new ProgressDialog(this);
        mDialog.setTitle("Đang tải MENU");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    //Load data
        AsyncTask asyncTask=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                while (!Data.isLoaded){}
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                mDialog.dismiss();
            }
        };

        asyncTask.execute();
       if(Data.getMenu().size()==0||Data.getMenu()==null){
           Data.loadData();
       }

       //find view by id
        fl_btn_add_order=(FloatingActionButton) findViewById(R.id.fl_btn_add_order);
        fl_btn_add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }




}

