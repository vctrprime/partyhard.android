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

    SharedPreferences credentials;
    HomeFragment homeFragment;
    FavoritesFragment favoritesFragment;
    SettingsFragment settingsFragment;

    Fragment currentFragment;


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
        createFragments();
        loadProfileImage();
        configureBottomNavBar();
    }

    private void createFragments(){
        credentials = getSharedPreferences("user_credentials", MODE_PRIVATE);
        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        settingsFragment = new SettingsFragment();

    }

    private void configureBottomNavBar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                settingsFragment).hide(settingsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                favoritesFragment).hide(favoritesFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                homeFragment).commit();
        currentFragment = homeFragment;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            currentFragment = homeFragment;
                            break;
                        case R.id.nav_favs:
                            currentFragment = favoritesFragment;
                            break;
                        case  R.id.nav_settings:
                            currentFragment = settingsFragment;
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().show(currentFragment).commit();


                    return true;
                }
            };

    public void logout(View view) {
        SharedPreferences.Editor credEditor = credentials.edit();
        credEditor.putString("token", "");
        credEditor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadProfileImage(){
        String avatar = credentials.getString("avatar", "");
        avatar = avatar.contains("http") ? avatar : DjangoApi.SERVER + avatar;
        CircleImageView imageView = (CircleImageView)findViewById(R.id.profile_image);
        Glide.with(MainActivity.this)
                .load(avatar)
                .into(imageView);
    }
}
