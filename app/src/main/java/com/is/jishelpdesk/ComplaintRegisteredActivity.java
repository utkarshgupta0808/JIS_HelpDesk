package com.is.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ComplaintRegisteredActivity extends AppCompatActivity {

    TextView textView;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registered);

        textView=findViewById(R.id.tokenid);
        btnBack=findViewById(R.id.btn_back);

        Bundle extra=getIntent().getExtras();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ComplaintRegisteredActivity.this,WelcomePageActivity.class);
                finish();
                startActivity(intent);
            }
        });

        if (extra!=null){

            textView.setText(""+extra.getLong("tokenID"));
        }
    }
}