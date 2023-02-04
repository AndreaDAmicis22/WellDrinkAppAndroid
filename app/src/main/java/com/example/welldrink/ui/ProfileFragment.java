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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.ProfileRecyclerViewAdapter;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.User;
import com.example.welldrink.ui.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private boolean isTopDrink;
    private boolean isTopIngredient;
    private boolean isFavoriteDrink;
    private boolean isFavoriteIngredient;
    private final UserViewModel userViewModel;
    private final User user;

    private static final String TAG = ProfileFragment.class.getSimpleName();

    public ProfileFragment() {
        isTopDrink = false;
        isTopIngredient = false;
        isFavoriteDrink = false;
        isFavoriteIngredient = false;
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        user = userViewModel.getLoggedUser();
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
        Button topDrink = view.findViewById(R.id.profile_grd_btnTopDrink);
        Button topIngredient = view.findViewById(R.id.profile_grd_btnTopIngredient);
        Button favoriteDrink = view.findViewById(R.id.profile_grd_btnFavoriteDrink);
        Button favoriteIngredient = view.findViewById(R.id.profile_btnFavoriteIngredient);
        TextView profileName = view.findViewById(R.id.profile_txtNameProfile);
        profileName.setText(user.getName());
        List<Drink> array = new ArrayList<>();

        topDrink.setOnClickListener(el -> {
            isTopDrink = manageRecycleView(array, isTopDrink, topDrink, topIngredient, favoriteDrink, favoriteIngredient, 1);
            isTopIngredient = isFavoriteDrink = isFavoriteIngredient = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details);
                isTopDrink = false;
            });
            profileRecycleView.setAdapter(adapter);
        });

        topIngredient.setOnClickListener(el -> {
            isTopIngredient = manageRecycleView(array,  isTopIngredient, topIngredient, topDrink, favoriteDrink, favoriteIngredient, 2);
            isTopDrink = isFavoriteDrink = isFavoriteIngredient = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details);
                isTopIngredient = false;
            });
            profileRecycleView.setAdapter(adapter);
        });

        favoriteDrink.setOnClickListener(el -> {
            isFavoriteDrink = manageRecycleView(array,  isFavoriteDrink, favoriteDrink, topDrink, topIngredient, favoriteIngredient, 3);
            isTopDrink = isTopIngredient = isFavoriteIngredient = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details);
                isFavoriteDrink = false;
            });
            profileRecycleView.setAdapter(adapter);
        });

        favoriteIngredient.setOnClickListener(el -> {
            isFavoriteIngredient = manageRecycleView(array,  isFavoriteIngredient, favoriteIngredient, topDrink, topIngredient, favoriteDrink, 4);
            isTopDrink = isTopIngredient = isFavoriteDrink = false;
            ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array, drink -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details);
                isFavoriteIngredient = false;
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