package com.example.a8alashop.Activity;

import android.content.Intent;
import android.os.Bundle;
import com.example.a8alashop.databinding.ActivitySignInBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends BaseActivity {
  ActivitySignInBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySignInBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initView();
  }

  private void initView() {
    binding.signUpText.setOnClickListener(v -> {
      startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
      finish();
    });

    binding.loginButton.setOnClickListener(v -> {
      String email = binding.usernameInput.getText().toString();
      String password = binding.passwordInput.getText().toString();
      if (validateInput(email, password)) {
        handleSignIn(email, password);
      }
    });
  }

  private boolean validateInput(String email, String password) {
    if (email.isEmpty()) {
      binding.usernameInput.setError("Please enter your email");
      return false;
    }
    if (password.isEmpty()) {
      binding.passwordInput.setError("Please enter your password");
      return false;
    }
    return true;
  }

  private void handleSignIn(String email, String password) {
    showLoading("Signing in...");

    mAuth.signInWithEmailAndPassword(email, password)
      .addOnSuccessListener(authResult -> {
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users").child(uid);

        userRef.get().addOnSuccessListener(snapshot -> {
          hideLoading();
          if (snapshot.exists()) {
            Boolean isAdmin = snapshot.child("isAdmin").getValue(Boolean.class);
            if (isAdmin != null && isAdmin) {
              // Redirection vers interface admin
              startActivity(new Intent(SignInActivity.this, AdminActivity.class));
            } else {
              // Redirection vers interface utilisateur classique
              startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
            finish();
          } else {
            showToast("User data not found.");
          }
        }).addOnFailureListener(e -> {
          hideLoading();
          showToast("Failed to fetch user data: " + e.getMessage());
        });
      })
      .addOnFailureListener(e -> {
        hideLoading();
        showToast("Sign in failed: " + e.getMessage());
      });
  }
}
