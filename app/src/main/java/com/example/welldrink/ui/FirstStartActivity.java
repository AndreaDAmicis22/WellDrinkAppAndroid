package com.example.welldrink.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

import com.example.welldrink.R;

public class FirstStartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        SearchView searchView = findViewById(R.id.first_inpSearch);
        Bundle extras = getIntent().getExtras();
        Button prev = findViewById(R.id.first_btnPrevious);
        Button forward = findViewById(R.id.first_btnNext);
        if (extras != null) {
            prev.setEnabled(extras.getBoolean("prev"));
            forward.setEnabled(extras.getBoolean("forward"));
        }
        else {
            prev.setEnabled(true);
            prev.setEnabled(false);
        }
        searchView.setOnClickListener(v -> {
            searchView.setIconified(false);
        });
    }


}