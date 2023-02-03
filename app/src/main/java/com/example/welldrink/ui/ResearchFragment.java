package com.example.welldrink.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.welldrink.R;
import com.example.welldrink.adapter.ProfileRecyclerViewAdapter;
import com.example.welldrink.model.Drink;

import java.util.ArrayList;
import java.util.List;

public class ResearchFragment extends Fragment {

    private static final String TAG = ResearchFragment.class.getSimpleName();

    private boolean onIngredient;
    private boolean onName;
    private boolean onTaste;
    private boolean onGlass;

    public ResearchFragment() {
        onIngredient = false;
        onName = false;
        onTaste = false;
        onGlass = false;
    }

    public static ResearchFragment newInstance(String param1, String param2) {
        return new ResearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_research, container, false);
        Button ingredient = view.findViewById(R.id.research_filter_btnIngredient);
        Button name = view.findViewById(R.id.research_filter_btnName);
        Button taste = view.findViewById(R.id.research_filter_btnTaste);
        Button glass = view.findViewById(R.id.research_filter_btnGlass);
        boolean darkMode = isDarkMode();
        ingredient.setOnClickListener(view1 -> {
            onIngredient = changeColor(onIngredient, ingredient);
            if (onIngredient) {
                resetOther(darkMode, name, taste, glass);
                onName = false;
                onTaste = false;
                onGlass = false;
            }
            onIngredient = !onIngredient;
        });
        name.setOnClickListener(view1 -> {
            onName = changeColor(onName, name);
            if (onName) {
                resetOther(darkMode, ingredient, taste, glass);
                onIngredient = false;
                onTaste = false;
                onGlass = false;
            }
            onName = !onName;
        });
        taste.setOnClickListener(view1 -> {
            onTaste = changeColor(onTaste, taste);
            if (onTaste) {
                resetOther(darkMode, ingredient, name, glass);
                onIngredient = false;
                onName = false;
                onGlass = false;
            }
            onTaste = !onTaste;
        });
        glass.setOnClickListener(view1 -> {
            onGlass = changeColor(onGlass, glass);
            if (onGlass) {
                resetOther(darkMode, ingredient, name, taste);
                onIngredient = false;
                onName = false;
                onTaste = false;
            }
            onGlass = !onGlass;
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Drink> array = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            array.add(new Drink(i, Integer.toString(5),null, null, null));
        }
        RecyclerView researchRecycleView = view.findViewById(R.id.research_rscv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        researchRecycleView.setLayoutManager(linearLayoutManager);
        ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
        researchRecycleView.setAdapter(adapter);
    }

    private boolean changeColor(boolean previous, Button button) {
        if (previous) {
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
            button.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
        }
        else {
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_onSurface));
            button.setTextColor(getResources().getColor(R.color.md_theme_dark_surface));
        }
        return !previous;
    }

    private void resetOther(boolean darkMode, Button b1, Button b2, Button b3) {
        int bgDark = getResources().getColor(R.color.md_theme_dark_inverseOnSurface);
        int txtDark = getResources().getColor(R.color.md_theme_dark_primary);
        int bgLight = getResources().getColor(R.color.md_theme_light_inverseOnSurface);
        int txtLight = getResources().getColor(R.color.md_theme_light_primary);
        if (darkMode) {
            resetColor(b1, bgDark, txtDark);
            resetColor(b2, bgDark, txtDark);
            resetColor(b3, bgDark, txtDark);
        }
        else {
            resetColor(b1, bgLight, txtLight);
            resetColor(b2, bgLight, txtLight);
            resetColor(b3, bgLight, txtLight);
        }
    }

    private void resetColor(Button button, int bg, int txt) {
        button.setBackgroundColor(bg);
        button.setTextColor(txt);
    }

    private boolean isDarkMode() {
        return (getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }
}