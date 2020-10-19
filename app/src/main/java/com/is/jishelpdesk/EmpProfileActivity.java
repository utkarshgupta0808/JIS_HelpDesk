package com.is.jishelpdesk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

public class EmpProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    Bundle extra;
    TextView name, empId, address, number, panNumber, aadhaarNumber,countActive, countTotal;
    ImageView btnBack, empPhoto;
    CardView photoCard, editPhoto;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_profile);

        toolbar=findViewById(R.id.toolbar);
        name=findViewById(R.id.name);
        empId=findViewById(R.id.empid);
        address=findViewById(R.id.address);
        number=findViewById(R.id.number);
        panNumber=findViewById(R.id.pan);
        aadhaarNumber=findViewById(R.id.aadhaar);
        countActive=findViewById(R.id.count_active);
        countTotal=findViewById(R.id.count_total);
        btnBack=findViewById(R.id.btn_back);
        empPhoto=findViewById(R.id.emp_photo);
        photoCard=findViewById(R.id.emp_photo_card);
        editPhoto=findViewById(R.id.edit_photo);


        extra=getIntent().getExtras();

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmpProfileActivity.this,AddPhotoActivity.class).putExtras(extra));
                finish();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmpProfileActivity.this,ComplaintEmpActivity.class);
                finish();
                startActivity(intent);
            }
        });

        photoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();
                bundle.putString("photoPreview",extra.getString("photo"));
                bundle.putString("photoDes",extra.getString("name"));
                startActivity(new Intent(EmpProfileActivity.this,PhotoPreviewActivity.class).putExtras(bundle));
            }
        });


        if(extra != null){
            name.setText(extra.getString("name"));
            number.setText(extra.getString("number"));
            address.setText(extra.getString("address"));
            panNumber.setText(extra.getString("pan"));
            empId.setText("" +extra.getLong("empid"));
            aadhaarNumber.setText(extra.getString("aadhaar"));
            countActive.setText(""+extra.getLong("cActive"));
            countTotal.setText(""+extra.getLong("cTotal"));
            Glide.with(this).load(extra.getString("photo")).into(empPhoto);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(EmpProfileActivity.this,ComplaintEmpActivity.class);
        finish();
        startActivity(intent);
    }
}