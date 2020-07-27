package com.is.jishelpdesk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class EmpRegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnReg, btnReset;
    TextView eName, eAddress, ePanNumber, eAadhaarNumber;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    long en;
    Bundle extra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_register);

        toolbar=findViewById(R.id.toolbar);
        btnReg=findViewById(R.id.btn_reg);
        btnReset=findViewById(R.id.btn_reset);
        eName=findViewById(R.id.emp_name);
        eAddress=findViewById(R.id.emp_address);
        ePanNumber=findViewById(R.id.emp_pan);
        eAadhaarNumber=findViewById(R.id.emp_aadhaar);

        extra=getIntent().getExtras();


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        final DocumentReference documentReference=firebaseFirestore.collection("Employee").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eName.getText().toString().isEmpty()){
                    eName.setError("Name is Mandatory");
                }
                else if(eAddress.getText().toString().isEmpty()){
                    eAddress.setError("Address is Mandatory");
                }
                else if(ePanNumber.getText().toString().isEmpty()){
                    ePanNumber.setError("Pan Number is Mandatory");
                }
                else if (eAadhaarNumber.getText().toString().isEmpty()){

                    eAadhaarNumber.setError("Pan Number is Mandatory");
                }
                else {
                    showProgress();
                    firebaseFirestore =FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("Counter").document("123456789")
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot documentSnapshot=task.getResult();

                                en= Objects.requireNonNull(documentSnapshot).getLong("countEmp");


                                en=en+1;

                                String name=eName.getText().toString();
                                String address=eAddress.getText().toString();
                                String pan=ePanNumber.getText().toString();
                                String aadhaar=eAadhaarNumber.getText().toString();
                                String number=firebaseAuth.getCurrentUser().getPhoneNumber();
                                HashMap<String,Object>user=new HashMap<>();
                                user.put("name",name);
                                user.put("address", address);
                                user.put("pan",pan);
                                user.put("number", number);
                                user.put("empid",en);
                                user.put("aadhaar",aadhaar);
                                user.put("cActive",0);
                                user.put("ctotal",0);

                                documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            startActivity(new Intent(getApplicationContext(),ComplaintEmpActivity.class));
                                            finish();
                                            resetFields();
                                            progressDialog.dismiss();
                                        }
                                        else {
                                            Toast.makeText(EmpRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                        firebaseFirestore.collection("Counter").document("123456789")
                                                .update("countEmp",en).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){

                                                }else {

                                                }
                                            }
                                        });
                                    }
                                });

                            }
                            else {
                            }
                        }
                    });
                    
                    
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();
            }
        });


    }


    public void resetFields(){

        ePanNumber.setText("");
        eAddress.setText("");
        eName.setText("");
        eAadhaarNumber.setText("");

    }
    private void showProgress() {
        progressDialog = new ProgressDialog(EmpRegisterActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

}