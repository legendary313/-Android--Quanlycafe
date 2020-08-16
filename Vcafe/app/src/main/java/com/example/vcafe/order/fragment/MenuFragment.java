package com.example.vcafe.order.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcafe.R;
import com.example.vcafe.order.ItemProfileDialog;
import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.adapter.MenuRecyclerViewAdapter;
import com.example.vcafe.order.model.Item;

import java.util.List;

public class MenuFragment extends Fragment implements MenuRecyclerViewAdapter.OnItemOrderClickListener{
    OrderActivity main;
    Context context = null;
    private List<Item> list;
    private RecyclerView menu_view;
    private MenuRecyclerViewAdapter menu_adapter;

    public MenuFragment(List<Item> list) {
        this.list = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity(); // use this reference to invoke main callbacks
            main = (OrderActivity) getActivity();

        } catch (IllegalStateException e) {
            throw new IllegalStateException(
                    "MainActivity must implement callbacks");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.menu_fragment,container,false);

        menu_view =(RecyclerView) view.findViewById(R.id.menu);


        menu_adapter=new MenuRecyclerViewAdapter(getContext(), list, this);

        menu_view.setAdapter(menu_adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(), 3);
        menu_view.setAdapter(menu_adapter);
        menu_view.setLayoutManager(gridLayoutManager);
        return view;
    }


    @Override
    public void onClick(int position) {
        OrderActivity.addToCart(list.get(position));


    }

    @Override
    public void onLongClick(int position) {
        ItemProfileDialog itemProfileDialog=new ItemProfileDialog(getContext(),list.get(position));
        itemProfileDialog.setUp();
        itemProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        itemProfileDialog.show();

    }
}
