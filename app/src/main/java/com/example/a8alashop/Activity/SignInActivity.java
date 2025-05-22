package com.example.a8alashop.Activity; // Déclaration du package

// Imports nécessaires pour les activités Android
import android.content.Intent;
import android.os.Bundle;

import com.example.a8alashop.databinding.ActivitySignInBinding;

// Activité de connexion qui hérite de BaseActivity (probablement une classe utilitaire de ton projet)
public class SignInActivity extends BaseActivity {
  ActivitySignInBinding binding; // ViewBinding pour accéder aux éléments de l’interface facilement

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Initialisation du binding avec le layout correspondant à l'activité
    binding = ActivitySignInBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot()); // On applique la vue liée

    initView(); // Initialisation des événements liés aux éléments de la vue
  }

  // Méthode pour initialiser les listeners des boutons
  private void initView() {
    // Quand on clique sur "Sign Up", on lance l’activité SignUpActivity
    binding.signUpText.setOnClickListener(v -> {
      startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
      finish(); // On termine l’activité actuelle pour ne pas revenir en arrière
    });

    // Quand on clique sur "Login"
    binding.loginButton.setOnClickListener(v -> {
      // Récupération de l'email et du mot de passe depuis les champs de texte
      String email = binding.usernameInput.getText().toString();
      String password = binding.passwordInput.getText().toString();

      // Si les champs sont valides, on continue avec la tentative de connexion
      if (validateInput(email, password)) {
        handleSignIn(email, password); // Méthode pour se connecter avec Firebase
      }
    });
  }

  // Méthode pour valider les champs email et mot de passe
  private boolean validateInput(String email, String password) {
    // Si l'email est vide, on affiche une erreur
    if (email.isEmpty()) {
      binding.usernameInput.setError("Please enter your email");
      return false;
    }

    // Si le mot de passe est vide, on affiche une erreur
    if (password.isEmpty()) {
      binding.passwordInput.setError("Please enter your password");
      return false;
    }

    return true; // Les deux champs sont valides
  }

  // Méthode pour gérer la connexion avec Firebase Authentication
  private void handleSignIn(String email, String password) {
    showLoading("Signing in..."); // Affiche un message de chargement

    // Utilise FirebaseAuth (mAuth hérité de BaseActivity ?) pour se connecter
    mAuth.signInWithEmailAndPassword(email, password)
      .addOnSuccessListener(authResult -> {
        hideLoading(); // Cache le chargement
        // Redirige vers l’activité principale après une connexion réussie
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish(); // Termine SignInActivity
      })
      .addOnFailureListener(e -> {
        hideLoading(); // Cache le chargement
        // Affiche un message d’erreur
        showToast("Sign in failed: " + e.getMessage());
      });
  }
}
