package com.app.nationalpaints.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nationalpaints.R;
import com.app.nationalpaints.activities_fragments.activity_paints.PaintsActivity;
import com.app.nationalpaints.databinding.ColorsRowBinding;
import com.app.nationalpaints.databinding.PaintRowBinding;
import com.app.nationalpaints.models.ProductModel;

import java.util.List;

public class ColorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel.Color> list;
    private Context context;
    private LayoutInflater inflater;
    public ColorsAdapter( Context context,List<ProductModel.Color> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ColorsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.colors_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;

        myHolder.binding.cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getColor_code()));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ColorsRowBinding binding;

        public MyHolder(ColorsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }


}
