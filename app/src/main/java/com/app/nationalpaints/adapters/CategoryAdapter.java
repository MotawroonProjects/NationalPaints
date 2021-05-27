package com.app.nationalpaints.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_award.AwardActivity;
import com.app.nationalpaints.activities_fragments.activity_home.HomeActivity;
import com.app.nationalpaints.databinding.AwardRowBinding;
import com.app.nationalpaints.databinding.CategoryRowBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
   // private AwardActivity activity;
    public CategoryAdapter( Context context) {
       // this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
      //  activity = (AwardActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.category_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
myHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        HomeActivity activity=(HomeActivity)context;
        activity.open();
    }
});


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CategoryRowBinding binding;

        public MyHolder(CategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }


}
