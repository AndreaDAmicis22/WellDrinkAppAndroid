package com.example.welldrink.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.StartRecyclerViewAdapter;
import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Result;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.DrinkViewModelFactory;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.example.welldrink.ui.viewModel.UserViewModelFactory;
import com.example.welldrink.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;


public class FirstStartFragment extends Fragment {

    private DrinkViewModel drinkViewModel;
    private Bundle args;

    public FirstStartFragment() {

    }

    public static FirstStartFragment newInstance() {
        return new FirstStartFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IDrinkRepository drinkRepository = ServiceLocator.getInstance().getDrinkRepository();
        this.drinkViewModel = new ViewModelProvider(
                requireActivity(),
                new DrinkViewModelFactory(drinkRepository)).get(DrinkViewModel.class);
        this.args = getArguments();
        this.drinkViewModel.clearDrinkMutableLiveData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_start, container, false);

        RecyclerView researchRecycleView = view.findViewById(R.id.first_start_rscv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        researchRecycleView.setLayoutManager(linearLayoutManager);
        ((SearchView) requireActivity().findViewById(R.id.first_inpSearch)).onActionViewCollapsed();
        drinkViewModel.getDrinkMutableLiveData().observe(
                requireActivity(), res -> {
                    if(res.isSuccess()){
                        List<Drink> drinkList = ((Result.Success<List<Drink>>) res).getData();
                        StartRecyclerViewAdapter adapter = new StartRecyclerViewAdapter(drinkList);
                        researchRecycleView.setAdapter(adapter);
                    }else{
                        //snackbar
                    }
                }
        );

        SearchView searchView = requireActivity().findViewById(R.id.first_inpSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(!query.isEmpty())
                    fetchData(query);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        List<Drink> array = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            if (args != null && !args.getBoolean("first"))
                array.add(new Drink(i, Integer.toString(5),null, null, null, null, null, null));
            else
                array.add(new Drink(i, Integer.toString(0),null, null, null, null, null, null));
        }
    }

    private void fetchData(String query){
        if(args != null && !args.getBoolean("first")){
            this.drinkViewModel.getIngredientsByName(query);
            Log.d("FIRST", "IF");
        }
        else{
            this.drinkViewModel.getDrinksByName(query);
            Log.d("FIRST", "ELSE");
        }
    }

}