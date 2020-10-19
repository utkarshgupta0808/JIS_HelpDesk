package com.is.jishelpdesk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class ComplaintInfoAdminActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tokenText, nameText, addressText, numberText, dateText, complaintText, statusText, compNameToolbar;
    Bundle extra;
    Button btnAssign;
    ImageView btnBack;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_info_admin);

        toolbar=findViewById(R.id.toolbar);
        tokenText=findViewById(R.id.tokenid);
        nameText=findViewById(R.id.name);
        statusText=findViewById(R.id.status);
        dateText=findViewById(R.id.date);
        complaintText=findViewById(R.id.complaint);
        numberText=findViewById(R.id.number);
        addressText=findViewById(R.id.address);
        btnAssign=findViewById(R.id.btn_assign);
        linearLayout=findViewById(R.id.complaint_info);
        btnBack=findViewById(R.id.btn_back);
        compNameToolbar=findViewById(R.id.comp_name_toolbar);
//        changeEmployee=findViewById(R.id.change_employee);

        linearLayout.setVerticalScrollBarEnabled(true);




        extra=getIntent().getExtras();

        if(extra != null){
            compNameToolbar.setText(extra.getString("name"));
            tokenText.setText(""+extra.getLong("tokenId"));
            nameText.setText(extra.getString("name"));
            dateText.setText(extra.getString("date"));
            statusText.setText(extra.getString("status"));
            complaintText.setText(extra.getString("complaint"));
            addressText.setText(extra.getString("address"));
            numberText.setText(extra.getString("number1"));
        }

        if (statusText.getText().toString().equals("Closed")){
            btnAssign.setText("Complaint is Closed");
            btnAssign.setEnabled(false);
        }
        else if (statusText.getText().toString().substring(0,8).equals("Assigned")){
            btnAssign.setText("Complaint is Already Assigned");
            btnAssign.setEnabled(false);
//            changeEmployee.setVisibility(View.VISIBLE);
        }
        else {
            btnAssign.setEnabled(true);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(ComplaintInfoAdminActivity.this,AdminMainActivity.class);
                finish();
//                startActivity(intent);
            }
        });

        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                Intent intent=new Intent(ComplaintInfoAdminActivity.this, AssignEmpActivity.class);
//                extra.putBoolean("newAssignment",false);
                intent.putExtras(extra);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        });

//        changeEmployee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showProgress();
//                Intent intentChangeEmp=new Intent(ComplaintInfoAdminActivity.this, AssignEmpActivity.class);
//                extra.putBoolean("newAssignment",true);
//                intentChangeEmp.putExtras(extra);
//                startActivity(intentChangeEmp);
//                finish();
//                progressDialog.dismiss();
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent=new Intent(ComplaintInfoAdminActivity.this,AdminMainActivity.class);
        finish();
//        startActivity(intent);
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(ComplaintInfoAdminActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}