package com.app.nationalpaints.activities_fragments.activity_home.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_home.HomeActivity;
import com.app.nationalpaints.activities_fragments.activity_sign_up.SignUpActivity;
import com.app.nationalpaints.databinding.FragmentProfileBinding;
import com.app.nationalpaints.models.UserModel;
import com.app.nationalpaints.preferences.Preferences;

import io.paperdb.Paper;

public class Fragment_Profile extends Fragment  {

    private HomeActivity activity;
    private FragmentProfileBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;



    public static Fragment_Profile newInstance() {
        return new Fragment_Profile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
binding.llEdit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
navigateToSignUpActivity();
    }
});


    }
    private void navigateToSignUpActivity() {
        Intent intent = new Intent(activity, SignUpActivity.class);

        startActivity(intent);

    }


}
