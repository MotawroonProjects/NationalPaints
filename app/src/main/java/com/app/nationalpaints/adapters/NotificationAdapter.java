package com.app.nationalpaints.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_notification.NotificationActivity;
import com.app.nationalpaints.databinding.NotificationRowBinding;
import com.app.nationalpaints.models.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NotificationModel> list;
    private Context context;
    private LayoutInflater inflater;
    private NotificationActivity activity;

    public NotificationAdapter(List<NotificationModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (NotificationActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        NotificationRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.notification_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
//
//        myHolder.itemView.setOnClickListener(v -> {
//            activity.setItemData(list.get(holder.getAdapterPosition()));
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public NotificationRowBinding binding;

        public MyHolder(@NonNull NotificationRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
