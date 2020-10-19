package com.is.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AdminEmpProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    Bundle extra;
    TextView name, empId, address, number, panNumber, aadhaarNumber,countActive, countTotal, empNameToolbar;
    ImageView btnBack, empPhoto;
    CardView photoCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_emp_profile);

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
        empNameToolbar=findViewById(R.id.emp_name_toolbar);




        extra=getIntent().getExtras();

        photoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();
                bundle.putString("photoPreview",extra.getString("photo"));
                bundle.putString("photoDes",extra.getString("name"));
                startActivity(new Intent(AdminEmpProfileActivity.this,PhotoPreviewActivity.class).putExtras(bundle));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        if(extra != null){
            empNameToolbar.setText(extra.getString("name"));
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
        finish();

    }
}