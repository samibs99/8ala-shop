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

/**
 * Adapter for displaying a list of "Best Deal" items in a RecyclerView.
 * Handles item rendering, click events, and navigation to detail activity.
 */
public class BestDealAdapter extends RecyclerView.Adapter<BestDealAdapter.Viewholder> {
  private ArrayList<ItemDomain> items; // List of items to display
  private Context context; // Context for resource access and activity launches

  public BestDealAdapter(ArrayList<ItemDomain> items) {
    this.items = items;
  }

  /**
   * Creates ViewHolder instances by inflating the item layout using data binding.
   */
  @NonNull
  @Override
  public BestDealAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    context = parent.getContext();
    // Inflate the layout using View Binding
    ViewholderBestDealBinding binding = ViewholderBestDealBinding.inflate(
      LayoutInflater.from(parent.getContext()), parent, false
    );
    return new Viewholder(binding);
  }

  /**
   * Binds data to the ViewHolder, setting item content and click listeners.
   * Uses Glide for efficient image loading.
   */
  @Override
  public void onBindViewHolder(@NonNull BestDealAdapter.Viewholder holder, int position) {
    ItemDomain item = items.get(position);

    // Set text content
    holder.binding.titleTxt.setText(item.getTitle());
    holder.binding.priceTxt.setText(item.getPrice() + "dt/Kg");

    // Load image with Glide to handle caching and efficient loading
    Glide.with(context)
      .load(item.getImagePath())
      .into(holder.binding.img);

    // Handle item click - navigate to DetailActivity with item data
    holder.itemView.setOnClickListener(v -> {
      Intent intent = new Intent(context, DetailActivity.class);
      intent.putExtra("object", item); // Pass serializable ItemDomain
      context.startActivity(intent);
    });
  }

  /**
   * @return Total number of items in the list
   */
  @Override
  public int getItemCount() {
    return items.size();
  }

  /**
   * ViewHolder class that holds references to the views via data binding.
   */
  public class Viewholder extends RecyclerView.ViewHolder {
    ViewholderBestDealBinding binding;

    public Viewholder(ViewholderBestDealBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
