package com.example.vcafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class QLNuocUong_sua_nuoc extends AppCompatActivity {
    private static int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;

    private String strImg_link = new String();
    private  NuocUong nuocSua;
    private EditText sua_tennuoc,sua_mota,sua_gia_nuoc,sua_discount;
    private Button nutXacNhanSuaNuoc,nutChonSuaAnh;
    private ImageView sua_anh_nuoc;
    private RadioButton r_sua_cafe, r_sua_trasua,r_sua_traicay,r_sua_tratraicay;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;
    StorageReference storageReference;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_nuoc_uong_sua_nuoc);

        //setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionbar.setIcon(R.drawable.categories_items);
        actionbar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //setup database
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //setup layout
        sua_tennuoc = (EditText) findViewById(R.id.sua_ten_nuoc);
        sua_mota = (EditText) findViewById(R.id.sua_mota_nuoc);
        sua_gia_nuoc = (EditText) findViewById(R.id.sua_gia_nuoc);
        sua_discount = (EditText) findViewById(R.id.sua_gia_discount);
        nutXacNhanSuaNuoc = (Button) findViewById(R.id.nut_xac_nhan_sua_nuoc);
        nutChonSuaAnh = (Button) findViewById(R.id.nut_chon_sua_anh);
        sua_anh_nuoc = (ImageView) findViewById(R.id.sua_anh_nuoc);
        r_sua_cafe = (RadioButton) findViewById(R.id.sua_cafe);
        r_sua_trasua = (RadioButton) findViewById(R.id.sua_trsua);
        r_sua_traicay = (RadioButton) findViewById(R.id.sua_traicay);
        r_sua_tratraicay = (RadioButton) findViewById(R.id.sua_tratraicay);
        sua_anh_nuoc = (ImageView) findViewById(R.id.sua_anh_nuoc);



        //lay thong tin sua
        final Intent intent = getIntent();
        nuocSua = (NuocUong) intent.getSerializableExtra("NuocUong");
        if(nuocSua != null)
        {
            sua_tennuoc.setText(nuocSua.getName());
            sua_discount.setText(Integer.toString(nuocSua.getDiscountMoney()));
            sua_gia_nuoc.setText(Integer.toString(nuocSua.getPrice()));
            sua_mota.setText(nuocSua.getDescription());
            if(nuocSua.getCategory().equals("CÀ PHÊ"))
            {
                r_sua_cafe.setChecked(true);
            }
            if (nuocSua.getCategory().equals("TRÁI CÂY"))
            {
                r_sua_traicay.setChecked(true);
            }
            if (nuocSua.getCategory().equals( "TRÀ TRÁI CÂY"))
            {
                r_sua_tratraicay.setChecked(true);
            }
            if (nuocSua.getCategory().equals("TRÀ SỮA"))
            {
                r_sua_trasua.setChecked(true);
            }
        }
        else{
            Toast.makeText(this,"Lỗi khi load dữ liệu nhân viên",Toast.LENGTH_LONG).show();
        }


        nutChonSuaAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Defining Implicit Intent to mobile gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."),
                        PICK_IMAGE_REQUEST);
            }
        });

        nutXacNhanSuaNuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NuocUong nuocMoi = new NuocUong();
                nuocMoi.setName(sua_tennuoc.getText().toString());
                nuocMoi.setDescription(sua_mota.getText().toString());
                if(!sua_discount.getText().toString().equals(""))
                {
                    nuocMoi.setDiscountMoney(Integer.parseInt(sua_discount.getText().toString()));
                }
                else{
                    nuocMoi.setDiscountMoney(0);
                }

                if(!sua_gia_nuoc.getText().toString().equals(""))
                {
                    nuocMoi.setPrice(Integer.parseInt(sua_gia_nuoc.getText().toString()));
                }
                else
                {
                    nuocMoi.setPrice(0);
                }
                if(r_sua_cafe.isChecked())
                {
                    nuocMoi.setCategory("CÀ PHÊ");
                }
                if (r_sua_traicay.isChecked())
                {
                    nuocMoi.setCategory("TRÁI CÂY");
                }
                if (r_sua_tratraicay.isChecked())
                {
                    nuocMoi.setCategory("TRÀ TRÁI CÂY");
                }
                if (r_sua_trasua.isChecked())
                {
                    nuocMoi.setCategory("TRÀ SỮA");
                }
                nuocMoi.setId(nuocSua.getId());
                if (filePath != null) {
                    // Defining the child of storageReference
                    StorageReference ref
                            = storageReference
                            .child(
                                    "images/"
                                            + UUID.randomUUID().toString());

                    // adding listeners on upload
                    // or failure of image

                    ref.putFile(filePath)
                            .addOnSuccessListener(
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                        @Override
                                        public void onSuccess(
                                                UploadTask.TaskSnapshot taskSnapshot)
                                        {
                                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                            while(!uri.isComplete());
                                            strImg_link = uri.getResult().toString();
                                            mDatabase.child("ITEM").child(nuocMoi.getId()).child("img_link").setValue(strImg_link);
                                        }
                                    })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {

                                }
                            })
                            .addOnProgressListener(
                                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                                        // Progress Listener for loading
                                        // percentage on the dialog box
                                        @Override
                                        public void onProgress(
                                                UploadTask.TaskSnapshot taskSnapshot)
                                        {

                                        }
                                    });

                    //nuocMoi.setImg_link(ref.getDownloadUrl().getResult().toString());
                }
                mDatabase.child("ITEM").child(nuocSua.getId()).setValue(nuocMoi);
                finish(); //đóng màn hình sửa
                Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                sua_anh_nuoc.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}