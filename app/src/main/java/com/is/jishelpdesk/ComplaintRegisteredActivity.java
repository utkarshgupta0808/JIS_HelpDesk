package com.is.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ComplaintRegisteredActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registered);

        textView=findViewById(R.id.tokenid);

        Bundle extra=getIntent().getExtras();

        if (extra!=null){

            textView.setText(""+extra.getLong("tokenID"));
        }
    }
}