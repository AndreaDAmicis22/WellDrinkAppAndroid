package com.example.welldrink.ui;

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
        int mode = view.getContext().getResources().getConfiguration().uiMode; // 44 21
        favoriteDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                array.clear();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(1),null, null, null));
                }
                if (isFavoriteDrinkButton == true) array.clear();
                if(mode == 44) {
                    isFavoriteDrinkButton = changeColorDark(favoriteDrinkButton, isFavoriteDrinkButton);
                    resetColorDark(tastedListButton, top20Button, favoriteIngredientButton);
                } else {
                    isFavoriteDrinkButton = changeColorLight(favoriteDrinkButton, isFavoriteDrinkButton);
                    resetColorLight(tastedListButton, top20Button, favoriteIngredientButton);
                }
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
                if(mode == 44){
                    isFavoriteIngredientButton = changeColorDark(favoriteIngredientButton, isFavoriteIngredientButton);
                    resetColorDark(top20Button, tastedListButton, favoriteDrinkButton);
                }else{
                    isFavoriteIngredientButton = changeColorLight(favoriteIngredientButton, isFavoriteIngredientButton);
                    resetColorLight(top20Button, tastedListButton, favoriteDrinkButton);
                }
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
                if(mode == 44){
                    isTastedListButton = changeColorDark(tastedListButton, isTastedListButton);
                    resetColorDark(top20Button, favoriteIngredientButton, favoriteDrinkButton);
                }else{
                    isTastedListButton = changeColorLight(tastedListButton, isTastedListButton);
                    resetColorLight(top20Button, favoriteIngredientButton, favoriteDrinkButton);
                }
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
                if(mode == 44){
                    isTop20Button = changeColorDark(top20Button, isTop20Button);
                    resetColorDark(tastedListButton, favoriteIngredientButton, favoriteDrinkButton);
                }else{
                    isTop20Button = changeColorLight(top20Button, isTop20Button);
                    resetColorLight(tastedListButton, favoriteIngredientButton, favoriteDrinkButton);
                }
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

    private Boolean changeColorDark(Button button, Boolean bool){
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

    private Boolean changeColorLight(Button button, Boolean bool){
        if(bool == false){
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_light_onSurface));
            button.setTextColor(getResources().getColor(R.color.md_theme_light_surface));
            return true;
        }
        else {
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_light_inverseOnSurface));
            button.setTextColor(getResources().getColor(R.color.md_theme_light_primary));
        }
        return false;
    }

    private void resetColorDark(Button button1, Button button2, Button button3){
        button1.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
        button2.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
        button3.setBackgroundColor(getResources().getColor(R.color.md_theme_dark_inverseOnSurface));
        button1.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
        button2.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
        button3.setTextColor(getResources().getColor(R.color.md_theme_dark_primary));
    }

    private void resetColorLight(Button button1, Button button2, Button button3){
        button1.setBackgroundColor(getResources().getColor(R.color.md_theme_light_inverseOnSurface));
        button2.setBackgroundColor(getResources().getColor(R.color.md_theme_light_inverseOnSurface));
        button3.setBackgroundColor(getResources().getColor(R.color.md_theme_light_inverseOnSurface));
        button1.setTextColor(getResources().getColor(R.color.md_theme_light_primary));
        button2.setTextColor(getResources().getColor(R.color.md_theme_light_primary));
        button3.setTextColor(getResources().getColor(R.color.md_theme_light_primary));
    }
}