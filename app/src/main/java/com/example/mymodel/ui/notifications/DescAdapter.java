package com.example.mymodel.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymodel.R;
import com.example.mymodel.databinding.ItemDescBinding;
import com.example.mymodel.models.ModelM;

import java.util.ArrayList;
import java.util.Arrays;

public class DescAdapter extends RecyclerView.Adapter<DescAdapter.ViewHolder> {
    ItemDescBinding binding;
    Context context;
    ArrayList<ModelM> listDesc;

    public DescAdapter(Context context, ArrayList<ModelM> listDesc) {
        this.context = context;
        this.listDesc = listDesc;
    }


    @NonNull
    @Override
    public DescAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {binding= ItemDescBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DescAdapter.ViewHolder holder, int position) {
        holder.onBind(listDesc.get(position));
    }

    @Override
    public int getItemCount() {
        return listDesc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemDescBinding itemView) {
            super(itemView.getRoot());
        }
        public void onBind(ModelM modelM){
            binding.titleCard.setText(modelM.getModelTitle());
            binding.priceCard.setText(String.valueOf(modelM.getModelPrice()));
            binding.descCard.setText(modelM.getModelDescription());
            Glide.with(context)
                    .load(modelM.getModelImage())
                    .placeholder(R.drawable.back_shop)
                    .error(R.drawable.back_shop)
                    .into(binding.imageCard);
        }
    }
}
