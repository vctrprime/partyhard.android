package com.partyhard.partyhard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.partyhard.partyhard.api.DjangoApi;
import com.partyhard.partyhard.crypt.Aes;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


public class LoginVKActivity extends AppCompatActivity {

    private String[] scope = new String[]{};
    private String accessToken;
    DjangoApi djangoApi = new DjangoApi();
    Aes aes = new Aes();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        VKSdk.login(this, scope);
    }



    public class BackgroundTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {


            String enc_token = aes.encryptPassword(accessToken);
            String api_method = "vk-login/?auth_token=" + enc_token;
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
            Intent intent = new Intent(LoginVKActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                accessToken = res.accessToken;
                new BackgroundTask().execute();

                // Пользователь успешно авторизовался
            }
            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}