package com.example.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

public class Menu_Fragment extends Fragment {
    private  java.util.List<Drink_Item> List;
    private GridView gridView;
    private Menu_Adapter menu_adapter;

    public Menu_Fragment(java.util.List<Drink_Item> list) {
        List = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.menu_item,container,false);

        gridView=(GridView)view.findViewById(R.id.menu);

        menu_adapter=new Menu_Adapter(getContext(),R.layout.drink_item,List);

        gridView.setAdapter(menu_adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getContext(), "Đây là: " + List.get(position).getTen(), Toast.LENGTH_SHORT).show();
                MainActivity.dsOrder.add(List.get(position));

                Toast.makeText(getContext(), "Đây là: " +  MainActivity.dsOrder.size(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
