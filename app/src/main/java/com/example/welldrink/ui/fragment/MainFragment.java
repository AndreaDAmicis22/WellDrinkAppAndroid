package com.example.welldrink.ui.fragment;

import static com.example.welldrink.util.Constants.PLACEHOLDER_ALCOL;
import static com.example.welldrink.util.Constants.PLACEHOLDER_CATEGORY;
import static com.example.welldrink.util.Constants.PLACEHOLDER_GLASS;
import static com.example.welldrink.util.Constants.PLACEHOLDER_LINK;
import static com.example.welldrink.util.Constants.PLACEHOLDER_NAME;

import com.example.welldrink.R;
import com.example.welldrink.adapter.MainFavoriteRecyclerViewAdapter;
import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Favorite;
import com.example.welldrink.model.Result;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.DrinkViewModelFactory;
import com.example.welldrink.util.ServiceLocator;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;

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
        CardView card = view.findViewById(R.id.home_random_card);
        SearchView search = requireActivity().findViewById(R.id.home_inpSearch);
        search.onActionViewCollapsed();
        card.setOnClickListener(el -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", (String) name.getText());
            if(!name.getText().equals(PLACEHOLDER_NAME)){
                drinkViewModel.clearDrinkDetails();
                drinkViewModel.getDrinkDetail((String) name.getText());
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_main_to_detailsActivity, bundle);
                Log.d("API", "CLICK!");
            }
        });
        drinkViewModel.getDrinksRandomLiveData().observe(getViewLifecycleOwner(), res -> {
            if (res.isSuccess()) {
                Drink drink = ((Result.Success<Drink>) res).getData();
                changeTextViews(view, drink);
                handleImages(view, drink.getImageUrl());
            } else {
                setPlaceholder(view);
            }
        });
        button.setOnClickListener(el -> {
            drinkViewModel.getDrinksRandom();
            activeLoadingScreen(view);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        List<Favorite> favs = getFavsUser();
//        for (int i = 0; i < 5; i++) {
//            favs.add(new Favorite(false, "Soda"));
//        }

        List<Favorite> favoriteList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.home_rcvFavorite);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

//        this.drinkViewModel.getFavoritesLiveData().observe(
//            requireActivity(), res -> {
//                Log.d("FAVMAIN", drinkViewModel.getFavoriteMap().values().toString());
//
//            });
        this.drinkViewModel.getFavoriteIngredientsLiveData().observe(
                requireActivity(), res -> {
                    if(res.isSuccess()){
                        Log.d("FAVMAIN", "FAVORITE INGREDIENTS: ---> " + ((Result.Success<List<String>>) res).getData().toString());
                        for(String n : ((Result.Success<List<String>>) res).getData()){
                            favoriteList.add(new Favorite(false, n));
                        }
                        MainFavoriteRecyclerViewAdapter adapter = new MainFavoriteRecyclerViewAdapter(favoriteList);
                        recyclerView.setAdapter(adapter);
                    }
                }
        );

    }

    private void handleImages(View view, String link) {
        RequestCreator imgReq = Picasso.get().load(link);
        imgReq.into((ImageView) view.findViewById(R.id.home_random_img));
        imgReq.transform(new BlurTransformation(requireContext(), 25, 1)).
                into((ImageView) view.findViewById(R.id.home_random_imgBg), new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Picasso", "Loaded");
                        removeLoadingScreen(view);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso", "Error loading");
                    }
                });
    }

    private void removeLoadingScreen(View view) {
        CircularProgressIndicator loading = view.findViewById(R.id.home_progress);
        loading.setVisibility(View.INVISIBLE);
        ImageView img = view.findViewById(R.id.home_random_img);
        ImageView imgBg = view.findViewById(R.id.home_random_imgBg);
        Button button = view.findViewById(R.id.home_random_btn);
        img.setVisibility(View.VISIBLE);
        imgBg.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
    }

    private void activeLoadingScreen(View view) {
        CircularProgressIndicator loading = view.findViewById(R.id.home_progress);
        loading.setVisibility(View.VISIBLE);
        ImageView img = view.findViewById(R.id.home_random_img);
        ImageView imgBg = view.findViewById(R.id.home_random_imgBg);
        Button button = view.findViewById(R.id.home_random_btn);
        img.setVisibility(View.INVISIBLE);
        imgBg.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
    }

    private void changeTextViews(View view, Drink drink) {
        ((TextView) view.findViewById(R.id.home_random_txtTitle)).setText(drink.getName());
        ((TextView) view.findViewById(R.id.home_random_txtCategory)).setText(drink.getCategory());
        ((TextView) view.findViewById(R.id.home_random_txtGlass)).setText(drink.getGlass());
        ((TextView) view.findViewById(R.id.home_random_txtAlcol)).setText(drink.getAlcolType());
    }

    private void setPlaceholder(View view) {
        ((TextView) view.findViewById(R.id.home_random_txtTitle)).setText(PLACEHOLDER_NAME);
        ((TextView) view.findViewById(R.id.home_random_txtCategory)).setText(PLACEHOLDER_CATEGORY);
        ((TextView) view.findViewById(R.id.home_random_txtGlass)).setText(PLACEHOLDER_GLASS);
        ((TextView) view.findViewById(R.id.home_random_txtAlcol)).setText(PLACEHOLDER_ALCOL);
        Picasso.get().load(PLACEHOLDER_LINK).into((ImageView) view.findViewById(R.id.home_random_img));
    }

    private ArrayList<Favorite> getFavsUser() {
        return new ArrayList<>();
    }
}