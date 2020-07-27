package com.is.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getIntent().getBooleanExtra("EXIT", false)){

            finish();
            System.exit(1);

        }
        else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                        Intent intent = new Intent(SplashActivity.this, WelcomePageActivity.class);
                        startActivity(intent);
                        finish();


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

    }
}