package com.app.nationalpaints.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_award.AwardActivity;
import com.app.nationalpaints.activities_fragments.activity_points.PointsActivity;
import com.app.nationalpaints.databinding.AwardRowBinding;
import com.app.nationalpaints.databinding.PointsRowBinding;

import java.util.List;

public class AwardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private AwardActivity activity;
    public AwardAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (AwardActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AwardRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.award_row, parent, false);
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
        private AwardRowBinding binding;

        public MyHolder(AwardRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }


}
