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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ShopGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private ShopGalleryActivity activity;
    public ShopGalleryAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (ShopGalleryActivity) context;
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



    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ShopGalleryRowBinding binding;

        public MyHolder(ShopGalleryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }


}