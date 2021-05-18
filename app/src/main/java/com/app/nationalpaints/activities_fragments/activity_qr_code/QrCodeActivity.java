package com.app.nationalpaints.activities_fragments.activity_qr_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.app.nationalpaints.R;
import com.app.nationalpaints.databinding.ActivityLoginBinding;
import com.app.nationalpaints.databinding.ActivityQrCodeBinding;
import com.app.nationalpaints.language.Language;
import com.app.nationalpaints.models.LoginModel;
import com.app.nationalpaints.preferences.Preferences;
import com.app.nationalpaints.share.Common;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import io.paperdb.Paper;

public class QrCodeActivity extends AppCompatActivity {
    private ActivityQrCodeBinding binding;
    private String lang;
    private CodeScanner mCodeScanner;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final int  CAMERA_REQ = 2;
    private Animation animation;
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_code);
        initView();
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        checkCameraPermission();
        binding.imageBack.setOnClickListener(v -> finish());

    }

    private void initScanner(){

        mCodeScanner = new CodeScanner(this, binding.scannerView);
        mCodeScanner.setScanMode(ScanMode.SINGLE);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            binding.scannerView.setVisibility(View.GONE);
            binding.flData.setVisibility(View.VISIBLE);
            getProduct(result.getText());
        }));
        binding.scannerView.setVisibility(View.VISIBLE);
        mCodeScanner.startPreview();
    }

    private void getProduct(String text) {
        Log.e("text", text);
        slideUp();

    }




    public void checkCameraPermission() {


        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            initScanner();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initScanner();

            }

        }
    }

    private void slideUp(){
        animation = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        binding.flData.clearAnimation();
        binding.flData.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.flData.setVisibility(View.VISIBLE);
                binding.scannerView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void slideDown(){
        animation = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        binding.flData.clearAnimation();
        binding.flData.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.flData.setVisibility(View.GONE);
                binding.scannerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (mCodeScanner!=null){
            mCodeScanner.startPreview();
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.flData.getVisibility()==View.VISIBLE){
            slideDown();
        }else {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCodeScanner!=null){
            mCodeScanner.releaseResources();

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mCodeScanner!=null){
            mCodeScanner.startPreview();

        }
    }
}