package com.example.vcafe.order.model;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vcafe.order.dialog.PayDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public abstract class Discount {
    private static CodeDiscount codeDiscount=new CodeDiscount();
    protected int byMoney;
    protected int byPercent;
    protected Date startDate;
    protected Date deadline;

    public static void checkCode(final String code, final ICodeDiscount iCodeDiscount ){

        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference(Child.FB_ROOT_DISCOUNT_CODE);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CodeDiscount codeDiscount1=snapshot.getValue(CodeDiscount.class);
                if(codeDiscount1!=null){
                    if(code.equals(codeDiscount1.getCode())){
                        Log.i("HELLO",codeDiscount1.getCode());
                        codeDiscount.setByPercent(codeDiscount1.getByPercent());
                        codeDiscount.setByMoney(codeDiscount1.getByMoney());
                        return;
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


         myRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 iCodeDiscount.getDiscount(code,codeDiscount.byMoney,codeDiscount.byPercent);
                 codeDiscount=new CodeDiscount();

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }

    public abstract int getMoneyDiscout(int money);

    public int getByMoney() {
        return byMoney;
    }

    public void setByMoney(int byMoney) {
        this.byMoney = byMoney;
    }


    public int getByPercent() {
        return  byPercent ;
    }
    public void setByPercent(int byPercent) {
        this.byPercent = byPercent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public interface ICodeDiscount{
        void getDiscount(String code,int money, int percent);
    }
}


