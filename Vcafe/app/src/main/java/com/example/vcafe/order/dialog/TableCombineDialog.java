package com.example.vcafe.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.vcafe.R;
import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.TableActivity;
import com.example.vcafe.order.model.Child;
import com.example.vcafe.order.model.Item;
import com.example.vcafe.order.model.OrderItem;
import com.example.vcafe.order.model.Table;
import com.example.vcafe.order.model.TableOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TableCombineDialog extends Dialog {
    private List<Table> tables;
    private ArrayAdapter adapter;
    private Table currentTable;
    int currentStatus;
    List<OrderItem> currentOrders=new ArrayList<>();
    List<OrderItem> chossenOrders=new ArrayList<>();

    private Button btnCancle,btnOke;
    private TextView txtTableName;
    private Spinner spinner;

    public TableCombineDialog(@NonNull Context context,List<Table> tables,Table currentTable)  {
        super(context);
        setContentView(R.layout.dialog_table_combine);
        this.tables=tables;
        this.currentTable=currentTable;
        currentStatus=currentTable.getStatus();


    }
    public void setUp(){
        show();
        if (tables.size()==0){
            TableActivity.updateTableStatus(currentTable.getKey(),currentStatus);
            Toast.makeText(getContext(),"HIỆN CÁC BÀN ĐANG BẬN!",Toast.LENGTH_SHORT).show();
            dismiss();
        }
        for(int i=0;i<tables.size();i++){
            TableActivity.updateTableStatus(tables.get(i).getKey(),Table.STATUS_PROCESSING);
        }




        btnCancle=(Button)findViewById(R.id.btn_cancle);
        btnOke=(Button)findViewById(R.id.btn_oke);
        txtTableName=(TextView)findViewById(R.id.txtv_table_name);
        spinner=(Spinner)findViewById(R.id.spn_table);
        txtTableName.setText(currentTable.getName());
        adapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,tables);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Table chossen= (Table) spinner.getSelectedItem();

                chossenOrders.clear();
                DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
                myRef.child(Child.FB_ROOT_TABLE_ORDER).child(chossen.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TableOrder tableOrder=snapshot.getValue(TableOrder.class);
                        chossenOrders=tableOrder.getOrders();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                TableActivity.updateTableStatus(currentTable.getKey(),currentStatus);
                for(int i=0;i<tables.size();i++){
                    TableActivity.updateTableStatus(tables.get(i).getKey(),tables.get(i).getStatus());
                }

                dismiss();
            }
        });

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Table chosenTable= (Table) spinner.getSelectedItem();
                int chosenPossition=spinner.getSelectedItemPosition();
                  DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
                //Gọp lại
                    //Thêm order hiện tại vào chosen Order

                   myRef.child(Child.FB_ROOT_TABLE_ORDER).child(currentTable.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           TableOrder tableOrder=snapshot.getValue(TableOrder.class);
                           if(tableOrder!=null){
                               currentOrders=tableOrder.getOrders();
                           }

                           Log.i("HAHAHA",tableOrder.getOrders().size()+"_"+tableOrder.getTableKey());
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });


                    //Xóa order
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            TableOrder tableOrder=new TableOrder();
                            tableOrder.setTableKey(currentTable.getKey());
                            DatabaseReference myRef1=FirebaseDatabase.getInstance().getReference();
                            myRef1.child(Child.FB_ROOT_TABLE_ORDER).child(tableOrder.getTableKey()).setValue(tableOrder);

                            //Chuyển/gọp các order hiện tại

                            for(int i=0;i<currentOrders.size();i++){
                                chossenOrders.add(currentOrders.get(i));

                            }
                            myRef1.child(Child.FB_ROOT_TABLE_ORDER).child(chosenTable.getKey()).child("orders").setValue(chossenOrders);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                //Mấy cái khác
                for(int i=0;i<tables.size();i++){
                    if(i!=chosenPossition){
                        TableActivity.updateTableStatus(tables.get(i).getKey(),tables.get(i).getStatus());
                    }
                }

                //đổi lại bàn đang chọn
                TableActivity.updateTableStatus(currentTable.getKey(),Table.STATUS_AVAILABLE);

                //Đổi lại bàn tới
                TableActivity.updateTableStatus(chosenTable.getKey(),Table.STATUS_UNFINISHED);
                dismiss();
            }
        });
    }




}
