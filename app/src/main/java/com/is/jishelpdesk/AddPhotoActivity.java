package com.is.jishelpdesk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class AddPhotoActivity extends AppCompatActivity {

    ImageView itemImageView, imgItem, btnBack;
    Uri filePath;
    Button btnAddPhoto;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        imgItem=findViewById(R.id.img_item);
        itemImageView=findViewById(R.id.img_item_preview);
        btnAddPhoto=findViewById(R.id.btn_add_image);
        btnBack=findViewById(R.id.btn_back);

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        bundle=getIntent().getExtras();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPhotoActivity.this,EmpProfileActivity.class).putExtras(bundle));
                finish();
            }
        });

        imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseItemImage();
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(view.getContext());
                progressDialog.show();
                progressDialog.setContentView(R.layout.process_dialog);
                progressDialog.setCanceledOnTouchOutside(false);

                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                        android.R.color.transparent);


                String uiid = UUID.randomUUID().toString();
                StorageReference reference = storageReference.child("Item images/" + uiid);
                reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
//                                shop.put("itemsImage", uri.toString());

                                firebaseFirestore.collection("Employee").document(Objects.requireNonNull(firebaseAuth.getUid()))
                                        .update("photo",uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddPhotoActivity.this,"Photo Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                        bundle.putString("photo",uri.toString());
                                        startActivity(new Intent(AddPhotoActivity.this,EmpProfileActivity.class).putExtras(bundle));
                                        finish();
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddPhotoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddPhotoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
            }
        });



    }
    private void chooseItemImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent,"Select item image"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                itemImageView.setImageBitmap(bitmap);
                btnAddPhoto.setEnabled(true);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddPhotoActivity.this,EmpProfileActivity.class).putExtras(bundle));
        finish();
    }
}