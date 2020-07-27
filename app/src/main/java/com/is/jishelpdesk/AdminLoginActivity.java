package com.is.jishelpdesk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText userName, password;
    Button btn_login;
    Toolbar toolbar;
    TextView textView;
    private ProgressDialog progressDialog;
//    AtomicReference<String> userId = new AtomicReference<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        userName=findViewById(R.id.username);
        password=findViewById(R.id.password);
        toolbar=findViewById(R.id.toolbar);
        btn_login=findViewById(R.id.login_button);
        textView=findViewById(R.id.forgot_pass);
        mAuth = FirebaseAuth.getInstance();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminLoginActivity.this, ForgotPassAdminActivity.class);
                startActivity(intent);

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= userName.getText().toString().trim();
                String pass= password.getText().toString().trim();


                if (TextUtils.isEmpty(email)){
                    userName.setError("Email is Required");
                }

               else if (TextUtils.isEmpty(pass)){
                    password.setError("Password is Required");
                }
               else {
                   showProgress();
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(AdminLoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminLoginActivity.this, ComplaintAdminActivity.class);
//                            intent.putExtra("user_id", "" + userId);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(AdminLoginActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                            }
                            progressDialog.dismiss();
                        }


                    });

                }



            }

        });

    }
    private void showProgress() {
        progressDialog = new ProgressDialog(AdminLoginActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            if (currentUser.getUid().equals("A8gsDmooTBOYsvLID14fT8MKYsN2" )) {

                sendToMain();

            }
            if (currentUser.getUid().equals("EzZHaJcOV3TeZ7V8C3Ow4NvZoJt2" )) {

                sendToMain();

            }

        }



    }

    private void sendToMain() {

        Intent mainIntent = new Intent(AdminLoginActivity.this, ComplaintAdminActivity.class);
        startActivity(mainIntent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AdminLoginActivity.this, WelcomePageActivity.class);
        startActivity(intent);
        finish();
    }
}