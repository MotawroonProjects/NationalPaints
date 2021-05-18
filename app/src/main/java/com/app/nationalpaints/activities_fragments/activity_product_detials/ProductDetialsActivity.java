package com.app.nationalpaints.activities_fragments.activity_product_detials;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nationalpaints.R;
import com.app.nationalpaints.adapters.ColorsAdapter;
import com.app.nationalpaints.adapters.PaintsAdapter;
import com.app.nationalpaints.databinding.ActivityPaintsBinding;
import com.app.nationalpaints.databinding.ActivityProductDetialsBinding;
import com.app.nationalpaints.language.Language;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProductDetialsActivity extends AppCompatActivity {
    private ActivityProductDetialsBinding binding;
    private String lang;


    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detials);
        initView();
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recView.setAdapter(new ColorsAdapter( this));
        binding.llBack.setOnClickListener(v -> finish());

    }
}