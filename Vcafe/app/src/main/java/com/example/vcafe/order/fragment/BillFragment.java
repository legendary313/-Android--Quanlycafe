package com.example.vcafe.order.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcafe.R;
import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.adapter.BillRecyclerViewAdapter;
import com.example.vcafe.order.adapter.CardRecyclerViewAdapter;
import com.example.vcafe.order.dialog.ItemProfileDialog;
import com.example.vcafe.order.dialog.PayDialog;
import com.example.vcafe.order.model.Calculator;
import com.example.vcafe.order.model.IUpdateView;
import com.example.vcafe.order.model.Payed;

public class BillFragment extends Fragment implements IUpdateView {
    private Payed payed=new Payed();
    public static String date;
    public static int total;
    private BillRecyclerViewAdapter adapter;
    private Button btnPay;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.bill_fragment, container, false);
        RecyclerView recyclerView=(RecyclerView)root.findViewById(R.id.rycl_bill);
        adapter=new BillRecyclerViewAdapter(getContext(), OrderActivity.orders );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnPay=(Button)root.findViewById(R.id.btn_bill_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                payed.setDate(date); //format
                payed.setStuff("Trà mie"); //lấy data
                payed.setService_charge(total);
                payed.setCustomer(""); //lấy từ edit text
                PayDialog payDialog=new PayDialog(getContext(),(OrderActivity)getActivity(),payed);
                payDialog.setUp();
                payDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                payDialog.show();
            }
        });

        return root;
    }

    @Override
    public void updateView() {
        adapter.notifyDataSetChanged();
    }
}
