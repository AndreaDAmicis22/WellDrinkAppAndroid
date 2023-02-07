package com.example.welldrink.ui.fragment;

import static com.example.welldrink.util.ButtonHandler.getBgDark;
import static com.example.welldrink.util.ButtonHandler.getBgLight;
import static com.example.welldrink.util.ButtonHandler.getTxtDark;
import static com.example.welldrink.util.ButtonHandler.getTxtLight;
import static com.example.welldrink.util.ButtonHandler.handleClick;
import static com.example.welldrink.util.ButtonHandler.isDarkMode;

import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.List;

public class ResearchFragment extends Fragment {

    private static final String TAG = ResearchFragment.class.getSimpleName();

    private int selected;
    private boolean darkMode;
    private final List<Button> filters;

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
        drinkViewModel.clearDrinkMutableLiveData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_research, container, false);
        darkMode = isDarkMode(requireContext());
        addButtonsToList(filters, view);
        for (Button button : filters) {
            Resources resources = getResources();
            button.setOnClickListener(el -> {
                if (darkMode)
                    selected = handleClick(resources, selected, filters, button, getBgDark(resources), getTxtDark(resources));
                else
                    selected = handleClick(getResources(), selected, filters, button, getBgLight(resources), getTxtLight(resources));
            });
        }
        drinkViewModel.getDrinkMutableLiveData().observe(getViewLifecycleOwner(), res -> {
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
                    Navigation.findNavController(requireView()).navigate(R.id.action_fragment_research_to_detailsActivity, bundle);
                }, drinkViewModel);
                researchRecycleView.setAdapter(adapter);
            }
        });
        SearchView searchView = requireActivity().findViewById(R.id.home_inpSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                makeFetchCall(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(!query.isEmpty())
                    makeFetchCall(query);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addButtonsToList(List<Button> arrayList, View view) {
        arrayList.add(view.findViewById(R.id.research_filter_btnName));
        arrayList.add(view.findViewById(R.id.research_filter_btnIngredient));
        arrayList.add(view.findViewById(R.id.research_filter_btnCategory));
        arrayList.add(view.findViewById(R.id.research_filter_btnGlass));
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