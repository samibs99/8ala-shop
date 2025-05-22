package com.example.a8alashop.Activity; // Déclaration du package

// Imports nécessaires pour Android, Firebase, RecyclerView, etc.
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a8alashop.Adapter.BestDealAdapter;
import com.example.a8alashop.Adapter.CategoryAdapter;
import com.example.a8alashop.Domain.CategoryDomain;
import com.example.a8alashop.Domain.ItemDomain;
import com.example.a8alashop.Domain.LocationDomain;
import com.example.a8alashop.R;
import com.example.a8alashop.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// Déclaration de la classe principale de l'application qui hérite de BaseActivity
public class MainActivity extends BaseActivity {
  ActivityMainBinding binding; // ViewBinding pour accéder facilement aux éléments XML

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Initialise ViewBinding pour cette activité
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    // Applique la vue au layout
    setContentView(binding.getRoot());

    // Initialise la liste des localisations dans le Spinner
    initLocation();
    // Initialise la liste des catégories dans un RecyclerView horizontal
    initCategoryList();
    // Initialise la liste des meilleurs produits dans un autre RecyclerView horizontal
    initBestDealList();
    // Configure le bouton retour pour revenir à l’écran d’intro
    setupRetourButton();
  }

  // Méthode pour configurer l’action du bouton retour
  private void setupRetourButton() {
    binding.retourBtn.setOnClickListener(v -> {
      // Création de l’intention de retourner à IntroActivity
      Intent intent = new Intent(MainActivity.this, IntroActivity.class);
      startActivity(intent); // Lancement de l’activité Intro
      finish(); // Fermeture de MainActivity
    });
  }

  // Méthode pour afficher les meilleurs produits à partir de Firebase
  private void initBestDealList() {
    DatabaseReference myRef = database.getReference("Items"); // Référence à la base Items
    binding.progressBarBestDeal.setVisibility(View.VISIBLE); // Affiche une ProgressBar

    ArrayList<ItemDomain> list = new ArrayList<>(); // Liste des items

    // Récupération des données depuis Firebase une seule fois
    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()) {
          for (DataSnapshot issue : snapshot.getChildren()) {
            // Conversion de chaque élément JSON en objet ItemDomain
            list.add(issue.getValue(ItemDomain.class));
          }

          if (!list.isEmpty()) {
            // Configure le RecyclerView en horizontal
            binding.bestDealView.setLayoutManager(
              new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)
            );
            // Associe l’adapter à la liste récupérée
            binding.bestDealView.setAdapter(new BestDealAdapter(list));
          }
          // Cache la ProgressBar après le chargement
          binding.progressBarBestDeal.setVisibility(View.GONE);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        // En cas d’erreur, aucune action ici
      }
    });
  }

  // Surcharge du comportement du bouton retour Android (pas modifié ici)
  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  // Méthode pour afficher la liste des catégories depuis Firebase
  private void initCategoryList() {
    DatabaseReference myRef = database.getReference("Category"); // Référence aux catégories
    binding.progressBarCategory.setVisibility(View.VISIBLE); // Affiche une ProgressBar

    ArrayList<CategoryDomain> list = new ArrayList<>();

    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()) {
          for (DataSnapshot issue : snapshot.getChildren()) {
            // Conversion de chaque catégorie JSON en objet Java
            list.add(issue.getValue(CategoryDomain.class));
          }

          if (!list.isEmpty()) {
            // RecyclerView horizontal
            binding.catView.setLayoutManager(
              new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)
            );
            // Attache l'adapter
            binding.catView.setAdapter(new CategoryAdapter(list));
          }
          binding.progressBarCategory.setVisibility(View.GONE);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        // Aucune action prévue ici en cas d’erreur
      }
    });
  }

  // Méthode pour remplir le Spinner avec les emplacements disponibles
  private void initLocation() {
    DatabaseReference myref = database.getReference("Location"); // Référence Firebase pour les localisations
    ArrayList<LocationDomain> list = new ArrayList<>();

    myref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()) {
          for (DataSnapshot issue : snapshot.getChildren()) {
            // Chaque localisation est ajoutée à la liste
            list.add(issue.getValue(LocationDomain.class));
          }
        }

        // Crée un adaptateur pour le Spinner avec un layout personnalisé
        ArrayAdapter<LocationDomain> adapter = new ArrayAdapter<>(
          MainActivity.this,
          R.layout.sp_item,
          list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Associe l’adapter au Spinner dans le layout
        binding.locationSp.setAdapter(adapter);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        // Pas de traitement d’erreur ici
      }
    });
  }
}
