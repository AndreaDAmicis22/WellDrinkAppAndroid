package com.example.welldrink.util;

import android.view.View;

import com.example.welldrink.R;
import com.google.android.material.snackbar.Snackbar;

public class ErrorSnackbars {

    public static void handleDrinkError(View view) {
        Snackbar.make(view,
                R.string.error_drink,
                Snackbar.LENGTH_SHORT).show();
    }

    public static void handlePicassoError(View view) {
        Snackbar.make(view,
                R.string.error_picasso,
                Snackbar.LENGTH_SHORT).show();
    }

    public static void handleRegistrationError(View view, String message) {
        Snackbar.make(view,
                message,
                Snackbar.LENGTH_SHORT).show();
    }

}
