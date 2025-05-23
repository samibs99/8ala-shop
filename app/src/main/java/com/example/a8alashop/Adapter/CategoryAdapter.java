package com.example.a8alashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a8alashop.Domain.CategoryDomain;
import com.example.a8alashop.databinding.ViewholderCategoryBinding;

import java.util.ArrayList;

/**
 * Adapter for displaying category items in a RecyclerView.
 * Shows category names and images from a list of CategoryDomain objects.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
  private ArrayList<CategoryDomain> items; // List of categories to display
  private Context context; // Context for resource access

  public CategoryAdapter(ArrayList<CategoryDomain> items) {
    this.items = items;
  }

  /**
   * Creates ViewHolder instances using data binding.
   * Inflates the category item layout defined in viewholder_category.xml
   */
  @NonNull
  @Override
  public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    context = parent.getContext();
    ViewholderCategoryBinding binding = ViewholderCategoryBinding.inflate(
      LayoutInflater.from(parent.getContext()),
      parent,
      false
    );
    return new Viewholder(binding);
  }

  /**
   * Binds category data to the ViewHolder.
   * Sets category name and loads image using Glide for optimized performance.
   */
  @Override
  public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, int position) {
    // Set category name
    holder.binding.titleTxt.setText(items.get(position).getName());

    // Load category image using Glide library
    Glide.with(context)
      .load(items.get(position).getImagePath())
      .into(holder.binding.img);
  }

  /**
   * @return Total number of categories in the list
   */
  @Override
  public int getItemCount() {
    return items.size();
  }

  /**
   * ViewHolder class that maintains references to the category item views
   * using data binding to eliminate manual view lookups.
   */
  public static class Viewholder extends RecyclerView.ViewHolder {
    ViewholderCategoryBinding binding;

    public Viewholder(ViewholderCategoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
