package com.example.vcafe.order;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcafe.R;
import com.example.vcafe.order.adapter.MenuRecyclerViewAdapter;
import com.example.vcafe.order.dialog.ItemProfileDialog;
import com.example.vcafe.order.fragment.MenuFragment;
import com.example.vcafe.order.model.Data;
import com.example.vcafe.order.model.Item;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchItemActivity extends AppCompatActivity implements MenuRecyclerViewAdapter.OnItemOrderClickListener{
    private EditText editText;


    private final long DELAY = 500 ; // milliseconds
    Boolean isSearch=false;


    public static List<Item> list_search=new ArrayList<>();
    private RecyclerView menu_view;
    private MenuRecyclerViewAdapter menu_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        editText=(EditText)findViewById(R.id.edt);
        menu_view=(RecyclerView) findViewById(R.id.vinh);



        menu_adapter=new MenuRecyclerViewAdapter(this,list_search,this);

        menu_view.setAdapter(menu_adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 2);
        menu_view.setAdapter(menu_adapter);
        menu_view.setLayoutManager(gridLayoutManager);


        editText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //ghi luôn cái edt hiện tại -1

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //ghi luôn cái edt hiện tại


            }

            @Override
            public void afterTextChanged(final Editable editable) {
                //ghi luôn cái edt hiện tại
//                String myStr="CÀ PHÊ SỮA ĐÁ";
//                String subStr=editText.getText().toString().toUpperCase();
//                Log.i("CHECK-DATA",String.format("Đây là vị trí %d",myStr.indexOf(subStr) ));
                search2();

    }
});

    }


    void search2(){
        AsyncTask<Void, Void,Void> z=new AsyncTask<Void, Void, Void>() {
         List<Item> list=new ArrayList<>();
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String str=editText.getText().toString().toUpperCase();
                    if(!isSearch && !str.matches("") &&str.length()>2 ){
                        isSearch=true;
                        Thread.sleep(DELAY);

                        list.clear();
                        Log.i("CHECK-DATA",String.format("DATA là %s",str ));
                        for(int i=0;i< Data.getMenu().size();i++){
                            for(int j=0;j<Data.getMenu().get(i).getItems().size();j++){

                                if(Data.getMenu().get(i).getItems().get(j).getName().indexOf(str)>-1){
                                    Log.i("CHECK-DATA","CÓ RỒI: "+Data.getMenu().get(i).getItems().get(j).getName());
                                    list.add(Data.getMenu().get(i).getItems().get(j));
                                }

                            }

                        }
                        isSearch=false;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute( final Void result ) {
                // continue what you are doing...
                if(list.size()==0){

                }else {
                    list_search.clear();
                    for(int i=0;i<list.size();i++){
                        list_search.add(list.get(i));
                    }
                    Log.i("CHECK-DATAAaaaa","LISST SIZE: "+list_search.size());
                    menu_adapter.notifyDataSetChanged();
                }

            }
        };
        z.execute();
    }

    @Override
    public void onClick(int position) {
        MenuFragment.addToCart(list_search.get(position));
        Toast.makeText(this,String.format("+1 %s vào giỏ",list_search.get(position).getName()),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongClick(int position) {
        ItemProfileDialog itemProfileDialog=new ItemProfileDialog(this,list_search.get(position));
        itemProfileDialog.setUp();
        itemProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        itemProfileDialog.show();

    }
}