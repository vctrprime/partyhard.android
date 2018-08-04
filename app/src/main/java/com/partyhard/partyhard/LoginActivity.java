package com.partyhard.partyhard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.partyhard.partyhard.api.DjangoApi;
import com.partyhard.partyhard.crypt.Aes;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


public class LoginActivity extends AppCompatActivity  {

    String username;
    String password;
    TextView infoError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        infoError = (TextView) findViewById(R.id.notifError);

    }

    public void loginVK(View view) {
        Intent intent = new Intent(this, LoginVKActivity.class);
        startActivity(intent);
    }

    public void loginPartyhard(View view) {

        TextView textViewUser = (TextView) findViewById(R.id.userNameInput);
        username = textViewUser.getText().toString();
        TextView textViewPassword = (TextView) findViewById(R.id.passwordInput);
        password = textViewPassword.getText().toString();
        if (username.length() == 0 || password.length() == 0) {

            infoError.setVisibility(View.VISIBLE);
            infoError.setText(R.string.no_fill);
        }
        else {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            new BackgroundTask().execute();
        }

    }


    public class BackgroundTask extends AsyncTask<Void, Void, String> {



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {

            String enc_password = Aes.encryptPassword(password);
            String api_method = "login/?username=" + username + "&password=" + enc_password;
            return DjangoApi.getJSON(api_method, null);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
            if (result == "Connection refused"){
                infoError.setVisibility(View.VISIBLE);
                infoError.setText(R.string.no_connection);
            }
            if (result == "Incorrect") {
                infoError.setVisibility(View.VISIBLE);
                infoError.setText(R.string.inv_up);
            }
            if (result != "Connection refused" && result != "Incorrect") {
                SharedPreferences credentials =getSharedPreferences("user_credentials",MODE_PRIVATE);
                SharedPreferences.Editor credEditor = credentials.edit();
                String dec_token = Aes.decryptKey(DjangoApi.parseJSON(result, "sessionToken"));
                credEditor.putString("token", dec_token);
                String avatar = DjangoApi.parseJSON(result, "avatar");
                credEditor.putString("avatar", avatar);
                String username = DjangoApi.parseJSON(result, "username");
                credEditor.putString("username", username);
                credEditor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }


}
