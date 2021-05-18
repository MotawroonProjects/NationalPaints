package com.app.nationalpaints.activities_fragments.activity_shop_gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;

import com.app.nationalpaints.R;
import com.app.nationalpaints.adapters.ShopGalleryAdapter;
import com.app.nationalpaints.databinding.ActivityQrCodeBinding;
import com.app.nationalpaints.databinding.ActivityShopGalleryBinding;
import com.app.nationalpaints.language.Language;
import com.budiyev.android.codescanner.CodeScanner;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ShopGalleryActivity extends AppCompatActivity {

    private ActivityShopGalleryBinding binding;
    private String lang;
    private List<Object> list;
    private ShopGalleryAdapter adapter;
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
        list = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShopGalleryAdapter(list,this);
        binding.recView.setAdapter(adapter);
        binding.recView.setItemAnimator( new DefaultItemAnimator());
        binding.llBack.setOnClickListener(v -> finish());

    }
}