package com.app.nationalpaints.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_login.LoginActivity;
import com.app.nationalpaints.databinding.ActivitySplashBinding;
import com.app.nationalpaints.language.Language;
import com.app.nationalpaints.models.UserModel;
import com.app.nationalpaints.preferences.Preferences;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private Preferences preference;
    private UserModel userModel;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        preference = Preferences.getInstance();
        userModel = preference.getUserData(this);
        new Handler().postDelayed(() -> {
            Intent intent;
            if (userModel==null){
                intent = new Intent(this, LoginActivity.class);

            }else {
                intent = new Intent(this, LoginActivity.class);

                //  intent = new Intent(this, HomeActivity.class);

            }
            startActivity(intent);

            finish();

        },2000);
    }
}