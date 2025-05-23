package com.example.a8alashop.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a8alashop.databinding.ActivityAddProductBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {
  private ActivityAddProductBinding binding;

  private Uri selectedImageUri = null;
  private StorageReference storageReference;
  private DatabaseReference productsRef;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAddProductBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    storageReference = FirebaseStorage.getInstance().getReference("product_images");
    productsRef = FirebaseDatabase.getInstance().getReference("Items");

    ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
      new ActivityResultContracts.GetContent(),
      uri -> {
        if (uri != null) {
          selectedImageUri = uri;
          binding.productImage.setImageURI(uri);
        }
      });

    binding.selectImageBtn.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

    binding.addProductBtn.setOnClickListener(v -> {
      String name = binding.nameInput.getText().toString().trim();
      String priceStr = binding.priceInput.getText().toString().trim();
      String description = binding.descriptionInput.getText().toString().trim();

      if (name.isEmpty()) {
        binding.nameInput.setError("Veuillez entrer le nom du produit");
        return;
      }
      if (priceStr.isEmpty()) {
        binding.priceInput.setError("Veuillez entrer le prix");
        return;
      }
      if (description.isEmpty()) {
        binding.descriptionInput.setError("Veuillez entrer une description");
        return;
      }


      addProduct(name, priceStr, description, selectedImageUri);
    });
  }

  private void addProduct(String name, String price, String description, Uri imageUri) {

    if (imageUri != null) {
      // Image sélectionnée => upload
      StorageReference fileRef = storageReference.child(System.currentTimeMillis() + ".jpg");
      fileRef.putFile(imageUri)
        .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl()
          .addOnSuccessListener(uri -> {
            saveProductToDatabase(name, price, description, uri.toString());
          })
          .addOnFailureListener(e -> {
            Toast.makeText(this, "Erreur récupération URL image : " + e.getMessage(), Toast.LENGTH_SHORT).show();
          }))
        .addOnFailureListener(e -> {
          Toast.makeText(this, "Erreur upload image : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    } else {
      // Pas d'image sélectionnée => on enregistre sans image
      saveProductToDatabase(name, price, description, null);
    }
  }
  private void saveProductToDatabase(String name, String price, String description, String imageUrl) {
    HashMap<String, Object> productData = new HashMap<>();
    productData.put("Title", name);
    productData.put("Price", Double.parseDouble(price));
    productData.put("Description", description);

    if (imageUrl != null) {
      productData.put("imageUrl", imageUrl);
    } else {
      productData.put("imageUrl", ""); // ou null, ou une URL par défaut si tu veux
    }

    productData.put("CategoryId", 0); // adapte si tu veux un choix

    String productId = productsRef.push().getKey();

    productsRef.child(productId).setValue(productData)
      .addOnSuccessListener(aVoid -> {
        Toast.makeText(this, "Produit ajouté avec succès", Toast.LENGTH_SHORT).show();
        finish();
      })
      .addOnFailureListener(e -> {
        Toast.makeText(this, "Erreur ajout produit : " + e.getMessage(), Toast.LENGTH_SHORT).show();
      });
  }
}

