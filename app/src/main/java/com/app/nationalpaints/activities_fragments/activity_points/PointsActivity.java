package com.app.nationalpaints.activities_fragments.activity_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.app.nationalpaints.R;
import com.app.nationalpaints.adapters.PointsAdapter;
import com.app.nationalpaints.adapters.ShopGalleryAdapter;
import com.app.nationalpaints.databinding.ActivityPointsBinding;
import com.app.nationalpaints.databinding.ActivityShopGalleryBinding;
import com.app.nationalpaints.language.Language;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PointsActivity extends AppCompatActivity {
    private ActivityPointsBinding binding;
    private String lang;
    private List<Object> list;
    private PointsAdapter adapter;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_points);
        initView();
    }

    private void initView() {
        list = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PointsAdapter(list, this);
        binding.recView.setAdapter(adapter);
        binding.recView.setItemAnimator(new DefaultItemAnimator());
        binding.llBack.setOnClickListener(v -> finish());

    }
}