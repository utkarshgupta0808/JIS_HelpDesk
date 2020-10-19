package com.is.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReceiveNotificationActivity extends AppCompatActivity {

    TextView recNotiTitle, recNotiBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_notification);

        recNotiTitle=findViewById(R.id.rec_noti_title);
        recNotiBody=findViewById(R.id.rec_noti_body);

        recNotiBody.setText(getIntent().getStringExtra("bodyNoti"));
        recNotiTitle.setText(getIntent().getStringExtra("titleNoti"));
    }
}