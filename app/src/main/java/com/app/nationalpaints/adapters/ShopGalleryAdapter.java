package com.app.nationalpaints.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_shop_gallery.ShopGalleryActivity;
import com.app.nationalpaints.databinding.ShopGalleryRowBinding;
import com.app.nationalpaints.models.ShopGalleryModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class ShopGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopGalleryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private ShopGalleryActivity activity;
    private String lang = "ar";
    public ShopGalleryAdapter(List<ShopGalleryModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (ShopGalleryActivity) context;
        Paper.init(context);
        lang = Paper.book().read("lang","ar");
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ShopGalleryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.shop_gallery_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setLang(lang);
        myHolder.binding.setModel(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ShopGalleryRowBinding binding;

        public MyHolder(ShopGalleryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }


}
