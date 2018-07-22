package com.partyhard.partyhard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class LauncherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences credentials = getSharedPreferences("user_credentials", MODE_PRIVATE);
        String key = credentials.getString("token", "");
        Intent intent;
        if (key == "") {
            intent = new Intent(this, LoginActivity.class);

        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
