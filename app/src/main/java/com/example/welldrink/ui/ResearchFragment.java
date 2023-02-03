package com.example.welldrink.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private static final String TAG = MainFragment.class.getSimpleName();

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
        ingredient.setOnClickListener(view1 -> {
            onIngredient = changeColor(onIngredient, ingredient);
            if (onIngredient) {
                onName = resetColor(name);
                onTaste = resetColor(taste);
                onGlass = resetColor(glass);
            }
        });
        name.setOnClickListener(view1 -> {
            onName = changeColor(onName, name);
            if (onName) {
                onIngredient = resetColor(ingredient);
                onTaste = resetColor(taste);
                onGlass = resetColor(glass);
            }
        });
        taste.setOnClickListener(view1 -> {
            onTaste = changeColor(onTaste, taste);
            if (onTaste) {
                onIngredient = resetColor(ingredient);
                onName = resetColor(name);
                onGlass = resetColor(glass);
            }
        });
        glass.setOnClickListener(view1 -> {
            onGlass = changeColor(onGlass, glass);
            if (onGlass) {
                onIngredient = resetColor(ingredient);
                onName = resetColor(name);
                onTaste = resetColor(taste);
            }
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

    private boolean resetColor(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
        button.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
        return false;
    }
}