package com.example.a8alashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a8alashop.Activity.DetailActivity;
import com.example.a8alashop.Domain.ItemDomain;
import com.example.a8alashop.databinding.ViewholderBestDealBinding;

import java.util.ArrayList;

public class BestDealAdapter extends RecyclerView.Adapter<BestDealAdapter.Viewholder> {
  ArrayList<ItemDomain> items;
  Context context;

  public BestDealAdapter(ArrayList<ItemDomain> items) {
    this.items = items;
  }

  @NonNull
  @Override
  public BestDealAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    context= parent.getContext();
    ViewholderBestDealBinding binding=ViewholderBestDealBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    return new Viewholder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull BestDealAdapter.Viewholder holder, int position) {
    ItemDomain item = items.get(position);
    holder.binding.titleTxt.setText(item.getTitle());
    holder.binding.priceTxt.setText(item.getPrice() + "dt/Kg");
    Glide.with(context).load(item.getImagePath()).into(holder.binding.img);

    holder.itemView.setOnClickListener(v -> {
      Intent intent = new Intent(context, DetailActivity.class);
      intent.putExtra("object", item);
      context.startActivity(intent);
    });
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public class Viewholder extends RecyclerView.ViewHolder {
    ViewholderBestDealBinding binding;
    public Viewholder(ViewholderBestDealBinding binding) {
      super(binding.getRoot());
      this.binding=binding;
    }
  }
}
