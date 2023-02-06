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

    private int selected;
    private boolean darkMode;
    private final ArrayList<Button> filters;

    private DrinkViewModel drinkViewModel;
    private List<Drink> drinkList;

    public ResearchFragment() {
        selected = -1;
        filters = new ArrayList<>();
        drinkList = new ArrayList<>();
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
        darkMode = isDarkMode();
        filters.add(view.findViewById(R.id.research_filter_btnName));
        filters.add(view.findViewById(R.id.research_filter_btnIngredient));
        filters.add(view.findViewById(R.id.research_filter_btnCategory));
        filters.add(view.findViewById(R.id.research_filter_btnGlass));
        int bgDark = getResources().getColor(R.color.md_theme_dark_inverseOnSurface);
        int txtDark = getResources().getColor(R.color.md_theme_dark_primary);
        int bgLight = getResources().getColor(R.color.md_theme_light_inverseOnSurface);
        int txtLight = getResources().getColor(R.color.md_theme_light_primary);
        for (Button button : filters) {
            button.setOnClickListener(el -> {
                if (darkMode)
                    handleClick(button, bgDark, txtDark);
                else
                    handleClick(button, bgLight, txtLight);
            });
        }
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
                    bundle.putString("name", drink.getName());
                    bundle.putString("from", ResearchFragment.class.getSimpleName());
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
                makeFetchCall(query);
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

    private void handleClick(Button button, int bg, int txt) {
        Log.d(TAG, String.valueOf(selected));
        if (selected != -1 && filters.get(selected).equals(button)) {
            button.setBackgroundColor(bg);
            button.setTextColor(txt);
            selected = -1;
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_light_primary));
            button.setTextColor(getResources().getColor(R.color.md_theme_light_onPrimary));
            for (int i = 0; i < filters.size(); i++)
                if (filters.get(i).equals(button))
                    selected = i;
        }
        Log.d(TAG, String.valueOf(selected));
        resetOthers(button, bg, txt);
    }

    private void resetOthers(Button button, int bg, int txt) {
        for (int i = 0; i < filters.size(); i++)
            if (!filters.get(i).equals(button))
                resetColor(filters.get(i), bg, txt);
    }

    private void resetColor(Button button, int bg, int txt) {
        button.setBackgroundColor(bg);
        button.setTextColor(txt);
    }

    private boolean isDarkMode() {
        return (getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

    private void makeFetchCall(String query) {
        switch (selected) {
            case 1:
                drinkViewModel.getDrinksByIngredient(query);
                break;
            case 2:
                drinkViewModel.getDrinksByCategory(query);
                break;
            case 3:
                drinkViewModel.getDrinksByGlass(query);
                break;
            default:
                drinkViewModel.getDrinksByName(query);
                break;
        }
    }

}