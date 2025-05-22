package com.example.a8alashop.Activity; // Déclare le package de cette classe

// Importation des classes nécessaires
import android.app.ProgressDialog; // Pour afficher une boîte de dialogue de chargement
import android.os.Bundle; // Pour gérer les données lors du cycle de vie d'une activité
import android.view.WindowManager; // Pour configurer les paramètres de la fenêtre
import android.widget.Toast; // Pour afficher de petits messages à l’utilisateur

import androidx.appcompat.app.AppCompatActivity; // Classe de base pour les activités compatibles avec les anciennes versions Android

import com.google.firebase.auth.FirebaseAuth; // Pour gérer l'authentification avec Firebase
import com.google.firebase.database.FirebaseDatabase; // Pour accéder à la base de données Realtime Firebase

// Déclaration de la classe BaseActivity, qui hérite de AppCompatActivity
public class BaseActivity extends AppCompatActivity {

  // Déclaration des attributs Firebase et UI
  FirebaseDatabase database; // Objet pour interagir avec la base de données Firebase
  FirebaseAuth mAuth; // Objet pour gérer l’authentification Firebase
  ProgressDialog progressDialog; // Boîte de dialogue pour indiquer un chargement

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); // Appelle la méthode onCreate de la superclasse

    // Configure la fenêtre pour être affichée en plein écran, sans bordures ni barres
    getWindow().setFlags(
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    );

    // Initialise la connexion à la base de données Firebase
    database = FirebaseDatabase.getInstance();

    // Initialise le système d'authentification Firebase
    mAuth = FirebaseAuth.getInstance();

    // Crée une boîte de dialogue de chargement
    progressDialog = new ProgressDialog(this);

    // Rend la boîte de dialogue non annulable par l'utilisateur
    progressDialog.setCancelable(false);
  }

  // Méthode pour afficher la boîte de chargement avec un message personnalisé
  protected void showLoading(String message) {
    progressDialog.setMessage(message); // Définit le message à afficher
    progressDialog.show(); // Affiche la boîte de dialogue
  }

  // Méthode pour masquer la boîte de chargement si elle est visible
  protected void hideLoading() {
    if (progressDialog.isShowing()) { // Vérifie si la boîte de dialogue est actuellement visible
      progressDialog.dismiss(); // La ferme
    }
  }

  // Méthode pour afficher un message court à l'utilisateur sous forme de Toast
  protected void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // Affiche le message Toast
  }
}
