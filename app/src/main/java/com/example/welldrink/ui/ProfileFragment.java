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

public class ProfileFragment extends Fragment {
    private Boolean isFavoriteDrinkButton;
    private Boolean isFavoriteIngredientButton;
    private Boolean isTastedListButton;
    private Boolean isTop20Button;

    private static final String TAG = ProfileFragment.class.getSimpleName();

    public ProfileFragment() {
        isFavoriteDrinkButton = false;
        isFavoriteIngredientButton = false;
        isTastedListButton = false;
        isTop20Button = false;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView profileRecycleView = view.findViewById(R.id.profile_rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        profileRecycleView.setLayoutManager(linearLayoutManager);
        Button favoriteDrinkButton = view.findViewById(R.id.profile_grd_btnFavoriteDrink);
        Button favoriteIngredientButton = view.findViewById(R.id.profile_grd_btnFavoriteIngredient);
        Button tastedListButton = view.findViewById(R.id.profile_grd_btnTastedList);
        Button top20Button = view.findViewById(R.id.profile_btnTop20);

        List<Drink> array = new ArrayList<>();
        favoriteDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                array.clear();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(1),null, null, null));
                }
                if (isFavoriteDrinkButton == true) array.clear();
                isFavoriteDrinkButton = changeColor(favoriteDrinkButton, isFavoriteDrinkButton);
                resetColor(tastedListButton, top20Button, favoriteIngredientButton);
                isTastedListButton = isTop20Button = isFavoriteIngredientButton = false;
                ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
                profileRecycleView.setAdapter(adapter);
            }
        });

        favoriteIngredientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                array.clear();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(2),null, null, null));
                }
                if (isFavoriteIngredientButton == true) array.clear();
                isFavoriteIngredientButton = changeColor(favoriteIngredientButton, isFavoriteIngredientButton);
                resetColor(top20Button, tastedListButton, favoriteDrinkButton);
                isTop20Button = isTastedListButton = isFavoriteDrinkButton = false;
                ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
                profileRecycleView.setAdapter(adapter);
            }
        });

        tastedListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                array.clear();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(3),null, null, null));
                }
                if (isTastedListButton == true) array.clear();
                isTastedListButton = changeColor(tastedListButton, isTastedListButton);
                resetColor(top20Button, favoriteIngredientButton, favoriteDrinkButton);
                isTop20Button = isFavoriteIngredientButton = isFavoriteDrinkButton = false;
                ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
                profileRecycleView.setAdapter(adapter);
            }
        });

        top20Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                array.clear();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(4),null, null, null));
                }
                if (isTop20Button == true) array.clear();
                isTop20Button = changeColor(top20Button, isTop20Button);
                resetColor(tastedListButton, favoriteIngredientButton, favoriteDrinkButton);
                isTastedListButton = isFavoriteIngredientButton = isFavoriteDrinkButton = false;
                ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
                profileRecycleView.setAdapter(adapter);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Boolean changeColor(Button button, Boolean bool){
        if(bool == false){
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_onSurface));
            button.setTextColor(getResources().getColor(R.color.md_theme_dark_surface));
            return true;
        }
        else {
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
            button.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
        }
        return false;
    }

    private void resetColor(Button button1, Button button2, Button button3){
        button1.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
        button2.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
        button3.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
        button1.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
        button2.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
        button3.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
    }
}