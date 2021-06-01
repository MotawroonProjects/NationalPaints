package com.app.nationalpaints.activities_fragments.activity_shop_gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;

import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_award.AwardActivity;
import com.app.nationalpaints.adapters.ShopGalleryAdapter;
import com.app.nationalpaints.adapters.SpinnerGovernateAdapter;
import com.app.nationalpaints.databinding.ActivityQrCodeBinding;
import com.app.nationalpaints.databinding.ActivityShopGalleryBinding;
import com.app.nationalpaints.language.Language;
import com.app.nationalpaints.models.GovernmentModel;
import com.app.nationalpaints.models.ShopGalleryDataModel;
import com.app.nationalpaints.models.ShopGalleryModel;
import com.app.nationalpaints.models.UserModel;
import com.app.nationalpaints.remote.Api;
import com.app.nationalpaints.share.Common;
import com.app.nationalpaints.tags.Tags;
import com.budiyev.android.codescanner.CodeScanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopGalleryActivity extends AppCompatActivity {

    private ActivityShopGalleryBinding binding;
    private String lang;
    private List<ShopGalleryModel> list;
    private List<GovernmentModel.Data> governmentList;
    private ShopGalleryAdapter adapter;
    private SpinnerGovernateAdapter spinnerCountryAdapter;
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_gallery);
        initView();
    }

    private void initView() {
        governmentList = new ArrayList<>();
        list = new ArrayList<>();

        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShopGalleryAdapter(list,this);
        binding.recView.setAdapter(adapter);
        binding.recView.setItemAnimator( new DefaultItemAnimator());
        binding.llBack.setOnClickListener(v -> finish());
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    getData(null);
                }else {
                    getData(String.valueOf(governmentList.get(position).getId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        updateGovernateData(new ArrayList<>());
        getGovernate();
    }

    private void getData(String governate_id) {
        list.clear();
        adapter.notifyDataSetChanged();
        binding.progBar.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);
        Api.getService(Tags.base_url)
                .getShopGallery(governate_id)
                .enqueue(new Callback<ShopGalleryDataModel>() {
                    @Override
                    public void onResponse(Call<ShopGalleryDataModel> call, Response<ShopGalleryDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus()==200) {
                                if (response.body().getData().size()>0){
                                    binding.tvNoData.setVisibility(View.GONE);
                                    list.addAll(response.body().getData());
                                    adapter.notifyDataSetChanged();
                                }else{
                                    binding.tvNoData.setVisibility(View.VISIBLE);

                                }
                            }
                        } else {
                                binding.progBar.setVisibility(View.GONE);

                            try {
                                Log.e("error_code", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<ShopGalleryDataModel> call, Throwable t) {
                        try {
                            binding.progBar.setVisibility(View.GONE);

                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void getGovernate() {

        Api.getService(Tags.base_url)
                .getGovernate()
                .enqueue(new Callback<GovernmentModel>() {
                    @Override
                    public void onResponse(Call<GovernmentModel> call, Response<GovernmentModel> response) {

                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().size() > 0) {
                                        updateGovernateData(response.body().getData());
                                    }
                                }
                            } else {
                                //   Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {


                            switch (response.code()) {
                                case 500:
                                    //  Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    //    Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<GovernmentModel> call, Throwable t) {
                        try {

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //   Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void updateGovernateData(List<GovernmentModel.Data> data) {


        governmentList.clear();

        GovernmentModel.Data governatemodel = new GovernmentModel.Data("اختر المحافظة", "Choose Government");

        governmentList.add(governatemodel);
        governmentList.addAll(data);
        if(spinnerCountryAdapter==null){
            spinnerCountryAdapter = new SpinnerGovernateAdapter(governmentList, this);
            binding.spinner.setAdapter(spinnerCountryAdapter);
        }else {
            spinnerCountryAdapter.notifyDataSetChanged();
        }


    }

}