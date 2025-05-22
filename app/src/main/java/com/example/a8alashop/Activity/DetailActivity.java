package com.example.a8alashop.Activity; // Déclare le package de cette classe

// Importation des bibliothèques nécessaires
import android.content.Intent; // Pour gérer les intentions (navigation entre activités)
import android.os.Bundle; // Pour gérer l'état de l'activité
import android.view.View; // Pour manipuler les vues

import androidx.activity.EdgeToEdge; // (non utilisé ici, normalement pour afficher l'UI bord à bord)
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity; // Classe de base pour les activités
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager; // Pour le layout de RecyclerView (non utilisé ici)

import com.bumptech.glide.Glide; // Librairie pour charger des images depuis une URL ou un fichier
import com.example.a8alashop.Adapter.SimilarAdapter; // (import non utilisé ici)
import com.example.a8alashop.Domain.ItemDomain; // Classe représentant un produit/item
import com.example.a8alashop.R; // Accès aux ressources
import com.example.a8alashop.databinding.ActivityDetailBinding; // Liaison de vues avec ViewBinding
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// Déclaration de la classe DetailActivity qui hérite de BaseActivity
public class DetailActivity extends BaseActivity {

  ActivityDetailBinding binding; // Objet généré par ViewBinding pour accéder aux vues de l'interface
  private ItemDomain object; // Objet représentant l’article sélectionné
  private int weight = 1; // Poids par défaut sélectionné par l’utilisateur (1 kg)

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); // Appelle la méthode onCreate de la superclasse

    // Initialisation de ViewBinding pour lier le layout à l’activité
    binding = ActivityDetailBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot()); // Affiche le contenu lié au layout

    getBundles(); // Récupère l'objet passé via l’intent
    setVariable(); // Initialise les actions de l’interface
  }

  // Méthode pour configurer les actions sur les vues
  private void setVariable() {

    // Lorsque l’utilisateur clique sur le bouton "Retour", on ferme l’activité
    binding.backBtn.setOnClickListener(v -> finish());

    // Chargement de l’image de l’article dans la vue image
    Glide.with(DetailActivity.this)
      .load(object.getImagePath())
      .into(binding.img);

    // Mise à jour des textes avec les données de l’article
    binding.priceKgTxt.setText(object.getPrice() + "dt/kg"); // Prix par kg
    binding.titleTxt.setText(object.getTitle()); // Titre de l’article
    binding.descriptionTxt.setText(object.getDescription()); // Description
    binding.ratingBar.setRating((float) object.getStar()); // Étoiles (note)
    binding.ratingTxt.setText("(" + object.getStar() + ")"); // Note sous forme de texte

    // Prix total initial (prix * poids)
    binding.totalTxt.setText((weight * object.getPrice()) + "dt");

    // Action pour bouton + (ajouter 1 kg)
    binding.plusBtn.setOnClickListener(v -> {
      weight = weight + 1; // Incrémente le poids
      binding.weightTxt.setText(weight + "kg"); // Met à jour le texte du poids
      binding.totalTxt.setText(weight * object.getPrice() + "dt"); // Met à jour le prix total
    });

    // Action pour bouton - (retirer 1 kg si supérieur à 1)
    binding.minusBtn.setOnClickListener(v -> {
      if (weight > 1) {
        weight = weight - 1; // Décrémente le poids
        binding.weightTxt.setText(weight + "kg"); // Met à jour le poids affiché
        binding.totalTxt.setText((weight * object.getPrice()) + "dt"); // Met à jour le total
      }
    });
  }

  // Méthode pour récupérer l’objet passé depuis une autre activité via un intent
  private void getBundles() {
    object = (ItemDomain) getIntent().getSerializableExtra("object"); // Cast l’objet reçu
  }
}
