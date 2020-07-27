package com.is.jishelpdesk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EmpProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    Bundle extra;
    TextView name, empId, address, number, panNumber, aadhaarNumber,countActive, countTotal;


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



        extra=getIntent().getExtras();




        if(extra != null){
            name.setText(extra.getString("name"));
            number.setText(extra.getString("number"));
            address.setText(extra.getString("address"));
            panNumber.setText(extra.getString("pan"));
            empId.setText("" +extra.getLong("empid"));
            aadhaarNumber.setText(extra.getString("aadhaar"));
            countActive.setText(""+extra.getLong("cActive"));
            countTotal.setText(""+extra.getLong("cTotal"));

        }
    }
}