package com.example.vcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class dang_ky extends AppCompatActivity {
    private EditText email_dang_ky, mat_khau_dang_ky,xac_nhan_mat_khau_dang_ki,dang_ki_ten_nhan_vien,dang_ki_luong_nhan_vien;
    private Button nutXacNhanDangKy, nupHuyDangKy;
    RadioButton r_tkAdmin, r_tkNV;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        //Get Firebase auth instance AND database
        auth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //input zone
        nutXacNhanDangKy = (Button) findViewById(R.id.nut_xac_nhan_dang_ky);
        email_dang_ky = (EditText) findViewById(R.id.email_dang_ki);
        mat_khau_dang_ky = (EditText) findViewById(R.id.mat_khau_dang_ki);
        xac_nhan_mat_khau_dang_ki = (EditText) findViewById(R.id.xac_nhan_mat_khau_dang_ki);
        dang_ki_ten_nhan_vien = (EditText) findViewById(R.id.dang_ki_ten_nhan_vien);
        dang_ki_luong_nhan_vien = (EditText) findViewById(R.id.dang_ki_luong_nhan_vien);

        r_tkAdmin = findViewById(R.id.taikhoanAdmin);
        r_tkNV = findViewById(R.id.taikhoanNhanVien);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //datepicker

        //Xac nhan dang ky + push database
        nutXacNhanDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = email_dang_ky.getText().toString().trim();
                final String password = mat_khau_dang_ky.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(dang_ky.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(dang_ky.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(dang_ky.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    TaiKhoan newTK = new TaiKhoan();
                                    newTK.setMaNV(auth.getUid());
                                    newTK.setTaiKhoan(email);
                                    newTK.setMaTK(auth.getUid());
                                    newTK.setMatKhau(password);

                                    NhanVien newNV = new NhanVien();
                                    newNV.setMaNV(auth.getUid());
                                    newNV.setTenNV(dang_ki_ten_nhan_vien.getText().toString().trim());
                                    newNV.setLuong(Integer.parseInt(dang_ki_luong_nhan_vien.getText().toString()));

                                    if(r_tkAdmin.isChecked())
                                    {
                                        newTK.setLoaiTK("01");
                                        newNV.setLoaiNV("01");
                                    }
                                    else
                                    {
                                        newTK.setLoaiTK("02");
                                        newNV.setLoaiNV("02");
                                    }

                                    mDatabase.child("TaiKhoan").child(auth.getUid()).setValue(newTK);
                                    mDatabase.child("NhanVien").child(auth.getUid()).setValue(newNV);

                                    finish();
                                }
                            }
                        });

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}