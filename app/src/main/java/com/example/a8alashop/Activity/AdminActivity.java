package com.example.a8alashop.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.a8alashop.databinding.ActivityAdminBinding;

public class AdminActivity extends BaseActivity {
  ActivityAdminBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityAdminBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Bouton logout
    binding.logoutBtn.setOnClickListener(v -> {
      mAuth.signOut();
      Intent intent = new Intent(AdminActivity.this, SignInActivity.class);
      startActivity(intent);
      finish();
    });

    // Bouton add product - redirige vers AddProductActivity
    binding.addProductBtn.setOnClickListener(v -> {
      Intent intent = new Intent(AdminActivity.this, AddProductActivity.class);
      startActivity(intent);
      // Ne pas faire finish ici, on peut revenir à AdminActivity après l'ajout
    });
  }
}
