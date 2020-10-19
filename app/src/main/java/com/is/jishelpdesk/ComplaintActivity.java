package com.is.jishelpdesk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ComplaintActivity extends AppCompatActivity {

    EditText complaintText, numberText, nameText, addressText;
    Button btn_submit, btn_reset;

    ProgressDialog progressDialog;
    Toolbar toolbar;
    Map<String, Object> user = new HashMap<>();
    FirebaseFirestore db;
    String cDate;
    Calendar calendar;
    String currentDate;
    ImageView btnBack;
    long counterComplaint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        toolbar=findViewById(R.id.toolbar);
        db = FirebaseFirestore.getInstance();




        complaintText=findViewById(R.id.complaint);

        nameText=findViewById(R.id.name);
        numberText=findViewById(R.id.mobile);
        addressText=findViewById(R.id.address);
        btn_submit=findViewById(R.id.buttonSubmit);
        btn_reset=findViewById(R.id.buttonReset);
        btnBack=findViewById(R.id.btn_back);



        complaintText.setMovementMethod(new ScrollingMovementMethod());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ComplaintActivity.this,WelcomePageActivity.class);
                finish();
                startActivity(intent);
            }
        });




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String cName= nameText.getText().toString().trim();
                final String cMobile= numberText.getText().toString().trim();
                final String cAddress= addressText.getText().toString().trim();
                final String cComplaint= complaintText.getText().toString().trim();

                calendar= Calendar.getInstance();
                currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


                cDate=""+ currentDate;




                if (TextUtils.isEmpty(cName)){
                    nameText.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(cMobile)){
                    numberText.setError("Mobile Number is Required");
                    return;
                }
                if (TextUtils.isEmpty(cAddress)){
                    addressText.setError("Address is Required");
                    return;
                }
                if (TextUtils.isEmpty(cComplaint)){
                    complaintText.setError("Please provide us your complaint");
                    return;
                }
                showProgress();
                db=FirebaseFirestore.getInstance();
                db.collection("Counter").document("123456789")
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();

                            counterComplaint= Objects.requireNonNull(documentSnapshot).getLong("countComplaint");

                            counterComplaint++;




                            DocumentReference docRef =FirebaseFirestore.getInstance()
                                    .collection("Counter").document("123456789");

                            Map<String, Object>map1=new HashMap<>();
                            map1.put("countComplaint", counterComplaint);

                            docRef.update(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                            user.put("tokenId", counterComplaint);
                            user.put("name", cName);
                            user.put("mobile", cMobile);
                            user.put("status", "Unassigned");
                            user.put("address", cAddress);
                            user.put("complaint", cComplaint);
                            user.put("date", cDate);

                            db.collection("Complaint")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(ComplaintActivity.this, "Complaint Registered with token id "+ counterComplaint, Toast.LENGTH_SHORT).show();
                                            resetFields();

                                        }
                                    })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ComplaintActivity.this,
                                                    "Error occurred in registering your complaint! Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                            progressDialog.dismiss();
                            Intent intent=new Intent(ComplaintActivity.this, ComplaintRegisteredActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putLong("tokenID",counterComplaint);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();







                        }
                        else {
                            Toast.makeText(ComplaintActivity.this, "Error : " + Objects.requireNonNull(task.getException())
                                    .getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ComplaintActivity.this,WelcomePageActivity.class);
        finish();
        startActivity(intent);
    }

    public void resetFields(){
        nameText.setText("");
        numberText.setText("");
        addressText.setText("");
        complaintText.setText("");
    }
    private void showProgress() {

        progressDialog = new ProgressDialog(ComplaintActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}