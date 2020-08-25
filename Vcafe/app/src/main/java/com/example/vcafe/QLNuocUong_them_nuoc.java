package com.example.vcafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class QLNuocUong_them_nuoc extends AppCompatActivity {

    private static int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private String strImg_link = new String();
    private EditText them_tennuoc,them_mota,them_gia_nuoc,them_discount;
    private Button nutXacNhanThemNuoc,nutChonAnh;
    private ImageView anh_nuoc;
    private RadioButton r_them_cafe, r_them_trasua,r_them_traicay,r_them_tratraicay;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_nuoc_uong_them_nuoc);

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
        them_tennuoc = (EditText) findViewById(R.id.them_ten_nuoc);
        them_mota = (EditText) findViewById(R.id.them_mota_nuoc);
        them_gia_nuoc = (EditText) findViewById(R.id.them_gia_nuoc);
        them_discount = (EditText) findViewById(R.id.them_gia_discount);
        nutXacNhanThemNuoc = (Button) findViewById(R.id.nut_xac_nhan_them_nuoc);
        nutChonAnh = (Button) findViewById(R.id.nut_chon_anh);
        anh_nuoc = (ImageView) findViewById(R.id.anh_nuoc);
        r_them_cafe = (RadioButton) findViewById(R.id.them_cafe);
        r_them_trasua = (RadioButton) findViewById(R.id.them_trsua);
        r_them_traicay = (RadioButton) findViewById(R.id.them_traicay);
        r_them_tratraicay = (RadioButton) findViewById(R.id.them_tratraicay);
        anh_nuoc = (ImageView) findViewById(R.id.anh_nuoc);

        nutChonAnh.setOnClickListener(new View.OnClickListener() {
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

        nutXacNhanThemNuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NuocUong nuocMoi = new NuocUong();
                nuocMoi.setName(them_tennuoc.getText().toString());
                nuocMoi.setDescription(them_mota.getText().toString());
                if(!them_discount.getText().toString().equals(""))
                {
                    nuocMoi.setDiscountMoney(Integer.parseInt(them_discount.getText().toString()));
                }
                else{
                    nuocMoi.setDiscountMoney(0);
                }

                if(!them_gia_nuoc.getText().toString().equals(""))
                {
                    nuocMoi.setPrice(Integer.parseInt(them_gia_nuoc.getText().toString()));
                }
                else
                {
                    nuocMoi.setPrice(0);
                }
                if(r_them_cafe.isChecked())
                {
                    nuocMoi.setCategory("CÀ PHÊ");
                }
                if (r_them_traicay.isChecked())
                {
                    nuocMoi.setCategory("TRÁI CÂY");
                }
                if (r_them_tratraicay.isChecked())
                {
                    nuocMoi.setCategory("TRÀ TRÁI CÂY");
                }
                if (r_them_trasua.isChecked())
                {
                    nuocMoi.setCategory("TRÀ SỮA");
                }
                nuocMoi.setId(mDatabase.push().getKey());
                if (filePath != null) {
                    // Defining the child of storageReference
                    StorageReference ref
                            = storageReference
                            .child(
                                    "images/" + UUID.randomUUID().toString());

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



                }
                mDatabase.child("ITEM").child(nuocMoi.getId()).setValue(nuocMoi);
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
                anh_nuoc.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
}}