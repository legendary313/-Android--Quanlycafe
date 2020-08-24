package com.example.vcafe.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import com.example.vcafe.R;
import com.example.vcafe.order.ListOrderActivity;
import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.SearchItemActivity;
import com.example.vcafe.order.adapter.MenuViewpagerAdapter;

import com.example.vcafe.order.model.Calculator;
import com.example.vcafe.order.model.Data;
import com.example.vcafe.order.model.IUpdateView;
import com.example.vcafe.order.model.Item;
import com.example.vcafe.order.model.OrderItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MenuFragment extends Fragment implements IUpdateView {
    private ViewPager viewPager=null;
    private MenuViewpagerAdapter menu_viewpager_adapter;
    private FloatingActionButton floatingActionButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.menu_fragment, container, false);
        //find view by id
        viewPager=(ViewPager)root.findViewById(R.id.vpg_menu_category);
        floatingActionButton=(FloatingActionButton)root.findViewById(R.id.fl_btn_search_item) ;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), SearchItemActivity.class);
                startActivity(intent);
            }
        });
        menu_viewpager_adapter=new MenuViewpagerAdapter(getFragmentManager(), Data.getMenu());
        viewPager.setAdapter(menu_viewpager_adapter);
        TabLayout tabLayout = root.findViewById(R.id.tly_menu_category);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }


    public static void addToCart(Item drink_item){

        int condition= Calculator.validateAddOrder(OrderActivity.orders,drink_item);

        if(condition>=0){
            OrderActivity.orders.get(condition).increase(1);
        }else if(condition==-1){

            OrderActivity.orders.add(new OrderItem(drink_item));
        }


    }

    @Override
    public void updateView() {
        menu_viewpager_adapter.notifyDataSetChanged();
    }
}
