package com.example.welldrink.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.DrinkSmallInfoRecyclerViewAdapter;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Result;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ResearchFragment extends Fragment {

    private static final String TAG = ResearchFragment.class.getSimpleName();

    private boolean onIngredient;
    private boolean onName;
    private boolean onTaste;
    private boolean onGlass;

    private DrinkViewModel drinkViewModel;
    private List<Drink> drinkList;

    public ResearchFragment() {
        onIngredient = false;
        onName = false;
        onTaste = false;
        onGlass = false;
        drinkList = new ArrayList<Drink>();
    }

    public static ResearchFragment newInstance() {
        return new ResearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drinkViewModel = new ViewModelProvider(requireActivity()).get(DrinkViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_research, container, false);
        Button ingredient = view.findViewById(R.id.research_filter_btnIngredient);
        Button name = view.findViewById(R.id.research_filter_btnName);
        Button taste = view.findViewById(R.id.research_filter_btnCategory);
        Button glass = view.findViewById(R.id.research_filter_btnGlass);
        boolean darkMode = isDarkMode();
        int bgDark = getResources().getColor(R.color.md_theme_dark_inverseOnSurface);
        int txtDark = getResources().getColor(R.color.md_theme_dark_primary);
        int bgLight = getResources().getColor(R.color.md_theme_light_inverseOnSurface);
        int txtLight = getResources().getColor(R.color.md_theme_light_primary);
        ingredient.setOnClickListener(el -> {
            if (darkMode) {
                onIngredient = changeColor(onIngredient, ingredient, bgDark, txtDark);
                resetOthers(name, taste, glass, bgDark, txtDark);
            }
            else {
                onIngredient = changeColor(onIngredient, ingredient, bgLight, txtLight);
                resetOthers(name, taste, glass, bgLight, txtLight);
            }
            onName = false;
            onTaste = false;
            onGlass = false;
        });
        name.setOnClickListener(el -> {
            if (darkMode) {
                onName = changeColor(onName, name, bgDark, txtDark);
                resetOthers(ingredient, taste, glass, bgDark, txtDark);
            }
            else {
                onName = changeColor(onName, name, bgLight, txtLight);
                resetOthers(ingredient, taste, glass, bgLight, txtLight);
            }
            onIngredient = false;
            onTaste = false;
            onGlass = false;
        });
        taste.setOnClickListener(el -> {
            if (darkMode) {
                onTaste = changeColor(onTaste, taste, bgDark, txtDark);
                resetOthers(ingredient, name, glass, bgDark, txtDark);
            }
            else {
                onTaste = changeColor(onTaste, taste, bgLight, txtLight);
                resetOthers(ingredient, name, glass, bgLight, txtLight);
            }
            onIngredient = false;
            onName = false;
            onGlass = false;
        });
        glass.setOnClickListener(el -> {
            if (darkMode) {
                onGlass = changeColor(onGlass, glass, bgDark, txtDark);
                resetOthers(ingredient, taste, name, bgDark, txtDark);
            }
            else {
                onGlass = changeColor(onGlass, glass, bgLight, txtLight);
                resetOthers(ingredient, taste, name, bgLight, txtLight);
            }
            onIngredient = false;
            onTaste = false;
            onName = false;
        });

        drinkViewModel.getDrinksByNameLiveData("---").observe(getViewLifecycleOwner(), res -> {
            if(res.isSuccess()){
                Log.d("RES", "Observer");
                List<Drink> drinks = ((Result.Success<List<Drink>>) res).getData();
                Log.d("RES", drinks.toString());
                this.drinkList = drinks;
                RecyclerView researchRecycleView = view.findViewById(R.id.research_rscv);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
                researchRecycleView.setLayoutManager(linearLayoutManager);
                DrinkSmallInfoRecyclerViewAdapter adapter = new DrinkSmallInfoRecyclerViewAdapter(drinkList, drink -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", (String) drink.getName());
                    Navigation.findNavController(requireView()).navigate(R.id.action_fragment_research_to_fragment_details, bundle);
                });
                researchRecycleView.setAdapter(adapter);
            }
        });
        SearchView searchView = getActivity().findViewById(R.id.home_inpSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("RES", query);
                drinkViewModel.getDrinksByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean changeColor(boolean previous, Button button, int bg, int txt) {
        if (previous) {
            button.setBackgroundColor(bg);
            button.setTextColor(txt);
        }
        else {
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_light_primary));
            button.setTextColor(getResources().getColor(R.color.md_theme_light_onPrimary));
        }
        return !previous;
    }

    private void resetOthers(Button b1, Button b2, Button b3, int bg, int txt) {
        resetColor(b1, bg, txt);
        resetColor(b2, bg, txt);
        resetColor(b3, bg, txt);
    }

    private void resetColor(Button button, int bg, int txt) {
        button.setBackgroundColor(bg);
        button.setTextColor(txt);
    }

    private boolean isDarkMode() {
        return (getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }
}