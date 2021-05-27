package com.app.nationalpaints.activities_fragments.activity_home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_home.fragments.Fragment_Home;
import com.app.nationalpaints.activities_fragments.activity_home.fragments.Fragment_Profile;
import com.app.nationalpaints.activities_fragments.activity_notification.NotificationActivity;
import com.app.nationalpaints.activities_fragments.activity_paints.PaintsActivity;
import com.app.nationalpaints.activities_fragments.activity_qr_code.QrCodeActivity;
import com.app.nationalpaints.databinding.ActivityHomeBinding;
import com.app.nationalpaints.language.Language;
import com.app.nationalpaints.models.UserModel;
import com.app.nationalpaints.preferences.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Preferences preferences;
    private FragmentManager fragmentManager;
    private Fragment_Home fragment_home;
    private Fragment_Profile fragment_profile;
    private UserModel userModel;
    private String lang;
    private boolean backPressed= false;


    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();


    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);




        displayFragmentMain();


        updateFirebaseToken();
        binding.flNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
//        if (userModel != null) {
//            EventBus.getDefault().register(this);
//
//        }

        binding.bottomNavView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id){
                case R.id.profile:
                    displayFragmentProfile();
                    break;
                default:
                    if (!backPressed){
                        displayFragmentMain();
                    }
                    break;
            }
            return true;
        });

        binding.cardViewScanner.setOnClickListener(v -> {
            Intent intent = new Intent(this, QrCodeActivity.class);
            startActivity(intent);
        });

    }


    public void displayFragmentMain() {
        try {
            if (fragment_home == null) {
                fragment_home = Fragment_Home.newInstance();
            }



            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }

            if (fragment_home.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_home).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_home, "fragment_home").commit();

            }
        } catch (Exception e) {
        }

    }


    public void displayFragmentProfile() {

        try {
            if (fragment_profile == null) {
                fragment_profile = Fragment_Profile.newInstance();
            }


            if (fragment_home != null && fragment_home.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_home).commit();
            }



            if (fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_profile).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_profile, "fragment_profile").commit();

            }
            binding.setTitle(getString(R.string.profile));
        } catch (Exception e) {
        }
    }


    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }

    private void getNotificationCount() {

    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        binding.bottomNavView.setSelectedItemId(R.id.home);
        backPressed = false;

        if (fragment_home != null && fragment_home.isAdded() && fragment_home.isVisible()) {
            finish();
        } else {
            displayFragmentMain();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void updateFirebaseToken() {
      /*  FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult().getToken();
                        try {
                            Api.getService(Tags.base_url)
                                    .updatePhoneToken(userModel.getUser().getToken(), token, userModel.getUser().getId(), "android")
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful() && response.body() != null) {
                                                userModel.getUser().setFirebaseToken(token);
                                                preferences.create_update_userdata(HomeActivity.this, userModel);

                                                Log.e("token", "updated successfully");
                                            } else {
                                                try {

                                                    Log.e("errorToken", response.code() + "_" + response.errorBody().string());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            try {

                                                if (t.getMessage() != null) {
                                                    Log.e("errorToken2", t.getMessage());
                                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                                        Toast.makeText(HomeActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            } catch (Exception e) {
                                            }
                                        }
                                    });
                        } catch (Exception e) {

                        }
                    }
                });
*/
    }

    public void logout() {

        if (userModel==null){
            finish();
            return;
        }
       /* ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .logout("Bearer " + userModel.getUser().getToken(), userModel.getUser().getId(), userModel.getUser().getFirebaseToken())
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                navigateToSignInActivity();
                            }

                        } else {
                            dialog.dismiss();
                            try {
                                Log.e("error", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(HomeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });*/

    }

    public void open() {
        Intent intent=new Intent(HomeActivity.this, PaintsActivity.class);
        startActivity(intent);
    }
}
