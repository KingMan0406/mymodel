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
    ItemProductBinding binding;
    Context context;
    List<ModelM> list;
    ArrayList<ModelM> selected_list = new ArrayList<>();
    ArrayList<ModelM> getSelected_intoBasketList = new ArrayList<>();
    NavController navController;

    public JemAdapter(Context context, List<ModelM> list) {
        this.context = context;
        this.list = list;
    }

    public ArrayList<ModelM> getSelected_intoBasketList() {
        return getSelected_intoBasketList;
    }

    @NonNull
    @Override
    public JemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemProductBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JemAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));

    }
    public  Double getItemPrice(int position){
        return list.get(position).getModelPrice();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;
        public ViewHolder(@NonNull ItemProductBinding itemView) {
            super(itemView.getRoot());
            this.binding =itemView;
        }

        public void onBind(ModelM modelM) {
            binding.nameProductCard.setText(modelM.getModelTitle());
            binding.priceCard.setText(String.valueOf(modelM.getModelPrice()));
            binding.descriptionCard.setText(modelM.getModelDescription());

            Glide.with(context)
                    .load(list.get(getAdapterPosition()).getModelImage())
                    .placeholder(R.drawable.back_shop)
                    .error(R.drawable.back_shop)
                    .into(binding.imageCard);
            binding.btnZoom.setOnClickListener(v->{
                selected_list.add(modelM);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("see more",selected_list);
                navController= Navigation.findNavController((Activity) itemView.getContext(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_description,bundle);
                Log.e("TAG","pass data to descroptio !!!");
            });
            itemView.setOnClickListener(v1->{
                if(binding.tovarFavoriteCheck.getVisibility()==View.INVISIBLE){
                    binding.tovarFavoriteCheck.setVisibility(View.VISIBLE);
                    getSelected_intoBasketList.add(modelM);
                }else {
                    binding.tovarFavoriteCheck.setVisibility(View.INVISIBLE);
                    getSelected_intoBasketList.remove(modelM);
                }
            });
        }
    }

}
