package com.example.order;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Menu_Fragment extends Fragment implements Menu_Adapter.OnItemOrderClickListener{
    MainActivity main;
    Context context = null;
    private  java.util.List<Drink_Item> list;
    private RecyclerView menu_view;
    private Menu_Adapter menu_adapter;

    public Menu_Fragment(java.util.List<Drink_Item> list) {
        this.list = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity(); // use this reference to invoke main callbacks
            main = (MainActivity) getActivity();

        } catch (IllegalStateException e) {
            throw new IllegalStateException(
                    "MainActivity must implement callbacks");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.menu,container,false);

        menu_view =(RecyclerView)view.findViewById(R.id.menu);


        menu_adapter=new Menu_Adapter(getContext(), list, this);

        menu_view.setAdapter(menu_adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(), 3);
        menu_view.setAdapter(menu_adapter);
        menu_view.setLayoutManager(gridLayoutManager);
        return view;
    }


    @Override
    public void onClick(int position) {
        MainActivity.addToCart(list.get(position));


    }

    @Override
    public void onLongClick(int position) {

    }
}
