package com.partyhard.partyhard;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.partyhard.partyhard.api.DjangoApi;
import com.partyhard.partyhard.crypt.Aes;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


public class LoginVKActivity extends AppCompatActivity {

    private String[] scope = new String[]{};
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        VKSdk.login(this, scope);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                accessToken = res.accessToken;
                new LoginVkTask().execute();

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

    public class LoginVkTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {


            String enc_token = Aes.encryptPassword(accessToken);
            String api_method = "vk-login/?auth_token=" + enc_token;
            return DjangoApi.getJSON(api_method, null);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == "Connection refused"){
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginVKActivity.this, R.style.MyDialogTheme);
                builder/*.setTitle("Важное сообщение!")*/
                        .setMessage(R.string.no_connection)
                        .setCancelable(false)
                        .setNeutralButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Intent intent = new Intent(LoginVKActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else {
                SharedPreferences credentials =getSharedPreferences("user_credentials",MODE_PRIVATE);
                SharedPreferences.Editor credEditor = credentials.edit();
                String dec_token = Aes.decryptKey(DjangoApi.parseJSON(result, "sessionToken"));
                credEditor.putString("token", dec_token);
                String avatar = DjangoApi.parseJSON(result, "avatar");
                credEditor.putString("avatar", avatar);
                String username = DjangoApi.parseJSON(result, "username");
                credEditor.putString("username", username);
                credEditor.commit();
                Intent intent = new Intent(LoginVKActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }
}