package com.app.nationalpaints.activities_fragments.activity_award;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.app.nationalpaints.R;
import com.app.nationalpaints.adapters.AwardAdapter;
import com.app.nationalpaints.adapters.PointsAdapter;
import com.app.nationalpaints.databinding.ActivityAwardBinding;
import com.app.nationalpaints.databinding.ActivityPointsBinding;
import com.app.nationalpaints.language.Language;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class AwardActivity extends AppCompatActivity {
    private ActivityAwardBinding binding;
    private String lang;
    private List<Object> list;
    private AwardAdapter adapter;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_award);
        initView();
    }

    private void initView() {
        list = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new AwardAdapter(list, this);
        binding.recView.setAdapter(adapter);
        binding.recView.setItemAnimator(new DefaultItemAnimator());
        binding.llBack.setOnClickListener(v -> finish());

    }
}