package com.example.vcafe.order.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcafe.R;
import com.example.vcafe.order.dialog.ItemCardDialog;
import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.adapter.CardRecyclerViewAdapter;
import com.example.vcafe.order.model.Calculator;
import com.example.vcafe.order.model.IUpdateView;
import com.example.vcafe.order.model.VieMoney;

public class CardFragment extends Fragment implements CardRecyclerViewAdapter.OnItemOrderClickListener, IUpdateView {
    private CardRecyclerViewAdapter adapter;
    TextView textView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.cart_fragment, container, false);
       textView=(TextView)root.findViewById(R.id.txtv_total_price);

        RecyclerView recyclerView=(RecyclerView)root.findViewById(R.id.danh_sach_order);

        adapter=new CardRecyclerViewAdapter(getContext(), OrderActivity.orders,this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        updateView();




        return root;
    }


    @Override
    public void onClick(int position) {

        xuLyItemDialog2(position);

    }

    @Override
    public void onLongClick(int position) {
         OrderActivity.orders.remove(position);
         updateView();

    }


    public void xuLyItemDialog2(final int position){
        ItemCardDialog itemCardDialog2 =new ItemCardDialog(getContext(),position,this);
        itemCardDialog2.setUp();
        itemCardDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        itemCardDialog2.show();

    }
    @Override
    public void updateView(){
        adapter.notifyDataSetChanged();
        textView.setText(String.format("Tổng tiền : %s",new VieMoney().change(Calculator.totalMoney(OrderActivity.orders))));
    }

}
