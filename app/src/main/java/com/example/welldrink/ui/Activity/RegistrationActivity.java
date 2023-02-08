package com.example.welldrink.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.welldrink.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this).setTitle(R.string.close_app_title)
                .setMessage(R.string.close_app_desc).setPositiveButton(R.string.close_app_conf, (dialog, which) -> finish())
                .setNegativeButton(R.string.close_app_unconf, null).show();
    }

}