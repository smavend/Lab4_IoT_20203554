package com.example.lab4_appiot_20203554;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lab4_appiot_20203554.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonIngresar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(intent);
        });
    }
}