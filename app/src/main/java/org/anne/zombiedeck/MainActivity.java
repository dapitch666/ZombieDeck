package org.anne.zombiedeck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.anne.zombiedeck.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}