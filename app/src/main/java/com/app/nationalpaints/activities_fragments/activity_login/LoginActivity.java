package com.app.nationalpaints.activities_fragments.activity_login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_home.HomeActivity;
import com.app.nationalpaints.activities_fragments.activity_verification_code.VerificationCodeActivity;
import com.app.nationalpaints.adapters.CountriesAdapter;
import com.app.nationalpaints.databinding.ActivityLoginBinding;
import com.app.nationalpaints.databinding.DialogCountriesBinding;
import com.app.nationalpaints.language.Language;
import com.app.nationalpaints.models.CountryModel;
import com.app.nationalpaints.models.LoginModel;
import com.app.nationalpaints.preferences.Preferences;
import com.app.nationalpaints.share.Common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String lang;
    private LoginModel loginModel;
    private Preferences preferences;
    private CountryModel[] countries;
    private List<CountryModel> countryModelList = new ArrayList<>();
    private CountriesAdapter countriesAdapter;
    private AlertDialog dialog;
    private String phone_code = "+20";

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = new Fade();
            transition.setInterpolator(new LinearInterpolator());
            transition.setDuration(500);
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);

        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    private void initView() {
        countries = new CountryModel[]{new CountryModel("EG", getResources().getString(R.string.egypt), "+20", R.drawable.flag_eg, "EGP"),
                new CountryModel("SA", getResources().getString(R.string.saudi_arabia), "+966", R.drawable.flag_sa, "SAR")};
        countryModelList = new ArrayList<>(Arrays.asList(countries));

        preferences = Preferences.getInstance();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        loginModel = new LoginModel();
        binding.setModel(loginModel);
        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().startsWith("0")) {
                    binding.edtPhone.setText("");
                }
            }
        });

        binding.btnLogin.setOnClickListener(view -> {
            if (loginModel.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edtPhone);
                login();
            }
        });


        binding.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        //    countryDialog();
        sortCountries();
        createCountriesDialog();
    }


    private void createCountriesDialog() {

        dialog = new AlertDialog.Builder(this)
                .create();
        countriesAdapter = new CountriesAdapter(countryModelList, this);

        DialogCountriesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_countries, null, false);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(countriesAdapter);

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
    }

    private void sortCountries() {
        Collections.sort(countryModelList, (country1, country2) -> {
            return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());
        });
    }

    public void setItemData(CountryModel countryModel) {
        dialog.dismiss();
        phone_code = countryModel.getDialCode();
        binding.tvCode.setText(countryModel.getDialCode());
        binding.image.setImageResource(countryModel.getFlag());
        loginModel.setPhone_code(phone_code);
    }



    private void navigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void login() {
        navigateToConfirmCode();


    }

    private void navigateToConfirmCode() {
        Intent intent = new Intent(this, VerificationCodeActivity.class);
        intent.putExtra("phone", loginModel.getPhone());
        intent.putExtra("phone_code", loginModel.getPhone_code());
        startActivity(intent);
        finish();
    }


}