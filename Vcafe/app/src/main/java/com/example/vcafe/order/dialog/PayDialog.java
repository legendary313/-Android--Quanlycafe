package com.example.vcafe.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.vcafe.R;
import com.example.vcafe.order.OrderActivity;
import com.example.vcafe.order.model.Child;
import com.example.vcafe.order.model.Discount;
import com.example.vcafe.order.model.Payed;
import com.example.vcafe.order.model.VieMoney;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class PayDialog extends Dialog  {
    private Payed payed;


    IShutDownOrder iShutDownOrder;
    private EditText edtCode,edtCustomer;
    private Button btnConfirmCode,btnComplete;
    private TextView txtCharge,txtConfirmMessage;

    public PayDialog(@NonNull Context context,IShutDownOrder iShutDownOrder,Payed payed ) {
        super(context);
        this.setContentView(R.layout.dialog_pay);
        this.iShutDownOrder=iShutDownOrder;
        this.payed=payed;
    }

    public void setUp(){
        edtCustomer=(EditText)findViewById(R.id.edit_pay_customer);
        edtCode=(EditText)findViewById(R.id.edit_pay_code);
        btnConfirmCode=(Button)findViewById(R.id.btn_pay_confirm);
        btnComplete=(Button)findViewById(R.id.btn_pay_complete);
        txtCharge=(TextView)findViewById(R.id.txtv_pay_total);
        txtConfirmMessage=(TextView)findViewById(R.id.txtv_pay_code_message);

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lưu dữ liệu lên dtb
                FirebaseDatabase db= FirebaseDatabase.getInstance();
                DatabaseReference myRef=db.getReference();
                payed.setCustomer(edtCustomer.getText().toString());

                myRef.child(Child.FB_ROOT_PAYED).push().setValue(payed);

                Toast.makeText(getContext(),"Đã lưu!",Toast.LENGTH_SHORT).show();

                //tắt activity order
                OrderActivity.orders.clear();
                iShutDownOrder.shutDown();

            }
        });
        btnConfirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=edtCode.getText().toString();
                float condition= Discount.checkCode(code);
                if (condition<0){
                    txtConfirmMessage.setText("Mã của bạn không hợp lê");
                }else {
                    edtCode.setEnabled(false);
                    btnConfirmCode.setEnabled(false);
                    if(condition>1){
                        payed.setService_charge(payed.getService_charge()-(int)(condition));
                        txtConfirmMessage.setText("Mã của bạn được giảm "+new VieMoney().change((int)condition).toString());
                    }else {
                        payed.setService_charge((int)(payed.getService_charge()*(1-condition)));
                        txtConfirmMessage.setText("Mã của bạn được giảm "+(int)condition*100 +"%");
                    }
                    txtCharge.setText(payed.getService_charge()+"");

                }

                //sửa lại view và payed
            }
        });
        txtCharge.setText(payed.getService_charge()+"");

    }



    public interface IShutDownOrder{

        void shutDown();
    }
}
