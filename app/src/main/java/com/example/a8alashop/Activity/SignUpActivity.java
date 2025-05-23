package com.example.a8alashop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import com.example.a8alashop.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Activity permettant à un utilisateur de créer un compte (inscription).
 * Elle gère la saisie des informations, leur validation, la création de l'utilisateur
 * via Firebase Authentication, puis l'enregistrement des données utilisateur dans Firebase Realtime Database.
 */
public class SignUpActivity extends BaseActivity {

  // Binding automatique généré par View Binding pour accéder aux vues du layout activity_sign_up.xml
  ActivitySignUpBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Initialisation du binding pour lier la vue
    binding = ActivitySignUpBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Initialisation des interactions avec les éléments de l'interface
    initView();
  }

  /**
   * Configure les actions des éléments UI comme les boutons et textes cliquables.
   */
  private void initView() {
    // Quand l'utilisateur clique sur "Se connecter" (texte), on ouvre la page de connexion (SignInActivity)
    binding.signInText.setOnClickListener(v -> {
      startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
      finish(); // Ferme l'activité actuelle
    });

    // Quand l'utilisateur clique sur le bouton d'inscription, on récupère les données et on valide le formulaire
    binding.signUpButton.setOnClickListener(v -> {
      String username = binding.usernameInput.getText().toString();
      String email = binding.emailInput.getText().toString();
      String password = binding.passwordInput.getText().toString();

      // Validation des champs avant inscription
      if (validateInput(username, email, password)) {
        handleSignUp(username, email, password);
      }
    });
  }

  /**
   * Valide les champs saisis par l'utilisateur.
   * Vérifie que le nom d'utilisateur, l'email et le mot de passe respectent les critères.
   *
   * @param username nom d'utilisateur saisi
   * @param email adresse email saisie
   * @param password mot de passe saisi
   * @return true si tous les champs sont valides, false sinon
   */
  private boolean validateInput(String username, String email, String password) {
    if (username.isEmpty()) {
      binding.usernameInput.setError("Please enter a username");
      return false;
    }
    if (email.isEmpty()) {
      binding.emailInput.setError("Please enter an email");
      return false;
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      binding.emailInput.setError("Invalid email address");
      return false;
    }
    if (password.isEmpty()) {
      binding.passwordInput.setError("Please enter a password");
      return false;
    }
    if (password.length() < 6) {
      binding.passwordInput.setError("Password must be at least 6 characters");
      return false;
    }
    return true;
  }

  /**
   * Lance la création de compte Firebase Authentication avec email et mot de passe.
   * En cas de succès, sauvegarde les informations de l'utilisateur dans la base de données Firebase Realtime Database.
   *
   * @param username nom d'utilisateur à enregistrer
   * @param email adresse email pour l'authentification
   * @param password mot de passe pour l'authentification
   */
  private void handleSignUp(String username, String email, String password) {
    showLoading("Creating account..."); // Affiche un indicateur de chargement

    // Crée un utilisateur avec email et mot de passe dans Firebase Authentication
    mAuth.createUserWithEmailAndPassword(email, password)
      .addOnSuccessListener(authResult -> {
        // Récupère l'utilisateur actuellement connecté (créé)
        FirebaseUser user = mAuth.getCurrentUser();
        // Référence à la base de données pour stocker les données de cet utilisateur sous "users/{uid}"
        DatabaseReference userRef = database.getReference("users").child(user.getUid());

        // Prépare les données utilisateur à enregistrer
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("isAdmin", false);
        userData.put("createdAt", ServerValue.TIMESTAMP); // Timestamp serveur de création

        // Enregistre les données utilisateur dans la base de données Firebase Realtime Database
        userRef.setValue(userData)
          .addOnSuccessListener(aVoid -> {
            hideLoading(); // Cache l'indicateur de chargement
            showToast("Account created successfully"); // Affiche un message de succès
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class)); // Redirige vers la connexion
            finish(); // Ferme l'activité d'inscription
          })
          .addOnFailureListener(e -> {
            hideLoading();
            showToast("Failed to save user data: " + e.getMessage()); // Message en cas d'erreur de sauvegarde
          });
      })
      .addOnFailureListener(e -> {
        hideLoading();
        showToast("Sign up failed: " + e.getMessage()); // Message en cas d'erreur d'inscription
      });
  }
}
