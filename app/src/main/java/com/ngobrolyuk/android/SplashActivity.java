package com.ngobrolyuk.android;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ngobrolyuk.android.utils.Session;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Session.getInstance().isUserLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
