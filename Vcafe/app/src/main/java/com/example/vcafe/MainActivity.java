package com.example.vcafe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button nut_dang_nhap;
    private EditText email_dang_nhap, mat_khau_dang_nhap;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, man_hinh_chinh.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email_dang_nhap = (EditText) findViewById(R.id.email_dang_nhap);
        mat_khau_dang_nhap = (EditText) findViewById(R.id.mat_khau_dang_nhap);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        nut_dang_nhap = (Button) findViewById(R.id.nut_dang_nhap);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        nut_dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_dang_nhap.getText().toString();
                final String password = mat_khau_dang_nhap.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        mat_khau_dang_nhap.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(MainActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    openManhinhchinhActivity();
                                }
                            }
                        });
            }
        });
    }

    private void openManhinhchinhActivity() {
        Intent intent = new Intent(this,man_hinh_chinh.class);
        startActivity(intent);
        finish();
    }


}