package com.example.vcafe.order;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.vcafe.R;
import com.example.vcafe.order.dialog.PayDialog;
import com.example.vcafe.order.fragment.BillFragment;
import com.example.vcafe.order.fragment.MenuFragment;
import com.example.vcafe.order.fragment.CardFragment;
import com.example.vcafe.order.model.OrderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements PayDialog.IShutDownOrder{
    public static List<OrderItem> orders=new ArrayList<>();

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



        //find view

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bt_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.nav_host_fragment, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment,fragment1, "1").commit();


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
        orders.clear();
    }
}