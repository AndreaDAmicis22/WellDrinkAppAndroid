package com.example.welldrink.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.welldrink.R;
import com.example.welldrink.ui.fragment.FirstStartFragment;

public class FirstStartActivity extends AppCompatActivity {

    private boolean end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        SearchView searchView = findViewById(R.id.first_inpSearch);
        Bundle extras = getIntent().getExtras();
        Button prev = findViewById(R.id.first_btnPrevious);
        Button forward = findViewById(R.id.first_btnNext);
        TextView choose = findViewById(R.id.first_txtSubtitle);
        end = false;
        if (extras != null) {
            prev.setEnabled(extras.getBoolean("prev"));
            forward.setEnabled(extras.getBoolean("forward"));
        } else {
            prev.setEnabled(true);
            prev.setEnabled(false);
        }
        searchView.setOnClickListener(v -> searchView.setIconified(false));
        prev.setOnClickListener(el -> {
            handleClick(true, prev);
            choose.setText(R.string.first_txtSubtitleDrink);
            end = false;
        });
        forward.setOnClickListener(el -> {
            if (!end) {
                handleClick(false, prev);
                choose.setText(R.string.first_txtSubtitleIngredient);
                end = true;
            } else
                switchActivities();
        });
    }

    private void handleClick(boolean first, Button prev) {
        FirstStartFragment fragment = new FirstStartFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putBoolean("first", first);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.first_fragmentCont, fragment);
        fragmentTransaction.commit();
        prev.setEnabled(!first);
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }


}