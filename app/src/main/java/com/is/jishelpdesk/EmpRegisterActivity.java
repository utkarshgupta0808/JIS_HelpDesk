package com.is.jishelpdesk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class EmpRegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnReg, btnReset;
    TextView eName, eAddress, ePanNumber, eAadhaarNumber;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    long en;
    Bundle extra;
    ImageView btnBack, empPhoto;
    CardView photoCard;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Uri filePath;



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
        btnBack=findViewById(R.id.btn_back);
        photoCard=findViewById(R.id.emp_photo_card);
        empPhoto=findViewById(R.id.emp_photo);

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        photoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseItemImage();
            }
        });

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
                                final HashMap<String,Object>user=new HashMap<>();
                                user.put("name",name);
                                user.put("address", address);
                                user.put("pan",pan);
                                user.put("number", number);
                                user.put("empid",en);
                                user.put("aadhaar",aadhaar);
                                user.put("cActive",0);
                                user.put("ctotal",0);

//
                                String uiid = UUID.randomUUID().toString();
                                StorageReference reference = storageReference.child("Item images/" + uiid);
                                reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
//                                shop.put("itemsImage", uri.toString());



                                                user.put("photo",uri.toString());

                                                documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            startActivity(new Intent(getApplicationContext(),ComplaintEmpActivity.class));
                                                            Toast.makeText(EmpRegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
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

//                                                firebaseFirestore.collection("Employee").document(Objects.requireNonNull(firebaseAuth.getUid()))
//                                                        .update("photo",uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//                                                        Toast.makeText(EmpRegisterActivity.this,"Photo Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                                                        startActivity(new Intent(EmpRegisterActivity.this,EmpProfileActivity.class).putExtras(bundle));
//                                                        finish();
//                                                        progressDialog.dismiss();
//                                                    }
//                                                }).addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        Toast.makeText(EmpRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                        progressDialog.dismiss();
//                                                    }
//                                                });

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(EmpRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EmpRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
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
    private void chooseItemImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent,"Select item image"),1);
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(EmpRegisterActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                empPhoto.setImageBitmap(bitmap);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent=new Intent(EmpRegisterActivity.this,EmpLoginActivity.class);
        finish();
//        startActivity(intent);
    }

}