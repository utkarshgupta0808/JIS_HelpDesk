package com.is.jishelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPassAdminActivity extends AppCompatActivity {

    Button btnForgotPass;
    EditText editTextEmailInput;
    TextView textViewForgotSignIn;
    private FirebaseAuth mAuth;
    Toolbar toolBar;
    private ProgressDialog progressDialog;
    ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_admin);

        btnForgotPass = findViewById(R.id.forgotSend);
        editTextEmailInput = findViewById(R.id.forgot_emailText);
        textViewForgotSignIn = findViewById(R.id.textViewSignForgot);
        mAuth = FirebaseAuth.getInstance();
        toolBar=findViewById(R.id.toolbar);
        btnBack=findViewById(R.id.btn_back);


        textViewForgotSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ForgotPassAdminActivity.this,AdminLoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgotPassAdminActivity.this,AdminLoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();

                String userEmail = editTextEmailInput.getText().toString();
                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(ForgotPassAdminActivity.this, "Please enter valid email address!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
                else {
                    mAuth.sendPasswordResetEmail(userEmail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(ForgotPassAdminActivity.this, "Reset Link Sent", Toast.LENGTH_SHORT).show();
                                    editTextEmailInput.setText("");
                                    Intent intent = new Intent(ForgotPassAdminActivity.this, AdminLoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    progressDialog.dismiss();



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ForgotPassAdminActivity.this, "Error! Reset Link is not Sent "+ e.getMessage(), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        }
                    });


                }

            }
        });


    }
    private void showProgress() {
        progressDialog = new ProgressDialog(ForgotPassAdminActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ForgotPassAdminActivity.this,AdminLoginActivity.class);
        finish();
        startActivity(intent);
    }
}
