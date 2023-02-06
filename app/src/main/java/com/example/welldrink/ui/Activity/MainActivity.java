package com.example.welldrink.ui.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.welldrink.R;
import com.example.welldrink.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.welldrink.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.home_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.home_navbar);
        NavigationUI.setupWithNavController(bottomNav, navController);
        SearchView searchView = findViewById(R.id.home_inpSearch);
        searchView.setOnClickListener(v -> {
            searchView.setIconified(false);
            findViewById(R.id.fragment_research).performClick();
        });
        searchView.setOnSearchClickListener(v -> {
            findViewById(R.id.fragment_research).performClick();
        });
    }

    private int getCheckedItem(BottomNavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.isChecked()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.close_app_title)
                .setMessage(R.string.close_app_desc).setPositiveButton(R.string.close_app_conf, (dialog, which) -> finish())
                .setNegativeButton(R.string.close_app_unconf, null).show();
    }

}