package com.partyhard.partyhard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.partyhard.partyhard.api.DjangoApi;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BackgroundTask().execute();
    }

    public void logout(View view) {
        SharedPreferences credentials = getSharedPreferences("user_credentials", MODE_PRIVATE);
        SharedPreferences.Editor credEditor = credentials.edit();
        credEditor.putString("token", "");
        credEditor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public class BackgroundTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {

            SharedPreferences credentials = getSharedPreferences("user_credentials", MODE_PRIVATE);
            String token = credentials.getString("token", "");
            String api_method = "parties/";
            return DjangoApi.getJSON(api_method, token);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.test_text);
            textView.setText(result);
        }

    }

}
