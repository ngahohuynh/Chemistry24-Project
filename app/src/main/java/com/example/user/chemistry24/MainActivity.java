package com.example.user.chemistry24;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final int WAIT_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);

                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        },WAIT_TIME);
    }
}
