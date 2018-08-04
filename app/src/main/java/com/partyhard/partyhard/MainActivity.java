package com.partyhard.partyhard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.partyhard.partyhard.api.DjangoApi;
import com.bumptech.glide.Glide;
import com.partyhard.partyhard.fragments.FavoritesFragment;
import com.partyhard.partyhard.fragments.HomeFragment;
import com.partyhard.partyhard.fragments.SettingsFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            w.getDecorView().setSystemUiVisibility(uiOptions);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadProfileImage();
        configureBottomNavBar();

        //new BackgroundTask().execute();
    }

    private void configureBottomNavBar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_favs:
                            selectedFragment = new FavoritesFragment();
                            break;
                        case  R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    public void logout(View view) {
        SharedPreferences credentials = getSharedPreferences("user_credentials", MODE_PRIVATE);
        SharedPreferences.Editor credEditor = credentials.edit();
        credEditor.putString("token", "");
        credEditor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadProfileImage(){
        SharedPreferences credentials = getSharedPreferences("user_credentials", MODE_PRIVATE);
        String avatar = credentials.getString("avatar", "");
        avatar = avatar.contains("http") ? avatar : DjangoApi.SERVER + avatar;
        CircleImageView imageView = (CircleImageView)findViewById(R.id.profile_image);
        Glide.with(MainActivity.this)
                .load(avatar)
                .into(imageView);
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
