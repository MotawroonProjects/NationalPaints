package com.app.nationalpaints.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_paints.PaintsActivity;
import com.app.nationalpaints.activities_fragments.activity_points.PointsActivity;
import com.app.nationalpaints.databinding.PaintRowBinding;
import com.app.nationalpaints.databinding.PointsRowBinding;

import java.util.List;

public class PaintsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private PaintsActivity activity;
    public PaintsAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (PaintsActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PaintRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.paint_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;

        myHolder.binding.tvoldprice.setPaintFlags(myHolder.binding.tvoldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private PaintRowBinding binding;

        public MyHolder(PaintRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }


}
