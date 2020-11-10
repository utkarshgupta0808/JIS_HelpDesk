package com.is.jishelpdesk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class CustomerApplicationActivity extends AppCompatActivity {

    EditText custName, custAddress, custDob, custMobile;
    String custGender="Male";
    RadioGroup radioGroupGender;
    int day, month, year;
    Calendar calendar;
    RadioButton radioButton;
    Button btnAddressProof, btnPhotoId, btnSubmit;
    Uri filePathAddressProof, filePathPhotoId;
    ImageView imgAddressProof, imgPhotoId, btnBack;
    int addressProofCount=0, photoIdCount=0;
    ProgressDialog progressDialog;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    Map customer=new HashMap<>();
    Bundle bundle=new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_application);

        radioGroupGender=findViewById(R.id.gender);
        custAddress=findViewById(R.id.cust_address);
        custDob=findViewById(R.id.cust_dob);
        custName=findViewById(R.id.cust_name);
        btnAddressProof=findViewById(R.id.choose_address_proof);
        btnPhotoId=findViewById(R.id.choose_photo_id);
        btnSubmit=findViewById(R.id.btn_submit);
        imgAddressProof=findViewById(R.id.img_address_proof);
        imgPhotoId=findViewById(R.id.img_photo_id);
        custMobile=findViewById(R.id.cust_mob);
        btnBack=findViewById(R.id.btn_back);


        calendar=Calendar.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 switch (checkedId){
                     case R.id.gender_male:
                         custGender="Male";


                     case R.id.gender_female:
                         custGender="Female";
                 }
            }
        });

        custDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=calendar.get(Calendar.DAY_OF_MONTH);
                month=calendar.get(Calendar.MONTH);
                year=calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog=new DatePickerDialog(CustomerApplicationActivity.this, R.style.Theme_AppCompat_DayNight,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        custDob.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                },year, month,day);

                datePickerDialog.show();
            }
        });

        custMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (!custName.getText().toString().isEmpty() && !custAddress.getText().toString().isEmpty()
                        && !custDob.getText().toString().isEmpty() && !custMobile.getText().toString().isEmpty() &&photoIdCount>0 && addressProofCount>0) {

                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
            }
        });

        custDob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (!custName.getText().toString().isEmpty() && !custAddress.getText().toString().isEmpty()
                        && !custDob.getText().toString().isEmpty() && !custMobile.getText().toString().isEmpty()&&photoIdCount>0 && addressProofCount>0) {

                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
            }
        });

        custName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (!custName.getText().toString().isEmpty() && !custAddress.getText().toString().isEmpty()
                        && !custDob.getText().toString().isEmpty() && !custMobile.getText().toString().isEmpty() &&photoIdCount>0 && addressProofCount>0) {

                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
            }
        });

        custDob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (!custName.getText().toString().isEmpty() && !custAddress.getText().toString().isEmpty()
                        && !custDob.getText().toString().isEmpty() && !custMobile.getText().toString().isEmpty() &&photoIdCount>0 && addressProofCount>0) {

                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
            }
        });




        btnAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseAddressProof();
            }
        });

        btnPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosePhotoId();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(v.getContext());
                progressDialog.show();
                progressDialog.setContentView(R.layout.process_dialog);
                progressDialog.setCanceledOnTouchOutside(false);

                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                        android.R.color.transparent);

                final String uiid = UUID.randomUUID().toString();
                StorageReference reference = storageReference.child("Address Proof/" + uiid);
                reference.putFile(filePathAddressProof).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
                                customer.put("addressProof", uri.toString());

                                final String uiid1 = UUID.randomUUID().toString();

                                StorageReference reference = storageReference.child("Photo Id/" + uiid);
                                reference.putFile(filePathPhotoId).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                customer.put("photoId", uri.toString());
                                                customer.put("customerName", custName.getText().toString().trim());
                                                customer.put("customerAddress", custAddress.getText().toString().trim());
                                                customer.put("customerMobile", custMobile.getText().toString().trim());
                                                customer.put("customerDob", custDob.getText().toString().trim());
                                                customer.put("customerGender", custGender);
                                                firebaseFirestore.collection("Customers")
                                                        .add(customer).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {

                                                        Toast.makeText(CustomerApplicationActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                        progressDialog.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Toast.makeText(CustomerApplicationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
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
                                        Toast.makeText(CustomerApplicationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        });

                    }
                });

            }
        });
    }

    private void chooseAddressProof() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent,"Select item image"),1);
    }

    private void choosePhotoId() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent,"Select item image"),2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePathAddressProof = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathAddressProof);
                imgAddressProof.setVisibility(View.VISIBLE);
                imgAddressProof.setImageBitmap(bitmap);
                addressProofCount++;
                if (!custName.getText().toString().isEmpty() && !custAddress.getText().toString().isEmpty()
                        && !custDob.getText().toString().isEmpty() && !custMobile.getText().toString().isEmpty() && photoIdCount>0){
                    btnSubmit.setEnabled(true);
                }
                else{
                    btnSubmit.setEnabled(false);
                }
            }

            if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePathPhotoId = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathPhotoId);
                imgPhotoId.setVisibility(View.VISIBLE);
                imgPhotoId.setImageBitmap(bitmap);
                photoIdCount++;
                if (!custName.getText().toString().isEmpty() && !custAddress.getText().toString().isEmpty()
                        && !custDob.getText().toString().isEmpty() && !custMobile.getText().toString().isEmpty() && addressProofCount>0){
                    btnSubmit.setEnabled(true);
                }
                else{
                    btnSubmit.setEnabled(false);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}