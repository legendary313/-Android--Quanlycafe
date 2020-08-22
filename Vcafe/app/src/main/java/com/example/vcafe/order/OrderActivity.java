package com.example.vcafe.order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.vcafe.R;
import com.example.vcafe.order.dialog.PayDialog;
import com.example.vcafe.order.fragment.BillFragment;
import com.example.vcafe.order.fragment.MenuFragment;
import com.example.vcafe.order.fragment.CardFragment;
import com.example.vcafe.order.model.Child;
import com.example.vcafe.order.model.Data;
import com.example.vcafe.order.model.Item;
import com.example.vcafe.order.model.OrderItem;
import com.example.vcafe.order.model.Table;
import com.example.vcafe.order.model.TableOrder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements PayDialog.IShutDownOrder{
    public static String tableName;
    public static String tableKey;
    public final static List<OrderItem> orders=new ArrayList<>();

    BottomNavigationView bottomNavigationView;
    final Fragment fragment1 = new MenuFragment();
    final Fragment fragment2 = new CardFragment();
    final Fragment fragment3 = new BillFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
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


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            tableName=bundle.getString("TABLE_NAME");
            tableKey=bundle.getString("TABLE_KEY");
            loadOrderForTable();
        }else {
            tableName="ORDER";
        }


        //find view

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bt_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.nav_host_fragment, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment,fragment1, "1").commit();




    }

    private void loadOrderForTable() {
        orders.clear();
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
          myRef.child(Child.FB_ROOT_TABLE_ORDER).child(tableKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TableOrder tableOrder=snapshot.getValue(TableOrder.class);
                if(tableOrder!=null){
                    Log.i("ZZZ","ĐÂY LÀ KEY "+ tableOrder.getTableKey()+ " _size: "+tableOrder.getOrders().size());

                    for(int i=0;i<tableOrder.getOrders().size();i++){
                        orders.add(tableOrder.getOrders().get(i));
                        Log.i("ZZZ","ĐÂY LÀ NAME "+ orders.get(i).getName());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_menu:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

                case R.id.navigation_card:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    ((CardFragment)fragment2).updateView();
                    active = fragment2;
                    return true;
                case R.id.navigation_bill:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    ((BillFragment)fragment3).updateView();
                    active = fragment3;
                    return true;
            }
            return false;
        }
    };


    @Override
    public void shutDown() {
        finish();

        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!tableName.equals("ORDER")){
            if(orders.size()!=0){
                TableOrder tableOrder=new TableOrder();
                tableOrder.orders=orders;
                tableOrder.setTableKey(tableKey);

                DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
                myRef.child("TABLE_ORDER").child(tableKey).setValue(tableOrder);
                TableActivity.updateTableStatus(tableKey,Table.STATUS_UNFINISHED);
                orders.clear();
                return;
            }
            orders.clear();
            TableOrder tableOrder=new TableOrder();
            tableOrder.orders=orders;
            tableOrder.setTableKey(tableKey);
            DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
            myRef.child("TABLE_ORDER").child(tableKey).setValue(tableOrder);
            TableActivity.updateTableStatus(tableKey,Table.STATUS_AVAILABLE);



            orders.clear();
        }

    }
}