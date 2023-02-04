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
    private boolean isFavoriteDrinkButton;
    private boolean isFavoriteIngredientButton;
    private boolean isTastedListButton;
    private boolean isTop20Button;

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

        favoriteDrinkButton.setOnClickListener(view14 -> {
            isFavoriteDrinkButton = manageRecycleView(array, isFavoriteDrinkButton, favoriteDrinkButton, tastedListButton, top20Button, favoriteIngredientButton, 1);
            isTastedListButton = isTop20Button = isFavoriteIngredientButton = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink ->
                    Snackbar.make(view14, drink.getName(), Snackbar.LENGTH_SHORT).show());
            profileRecycleView.setAdapter(adapter);
        });

        favoriteIngredientButton.setOnClickListener(view13 -> {
            isFavoriteIngredientButton = manageRecycleView(array,  isFavoriteIngredientButton, favoriteIngredientButton, tastedListButton, top20Button, favoriteDrinkButton, 2);
            isTastedListButton = isTop20Button = isFavoriteDrinkButton = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink -> {
                Snackbar.make(view13, drink.getName(), Snackbar.LENGTH_SHORT).show();
            });
            profileRecycleView.setAdapter(adapter);
        });

        tastedListButton.setOnClickListener(view12 -> {
            isTastedListButton = manageRecycleView(array,  isTastedListButton, tastedListButton, favoriteIngredientButton, top20Button, favoriteDrinkButton, 3);
            isFavoriteIngredientButton = isTop20Button = isFavoriteDrinkButton = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink -> {
                    Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details);
                    isTastedListButton = false;
            });
            profileRecycleView.setAdapter(adapter);
        });

        top20Button.setOnClickListener(view1 -> {
            isTop20Button = manageRecycleView(array,  isTop20Button, top20Button, favoriteIngredientButton, tastedListButton, favoriteDrinkButton, 4);
            isFavoriteIngredientButton = isTastedListButton = isFavoriteDrinkButton = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink -> {
                //Snackbar.make(view13, drink.getName(), Snackbar.LENGTH_SHORT).show();
            });
            profileRecycleView.setAdapter(adapter);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean manageRecycleView(List<Drink> array, boolean val, Button button1, Button button2, Button button3, Button button4, int j){
        int backgroundColorDark = getResources().getColor(R.color.md_theme_dark_inverseOnSurface);
        int textColorDark = getResources().getColor(R.color.md_theme_dark_primary);
        int backgroundColorLight = getResources().getColor(R.color.md_theme_light_inverseOnSurface);
        int textColorLight = getResources().getColor(R.color.md_theme_light_primary);
        boolean darkMode = isDarkMode();
        array.clear();
        for (int i = 0; i < 1000; i++){
            array.add(new Drink(i, Integer.toString(j),null, null, null));
        }
        if (val)
            array.clear();
        if(darkMode){
            val = changeColor(button1, val, backgroundColorDark , textColorDark);
            resetColor(button2, button3, button4, backgroundColorDark , textColorDark);
        }else{
            val = changeColor(button1, val, backgroundColorLight, textColorLight);
            resetColor(button2, button3, button4, backgroundColorLight, textColorLight);
        }
        return val;
    }
    private boolean changeColor(Button button, boolean bool, int backgroundColor, int textColor){
        if(bool == false){
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