
package com.is.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class WelcomePageActivity extends AppCompatActivity {

    Button btnComplaint, btnAdminLogin, btnEmpLogin;
    private long backPressedTime;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);


        btnComplaint=findViewById(R.id.complaintButton);
        btnComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                Intent intent=new Intent(WelcomePageActivity.this,ComplaintActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
            }
        });

        btnAdminLogin=findViewById(R.id.adminButton);
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                Intent intent=new Intent(WelcomePageActivity.this,AdminLoginActivity.class);

                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        });

        btnEmpLogin=findViewById(R.id.empButton);
        btnEmpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                Intent intent=new Intent(WelcomePageActivity.this, EmpLoginActivity.class);
                startActivity(intent);

                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {

        if(backPressedTime+ 2000> System.currentTimeMillis()){
            super.onBackPressed();
            Intent intent=new Intent(WelcomePageActivity.this ,SplashActivity.class);

            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();




        }else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime=System.currentTimeMillis();
    }
    private void showProgress() {
        progressDialog = new ProgressDialog(WelcomePageActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}