package com.example.vcafe.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.vcafe.R;
import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.fragment.BillFragment;
import com.example.vcafe.order.model.Bill;
import com.example.vcafe.order.model.Calculator;
import com.example.vcafe.order.model.OrderItem;
import com.example.vcafe.order.model.VieMoney;

import java.util.Date;
import java.util.List;

public class BillRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private List<OrderItem> items;
    private static int TYPE_HEADER = 1;
    private static int TYPE_BODY = 2;
    private static int TYPE_FOOTER = 3;
    public BillRecyclerViewAdapter(Context context, List<OrderItem> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getItemViewType(int position) {
        if (position==0) {
            return TYPE_HEADER;

        } else if (position==items.size()+1){
            return TYPE_FOOTER;
        }
        return TYPE_BODY;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == TYPE_HEADER) { // for call layout
            view = LayoutInflater.from(context).inflate(R.layout.bill_item_header, parent, false);
            return new ViewHolderHeader(view);

        }else if (viewType==TYPE_FOOTER){
            view = LayoutInflater.from(context).inflate(R.layout.bill_item_footer, parent, false);
            return new ViewHolderFooter(view);

        }else {
            view = LayoutInflater.from(context).inflate(R.layout.bill_item, parent, false);
            return new ViewHolderBody(view);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(getItemViewType(position)==TYPE_HEADER){
            ViewHolderHeader holder2=(ViewHolderHeader) holder;
            holder2.kieuOrder.setText("Bàn số: "+"ORDER");
            holder2.tenNhanVien.setText("Nhân viên: "+"Trà mie");

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            BillFragment.date=df.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date()).toString();



            android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date());

            holder2.ngayOrder.setText("Thời gian: "+BillFragment.date);

        }else if(getItemViewType(position)==TYPE_FOOTER){
            ViewHolderFooter holder3=(ViewHolderFooter) holder;
            holder3.giamGia.setText(  String.format("%s",new VieMoney().change(Calculator.totalMoney(OrderActivity.orders))));
            holder3.tongTien.setText(  String.format("%s",new VieMoney().change(Calculator.totalMoney(OrderActivity.orders))));
            BillFragment.total=Calculator.totalMoney(OrderActivity.orders);

        }
        else {
            ViewHolderBody holder1=(ViewHolderBody) holder;
            OrderItem item=items.get(position-1);


            TextView ten=holder1.ten;
            TextView gia=holder1.gia;
            TextView soLuong=holder1.soLuong;
            TextView tongTien=holder1.tongTien;


            ten.setText(item.getName());
            gia.setText(new VieMoney().change(item.getPrice()));
            soLuong.setText("x"+item.getQuantity());

            tongTien.setText(new VieMoney().change(item.getPrice()*item.getQuantity()));

        }


    }

    @Override
    public int getItemCount() {
        return items.size()+2;
    }


    public class ViewHolderBody extends ViewHolder   {

        private TextView ten;
        private TextView gia;
        private TextView soLuong;
        private TextView tongTien;
        CardRecyclerViewAdapter.OnItemOrderClickListener onItemOrderClickListener;

        public ViewHolderBody(View itemView ) {
            super(itemView);

            ten=(TextView)itemView.findViewById(R.id.txtv_bill_item_name);
            gia=(TextView)itemView.findViewById(R.id.txtv_bill_item_price);
            soLuong=(TextView)itemView.findViewById(R.id.txtv_bill_item_quantity);
            tongTien=(TextView)itemView.findViewById(R.id.txtv_bill_item_total);

        }

    }
    public class ViewHolderHeader extends ViewHolder   {

        private TextView tenNhanVien;
        private TextView kieuOrder;
        private TextView ngayOrder;

        CardRecyclerViewAdapter.OnItemOrderClickListener onItemOrderClickListener;

        public ViewHolderHeader(View itemView ) {
            super(itemView);

            tenNhanVien=(TextView)itemView.findViewById(R.id.txtv_bill_staff_order);
            kieuOrder=(TextView)itemView.findViewById(R.id.txtv_bill_type_order);
            ngayOrder=(TextView)itemView.findViewById(R.id.txtv_bill_date_order);


        }

    }
    public class ViewHolderFooter extends ViewHolder   {

        private TextView tongTien;
        private TextView giamGia;
        CardRecyclerViewAdapter.OnItemOrderClickListener onItemOrderClickListener;

        public ViewHolderFooter(View itemView ) {
            super(itemView);


            tongTien=(TextView)itemView.findViewById(R.id.txtv_bill_total_order);
            giamGia =(TextView)itemView.findViewById(R.id.txtv_bill_discount);
        }

    }

}
