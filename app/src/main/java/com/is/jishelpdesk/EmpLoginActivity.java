package com.is.jishelpdesk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class EmpLoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnLogin;
    TextView resendOTP;
    EditText mobileNumber ,otp;
    FirebaseAuth firebaseAuth;
    CountryCodePicker countryCodePicker;
    String verficationId;
    PhoneAuthProvider.ForceResendingToken token;
    ImageView btnBack;
    Boolean verificationInProgress=false;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);

        firebaseAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);
        countryCodePicker=findViewById(R.id.ccp);
        firebaseFirestore=FirebaseFirestore.getInstance();

        btnLogin=findViewById(R.id.login_button);
        resendOTP=findViewById(R.id.resend);
        otp=findViewById(R.id.otp);
        btnBack=findViewById(R.id.btn_back);

        mobileNumber=findViewById(R.id.number);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(EmpLoginActivity.this,WelcomePageActivity.class);
                finish();
//                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verificationInProgress){
                    String numberMob=mobileNumber.getText().toString();
                    if (numberMob.length() == 10){
                        String number=countryCodePicker.getSelectedCountryCodeWithPlus()+numberMob;
//                        progressBar.setVisibility(View.VISIBLE);
                        showProgress();
                        otp.setVisibility(View.VISIBLE);
                        requestOTP(number);
                        }
                    else {
                        mobileNumber.setError("Number is not valid");
                    }
                }
                else {
                    String userOTP=otp.getText().toString();

                    if(userOTP.length() == 6){
                        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verficationId,userOTP);
//                        progressBar.setVisibility(View.VISIBLE);
                        showProgress();
                        verifyAuth(phoneAuthCredential);
                    }
                    else {
                        otp.setError("OTP must contain 6 digits");
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null){
//            progressBar.setVisibility(View.VISIBLE);
            showProgress();
            checkEmployeeProfile();
//            progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
        }
        else {

        }
    }





    private void requestOTP(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                verficationId=s;
                token=forceResendingToken;
                btnLogin.setText("Verify");
                verificationInProgress=true;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                String str=phoneAuthCredential.getSmsCode();
                assert str!=null;
                otp.setText(str);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(EmpLoginActivity.this, "Cannot create account "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyAuth(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    showProgress();
                    checkEmployeeProfile();
                }
                else {
                    Toast.makeText(EmpLoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
//                progressBar.setVisibility(View.GONE);.
                progressDialog.dismiss();
            }
        });
    }
    private void checkEmployeeProfile() {
        DocumentReference documentReference=firebaseFirestore.collection("Employee")
                .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    startActivity(new Intent(getApplicationContext(),ComplaintEmpActivity.class));
                }
                else {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    if (currentUser.getUid().equals("EzZHaJcOV3TeZ7V8C3Ow4NvZoJt2") || currentUser.getUid().equals("A8gsDmooTBOYsvLID14fT8MKYsN2")) {

                        Toast.makeText(EmpLoginActivity.this, "First Logout to Admin Account", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(), EmpRegisterActivity.class));
                    }

                }
                finish();
            }
        });
    }
    private void showProgress() {

        progressDialog = new ProgressDialog(EmpLoginActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent=new Intent(EmpLoginActivity.this,WelcomePageActivity.class);
        finish();
//        startActivity(intent);
    }
}