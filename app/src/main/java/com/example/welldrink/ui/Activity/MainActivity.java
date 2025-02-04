package com.example.welldrink.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.welldrink.R;
import com.example.welldrink.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.welldrink.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.home_fragment);
        BottomNavigationView bottomNav = findViewById(R.id.home_navbar);
        NavController navController = null;
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        if (navController != null)
            NavigationUI.setupWithNavController(bottomNav, navController);
        SearchView searchView = findViewById(R.id.home_inpSearch);
        searchView.setOnClickListener(v -> {
            searchView.setIconified(false);
            findViewById(R.id.fragment_research).performClick();
        });
        searchView.setOnSearchClickListener(v -> findViewById(R.id.fragment_research).performClick());
    }

    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this).setTitle(R.string.close_app_title)
                .setMessage(R.string.close_app_desc).setPositiveButton(R.string.close_app_conf, (dialog, which) -> finish())
                .setNegativeButton(R.string.close_app_unconf, null).show();
    }

}