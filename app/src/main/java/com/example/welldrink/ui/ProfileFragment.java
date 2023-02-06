package com.example.welldrink.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.DrinkSmallInfoRecyclerViewAdapter;
import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.User;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.example.welldrink.ui.viewModel.UserViewModelFactory;
import com.example.welldrink.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private boolean isTopDrink;
    private boolean isTopIngredient;
    private boolean isFavoriteDrink;
    private boolean isFavoriteIngredient;
    private UserViewModel userViewModel;
    private User user;

    private static final String TAG = ProfileFragment.class.getSimpleName();

    public ProfileFragment() {
        isTopDrink = false;
        isTopIngredient = false;
        isFavoriteDrink = false;
        isFavoriteIngredient = false;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        user = userViewModel.getLoggedUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView profileRecycleView = view.findViewById(R.id.profile_rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        profileRecycleView.setLayoutManager(linearLayoutManager);
        Button logout = view.findViewById(R.id.profile_btnLogOut);
        Button topDrink = view.findViewById(R.id.profile_grd_btnTopDrink);
        Button topIngredient = view.findViewById(R.id.profile_grd_btnTopIngredient);
        Button favoriteDrink = view.findViewById(R.id.profile_grd_btnFavoriteDrink);
        Button favoriteIngredient = view.findViewById(R.id.profile_btnFavoriteIngredient);
        TextView profileName = view.findViewById(R.id.profile_txtNameProfile);
        SearchView search = getActivity().findViewById(R.id.home_inpSearch);
        search.onActionViewCollapsed();
        profileName.setText(user.getEmail());
        List<Drink> array = new ArrayList<>();
        ArrayList<Button> buttonList = new ArrayList<>();
        buttonList.add(topDrink);
        buttonList.add(topIngredient);
        buttonList.add(favoriteDrink);
        buttonList.add(favoriteIngredient);
        String imgLink = "https://www.thecocktaildb.com//images//media//drink//2x8thr1504816928.jpg";
        logout.setOnClickListener(el -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_registrationActivity);
        });
        topDrink.setOnClickListener(el -> {
            isTopDrink = manageRecycleView(array, isTopDrink, 0, buttonList, 1);
            isTopIngredient = isFavoriteDrink = isFavoriteIngredient = false;
            DrinkSmallInfoRecyclerViewAdapter adapter = new DrinkSmallInfoRecyclerViewAdapter(array, drink -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", drink.getName());
                bundle.putString("img", imgLink);
                bundle.putString("from", ProfileFragment.class.getSimpleName());
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details, bundle);
                isTopDrink = false;
            });
            profileRecycleView.setAdapter(adapter);
        });

        topIngredient.setOnClickListener(el -> {
            isTopIngredient = manageRecycleView(array, isTopIngredient, 1, buttonList, 2);
            isTopDrink = isFavoriteDrink = isFavoriteIngredient = false;
            DrinkSmallInfoRecyclerViewAdapter adapter = new DrinkSmallInfoRecyclerViewAdapter(array, drink -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", (String) drink.getName());
                bundle.putString("img", imgLink);
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details, bundle);
                isTopIngredient = false;
            });
            profileRecycleView.setAdapter(adapter);
        });

        favoriteDrink.setOnClickListener(el -> {
            isFavoriteDrink = manageRecycleView(array, isFavoriteDrink,2, buttonList, 3);
            isTopDrink = isTopIngredient = isFavoriteIngredient = false;
            DrinkSmallInfoRecyclerViewAdapter adapter = new DrinkSmallInfoRecyclerViewAdapter(array, drink -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", (String) drink.getName());
                bundle.putString("img", imgLink);
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details, bundle);
                isFavoriteDrink = false;
            });
            profileRecycleView.setAdapter(adapter);
        });

        favoriteIngredient.setOnClickListener(el -> {
            isFavoriteIngredient = manageRecycleView(array, isFavoriteIngredient, 3, buttonList, 4);
            isTopDrink = isTopIngredient = isFavoriteDrink = false;
            DrinkSmallInfoRecyclerViewAdapter adapter = new DrinkSmallInfoRecyclerViewAdapter(array, drink -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", (String) drink.getName());
                bundle.putString("img", imgLink);
                Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_fragment_details, bundle);
                isFavoriteIngredient = false;
            });
            profileRecycleView.setAdapter(adapter);
        });

        return view;
    }

    private boolean manageRecycleView(List<Drink> array, boolean val, int c, ArrayList<Button> buttonList, int j){
        int backgroundColorDark = getResources().getColor(R.color.md_theme_dark_inverseOnSurface);
        int textColorDark = getResources().getColor(R.color.md_theme_dark_primary);
        int backgroundColorLight = getResources().getColor(R.color.md_theme_light_inverseOnSurface);
        int textColorLight = getResources().getColor(R.color.md_theme_light_primary);
        boolean darkMode = isDarkMode();
        array.clear();
        for (int i = 0; i < 1000; i++){
            array.add(new Drink(i, Integer.toString(j),null, null, null, null, null, null));
        }
        if (val)
            array.clear();
        if(darkMode){
            resetColor(buttonList, backgroundColorDark , textColorDark);
            val = changeColor(buttonList.get(c), val, backgroundColorDark , textColorDark);
        }else{
            resetColor(buttonList, backgroundColorLight, textColorLight);
            val = changeColor(buttonList.get(c), val, backgroundColorLight, textColorLight);
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

    private void resetColor(ArrayList<Button> buttonList, int backgroundColor, int textColor){
        for (int i = 0; i < 4; i++){
            buttonList.get(i).setBackgroundColor(backgroundColor);
            buttonList.get(i).setTextColor(textColor);
        }
    }

    private boolean isDarkMode() {
        return (getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

}