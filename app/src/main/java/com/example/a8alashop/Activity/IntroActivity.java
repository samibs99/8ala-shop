package com.example.a8alashop.Activity; // Déclaration du package de l’activité

import android.content.Intent; // Import nécessaire pour lancer une nouvelle activité
import android.os.Bundle; // Pour gérer les états lors du cycle de vie de l’activité

import com.example.a8alashop.databinding.ActivityIntroBinding; // Import de ViewBinding pour cette activité

// Déclaration de la classe IntroActivity qui hérite de BaseActivity
public class IntroActivity extends BaseActivity {

  ActivityIntroBinding binding; // Liaison entre le layout XML et le code Java grâce à ViewBinding

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); // Appelle la méthode de la superclasse

    // Initialise le binding pour accéder aux éléments du layout XML
    binding = ActivityIntroBinding.inflate(getLayoutInflater());

    // Définit le layout de cette activité à partir de l’objet binding
    setContentView(binding.getRoot());

    // Appelle la méthode qui gère les événements des boutons
    setVariable();
  }

  // Méthode qui définit les actions à effectuer lorsque l'utilisateur clique sur les boutons
  private void setVariable() {

    // Lorsque l'utilisateur clique sur "Commencer" (startBtn), il est redirigé vers MainActivity
    binding.startBtn.setOnClickListener(v ->
      startActivity(new Intent(IntroActivity.this, MainActivity.class))
    );

    // Lorsque l'utilisateur clique sur "Se connecter" (signInBtn), il est redirigé vers SignInActivity
    // et l’activité IntroActivity est fermée avec finish()
    binding.signInBtn.setOnClickListener(v -> {
      startActivity(new Intent(IntroActivity.this, SignInActivity.class));
      finish(); // Ferme IntroActivity pour éviter de revenir dessus avec le bouton "Retour"
    });
  }
}
