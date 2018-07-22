package com.partyhard.partyhard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.partyhard.partyhard.api.DjangoApi;
import com.partyhard.partyhard.crypt.Aes;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


public class LoginActivity extends AppCompatActivity  {
    DjangoApi djangoApi = new DjangoApi();
    Aes aes = new Aes();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginVK(View view) {
        Intent intent = new Intent(this, LoginVKActivity.class);
        startActivity(intent);
    }

    public void loginPartyhard(View view) {
        new BackgroundTask().execute();
    }


    public class BackgroundTask extends AsyncTask<Void, Void, String> {

        TextView textViewUser = (TextView) findViewById(R.id.userNameInput);
        String username = textViewUser.getText().toString();
        TextView textViewPassword = (TextView) findViewById(R.id.passwordInput);
        String password = textViewPassword.getText().toString();

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {

            String enc_password = aes.encryptPassword(password);
            String api_method = "login/?username=" + username + "&password=" + enc_password;
            return djangoApi.getJSON(api_method, null);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            SharedPreferences credentials =getSharedPreferences("user_credentials",MODE_PRIVATE);
            SharedPreferences.Editor credEditor = credentials.edit();
            String dec_token = aes.decryptKey(djangoApi.parseJSON(result, "sessionToken"));
            credEditor.putString("token", dec_token);
            credEditor.commit();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
