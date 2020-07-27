package com.is.jishelpdesk;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ComplaintInfoEmpActivity extends AppCompatActivity {


    Toolbar toolbar;
    TextView tokenText, nameText, addressText, numberText, dateText, complaintText;
    Bundle extra;
    Button complaintSolved;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    long countActive;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_info_emp);

        toolbar = findViewById(R.id.toolbar);
        tokenText = findViewById(R.id.tokenid);
        nameText = findViewById(R.id.name);
        dateText = findViewById(R.id.date);
        complaintText = findViewById(R.id.complaint);
        numberText = findViewById(R.id.number);
        addressText = findViewById(R.id.address);
        complaintSolved=findViewById(R.id.btn_solved);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


         extra = getIntent().getExtras();

        if (extra != null) {
            tokenText.setText(""+extra.getLong("tokenId"));
            nameText.setText(extra.getString("name"));
            dateText.setText(extra.getString("date"));
            complaintText.setText(extra.getString("complaint"));
            addressText.setText(extra.getString("address"));
            numberText.setText(extra.getString("number1"));
        }
        
        complaintSolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                final DocumentReference documentReference;

                firebaseFirestore.collection("Complaint")
                        .whereEqualTo("tokenId",extra.getLong("tokenId"))
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        firebaseFirestore.collection("Complaint").document(queryDocumentSnapshots.getDocuments().get(0).getId())
                                .update("status","Closed").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(ComplaintInfoEmpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                
                documentReference=firebaseFirestore.collection("Employee").document(Objects.requireNonNull(firebaseAuth.getUid()));
                documentReference.collection("Complaint")
                        .whereEqualTo("tokenId",extra.getLong("tokenId"))
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        DocumentReference documentReference1=firebaseFirestore.collection("Employee").document(firebaseAuth.getUid());
                        documentReference1.collection("Complaint").document(queryDocumentSnapshots.getDocuments().get(0).getId())
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {



                                firebaseFirestore.collection("Employee").document(firebaseAuth.getUid())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
//                                            Intent intent=new Intent(ComplaintInfoEmpActivity.this,ComplaintEmpActivity.class);
                                            countActive = Objects.requireNonNull(documentSnapshot).getLong("cActive");
                                            countActive--;
                                            updateActiveCount(countActive);
                                            progressDialog.dismiss();
//                                            startActivity(intent);
                                            finish();
                                        } else {

                                        }
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                        
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });


    }


    void updateActiveCount(long c){
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Employee").document(Objects.requireNonNull(firebaseAuth.getUid()))
                .update("cActive", c).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ComplaintInfoEmpActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ComplaintInfoEmpActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                }
        });
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(ComplaintInfoEmpActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

}