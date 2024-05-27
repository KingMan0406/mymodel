package com.example.mymodel.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymodel.R;
import com.example.mymodel.databinding.ItemProductBinding;
import com.example.mymodel.models.ModelM;

import java.util.ArrayList;
import java.util.List;

public class JemAdapter extends RecyclerView.Adapter<JemAdapter.ViewHolder> {

    private Context context;
    private List<ModelM> list;
    private ArrayList<ModelM> selected_list = new ArrayList<>();
    private ArrayList<ModelM> getSelected_intoBasketList = new ArrayList<>();
    private NavController navController;
    private OnQuantityChangeListener quantityChangeListener;
    private boolean isBasketView;

    public JemAdapter(Context context, List<ModelM> list, OnQuantityChangeListener quantityChangeListener, boolean isBasketView) {
        this.context = context;
        this.list = list;
        this.quantityChangeListener = quantityChangeListener;
        this.isBasketView = isBasketView;
    }

    public ArrayList<ModelM> getSelected_intoBasketList() {
        return getSelected_intoBasketList;
    }

    @NonNull
    @Override
    public JemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemProductBinding binding = ItemProductBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JemAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemProductBinding binding;

        public ViewHolder(@NonNull ItemProductBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(ModelM modelM) {
            binding.nameProductCard.setText(modelM.getModelTitle());
            binding.priceCard.setText(String.valueOf(modelM.getModelPrice()));
            binding.descriptionCard.setText(modelM.getModelDescription());
            binding.productQuantity.setText(String.valueOf(modelM.getQuantity()));

            Glide.with(context).load(modelM.getModelImage()).into(binding.imageCard);

            if (isBasketView) {
                binding.btnIncrease.setVisibility(View.VISIBLE);
                binding.btnDecrease.setVisibility(View.VISIBLE);
                binding.productQuantity.setVisibility(View.VISIBLE);
                binding.btnIncrease.setOnClickListener(v -> {
                    modelM.setQuantity(modelM.getQuantity() + 1);
                    notifyItemChanged(getAdapterPosition());
                    quantityChangeListener.onQuantityChanged();
                });
                binding.btnDecrease.setOnClickListener(v -> {
                    if (modelM.getQuantity() > 1) {
                        modelM.setQuantity(modelM.getQuantity() - 1);
                        notifyItemChanged(getAdapterPosition());
                        quantityChangeListener.onQuantityChanged();
                    }
                });
            } else {
                binding.btnIncrease.setVisibility(View.GONE);
                binding.btnDecrease.setVisibility(View.GONE);
                binding.textRemaining.setVisibility(View.VISIBLE);
                binding.productQuantity.setVisibility(View.VISIBLE); // Показываем количество
                binding.getRoot().setOnClickListener(v -> {
                    if (getSelected_intoBasketList.contains(modelM)) {
                        getSelected_intoBasketList.remove(modelM);
                        binding.tovarFavoriteCheck.setVisibility(View.INVISIBLE);
                    } else {
                        modelM.setQuantity(1); // Устанавливаем количество в 1 при добавлении в корзину
                        getSelected_intoBasketList.add(modelM);
                        binding.tovarFavoriteCheck.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }
}
