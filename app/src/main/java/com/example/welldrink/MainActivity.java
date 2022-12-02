package com.example.welldrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        if (navHostFragment == null)
            Log.d(TAG, "Null");
        else {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView bottomNav = findViewById(R.id.home_navbar);
            AppBarConfiguration appBarConfiguration =
                    new AppBarConfiguration.Builder(R.id.researchFragment, R.id.mainFragment, R.id.profileFragment).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(bottomNav, navController);
        }
    }
}