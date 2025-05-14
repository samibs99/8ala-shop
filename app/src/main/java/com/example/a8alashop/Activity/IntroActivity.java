package com.example.a8alashop.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.a8alashop.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startBtn.setOnClickListener(v -> startActivity(new Intent( IntroActivity.this, MainActivity.class)));
    }
}