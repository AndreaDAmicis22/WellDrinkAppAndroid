package com.example.welldrink.ui;

import com.example.welldrink.R;
import com.example.welldrink.adapter.MainFavoriteRecyclerViewAdapter;
import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Favorite;
import com.example.welldrink.model.Result;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.DrinkViewModelFactory;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.example.welldrink.ui.viewModel.UserViewModelFactory;
import com.example.welldrink.util.ServiceLocator;
import com.squareup.picasso.Picasso;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private DrinkViewModel drinkViewModel;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IDrinkRepository drinkRepository = ServiceLocator.getInstance().getDrinkRepository();
        drinkViewModel = new ViewModelProvider(
                requireActivity(),
                new DrinkViewModelFactory(drinkRepository)).get(DrinkViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = view.findViewById(R.id.home_random_btn);
        TextView name = view.findViewById(R.id.home_random_txtTitle);
        TextView category = view.findViewById(R.id.home_random_txtCategory);
        ImageView image = view.findViewById(R.id.home_random_img);
        CardView card = view.findViewById(R.id.home_random_card);
        card.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", (String) name.getText());
            Navigation.findNavController(requireView()).navigate(R.id.action_fragment_main_to_fragment_details, bundle);
        });
        drinkViewModel.getDrinksRandomLiveData().observe(getViewLifecycleOwner(), res -> {
            if(res.isSuccess()){
                Drink drink = ((Result.Success<Drink>) res).getData();
                name.setText(drink.getName());
                category.setText(drink.getCategory());
                Picasso.get().load(drink.getImageUrl()).into(image);
            }
        });
        button.setOnClickListener(view1 -> {
            drinkViewModel.getDrinksRandom();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Favorite> favs = getFavsUser();
        for (int i = 0; i < 5; i++) {
            favs.add(new Favorite(false, "Soda"));
        }
        RecyclerView recyclerView = view.findViewById(R.id.home_rcvFavorite);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MainFavoriteRecyclerViewAdapter adapter = new MainFavoriteRecyclerViewAdapter(favs);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Favorite> getFavsUser() {
        return new ArrayList<>();
    }
}