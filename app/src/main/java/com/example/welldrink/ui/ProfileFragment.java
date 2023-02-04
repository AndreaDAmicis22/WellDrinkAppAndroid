package com.example.welldrink.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.google.android.material.snackbar.Snackbar;

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
        int backgroundColorDark = getResources().getColor(R.color.md_theme_dark_inverseOnSurface);
        int textColorDark = getResources().getColor(R.color.md_theme_dark_primary);
        int backgroundColorLight = getResources().getColor(R.color.md_theme_light_inverseOnSurface);
        int textColorLight = getResources().getColor(R.color.md_theme_light_primary);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView profileRecycleView = view.findViewById(R.id.profile_rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        profileRecycleView.setLayoutManager(linearLayoutManager);
        Button favoriteDrinkButton = view.findViewById(R.id.profile_grd_btnFavoriteDrink);
        Button favoriteIngredientButton = view.findViewById(R.id.profile_grd_btnFavoriteIngredient);
        Button tastedListButton = view.findViewById(R.id.profile_grd_btnTastedList);
        Button top20Button = view.findViewById(R.id.profile_btnTop20);

        List<Drink> array = new ArrayList<>();
        boolean darkMode = isDarkMode();
        favoriteDrinkButton.setOnClickListener(view14 -> {
            array.clear();
            for (int i = 0; i < 1000; i++){
                array.add(new Drink(i, Integer.toString(1),null, null, null));
            }
            if (isFavoriteDrinkButton)
                array.clear();
            if(darkMode) {
                isFavoriteDrinkButton = changeColor(favoriteDrinkButton, isFavoriteDrinkButton, backgroundColorDark , textColorDark);
                resetColor(tastedListButton, top20Button, favoriteIngredientButton, backgroundColorDark, textColorDark);
            } else {
                isFavoriteDrinkButton = changeColor(favoriteDrinkButton, isFavoriteDrinkButton, backgroundColorLight, textColorLight);
                resetColor(tastedListButton, top20Button, favoriteIngredientButton,  backgroundColorLight, textColorLight);
            }
            isTastedListButton = isTop20Button = isFavoriteIngredientButton = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink ->
                    Snackbar.make(view14, drink.getName(), Snackbar.LENGTH_SHORT).show());
            profileRecycleView.setAdapter(adapter);
        });

        favoriteIngredientButton.setOnClickListener(view13 -> {
            array.clear();
            for (int i = 0; i < 1000; i++){
                array.add(new Drink(i, Integer.toString(2),null, null, null));
            }
            if (isFavoriteIngredientButton)
                array.clear();
            if(darkMode){
                isFavoriteIngredientButton = changeColor(favoriteIngredientButton, isFavoriteIngredientButton, backgroundColorDark , textColorDark);
                resetColor(top20Button, tastedListButton, favoriteDrinkButton, backgroundColorDark , textColorDark);
            }else{
                isFavoriteIngredientButton = changeColor(favoriteIngredientButton, isFavoriteIngredientButton, backgroundColorLight, textColorLight);
                resetColor(top20Button, tastedListButton, favoriteDrinkButton, backgroundColorLight, textColorLight);
            }
            isTop20Button = isTastedListButton = isFavoriteDrinkButton = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink ->
                    Snackbar.make(view13, drink.getName(), Snackbar.LENGTH_SHORT).show());
            profileRecycleView.setAdapter(adapter);
        });

        tastedListButton.setOnClickListener(view12 -> {
            array.clear();
            for (int i = 0; i < 1000; i++){
                array.add(new Drink(i, Integer.toString(3),null, null, null));
            }
            if (isTastedListButton)
                array.clear();
            if(darkMode){
                isTastedListButton = changeColor(tastedListButton, isTastedListButton, backgroundColorDark , textColorDark);
                resetColor(top20Button, favoriteIngredientButton, favoriteDrinkButton, backgroundColorDark , textColorDark);
            }else{
                isTastedListButton = changeColor(tastedListButton, isTastedListButton, backgroundColorLight, textColorLight);
                resetColor(top20Button, favoriteIngredientButton, favoriteDrinkButton, backgroundColorLight, textColorLight);
            }
            isTop20Button = isFavoriteIngredientButton = isFavoriteDrinkButton = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink ->
                    Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details));
            profileRecycleView.setAdapter(adapter);
        });

        top20Button.setOnClickListener(view1 -> {
            array.clear();
            for (int i = 0; i < 1000; i++){
                array.add(new Drink(i, Integer.toString(4),null, null, null));
            }
            if (isTop20Button)
                array.clear();
            if(darkMode){
                isTop20Button = changeColor(top20Button, isTop20Button, backgroundColorDark , textColorDark);
                resetColor(tastedListButton, favoriteIngredientButton, favoriteDrinkButton, backgroundColorDark , textColorDark);
            }else{
                isTop20Button = changeColor(top20Button, isTop20Button, backgroundColorLight, textColorLight);
                resetColor(tastedListButton, favoriteIngredientButton, favoriteDrinkButton, backgroundColorLight, textColorLight);
            }
            isTastedListButton = isFavoriteIngredientButton = isFavoriteDrinkButton = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink ->
                    Snackbar.make(view1, drink.getName(), Snackbar.LENGTH_SHORT).show());
            profileRecycleView.setAdapter(adapter);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private boolean changeColor(Button button, boolean bool, int backgroundColor, int textColor){
        if(!bool){
            button.setBackgroundColor(getResources().getColor(R.color.md_theme_light_primary));
            button.setTextColor(getResources().getColor(R.color.md_theme_light_onPrimary));
            return true;
        }
        else {
            button.setBackgroundColor(backgroundColor);
            button.setTextColor(textColor);
        }
        return false;
    }

    private void resetColor(Button button1, Button button2, Button button3, int backgroundColor, int textColor){
        button1.setBackgroundColor(backgroundColor);
        button2.setBackgroundColor(backgroundColor);
        button3.setBackgroundColor(backgroundColor);
        button1.setTextColor(textColor);
        button2.setTextColor(textColor);
        button3.setTextColor(textColor);
    }

    private boolean isDarkMode() {
        return (getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

}