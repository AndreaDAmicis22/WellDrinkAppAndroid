package com.example.welldrink.ui.fragment;

import static com.example.welldrink.util.ButtonHandler.getBgDark;
import static com.example.welldrink.util.ButtonHandler.getBgLight;
import static com.example.welldrink.util.ButtonHandler.getTxtDark;
import static com.example.welldrink.util.ButtonHandler.getTxtLight;
import static com.example.welldrink.util.ButtonHandler.handleClick;
import static com.example.welldrink.util.ButtonHandler.isDarkMode;
import static com.example.welldrink.util.ErrorSnackbars.handleDrinkError;

import android.content.res.Resources;
import android.os.Bundle;

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
import android.widget.TextView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.DrinkSmallInfoRecyclerViewAdapter;
import com.example.welldrink.adapter.IngredientSmallInfoRecyclerViewAdapter;
import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Result;
import com.example.welldrink.model.User;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.example.welldrink.ui.viewModel.UserViewModelFactory;
import com.example.welldrink.util.ServiceLocator;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private UserViewModel userViewModel;
    private DrinkViewModel drinkViewModel;
    private User user;
    private final List<Button> buttonsList;
    private int selected;
    private boolean darkMode;
    private List<Drink> drinkList;
    private boolean favorites;
    private RecyclerView recyclerView;

    public ProfileFragment() {
        buttonsList = new ArrayList<>();
        selected = -1;
        this.favorites = false;
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
        drinkViewModel = new ViewModelProvider(requireActivity()).get(DrinkViewModel.class);
        drinkViewModel.clearDrinkMutableLiveData();
        this.drinkList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        darkMode = isDarkMode(requireContext());
        RecyclerView profileRecycleView = view.findViewById(R.id.profile_rcv);
        this.recyclerView = profileRecycleView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        profileRecycleView.setLayoutManager(linearLayoutManager);
        Button logout = view.findViewById(R.id.profile_btnLogOut);
        TextView profileName = view.findViewById(R.id.profile_txtNameProfile);
        SearchView search = requireActivity().findViewById(R.id.home_inpSearch);
        search.onActionViewCollapsed();
        profileName.setText(user.getName());
        addButtonsToList(buttonsList, view);
        for (Button button : buttonsList) {
            button.setOnClickListener(el -> {
                if (selected != -1 && button.equals(buttonsList.get(selected)))
                    removeLoadingScreen(view);
                else
                    activeLoadingScreen(view);
                Resources resources = getResources();
                if (darkMode)
                    selected = handleClick(getResources(), selected, buttonsList, button, getBgDark(resources), getTxtDark(resources));
                else
                    selected = handleClick(getResources(), selected, buttonsList, button, getBgLight(resources), getTxtLight(resources));
                this.handleCall(selected, view);
            });
        }
        drinkViewModel.getFavoritesLiveData().observe(getViewLifecycleOwner(), result -> {
            Log.d("RES", "OBSERVER FAV");
            Log.e("ALERT", "OBSERVERRRRRRR");
            if (result.isSuccess() ) {
                List<Drink> favorites = new ArrayList<>(((Result.Success<Map<String, Drink>>) result).getData().values());
                if(selected == 2){
                    Log.d("RES", "ProfileFragment: " + favorites);
                    this.attachToRecycleViewDrink(favorites);
                    removeLoadingScreen(view);
                }else if (selected == 0){
                    Log.e("ALERT", "ALERT");
                    for(Drink d : drinkList){
                        d.setFavorite(false);
                        for(Drink s : favorites){
                            if(d.getName().equals(s.getName()))
                                d.setFavorite(true);
                        }
                    }
                    this.attachToRecycleViewDrink(drinkList);
                }
            } else if (selected == 2) {
                handleDrinkError(view);
            }
        });
        drinkViewModel.getDrinkMutableLiveData().observe(getViewLifecycleOwner(), result -> {
            Log.d("RES", "PROFILE OBSERVER");
            if (result.isSuccess()) {
                List<Drink> drinkList = ((Result.Success<List<Drink>>) result).getData();
                this.drinkList = drinkList;
                Log.d("RES", "ProfileFragment: " + drinkList.toString());
                if (selected == 1) {
                    for (String s : drinkViewModel.getFavoriteIngredientsList())
                        for (Drink d : drinkList)
                            if (d.getName().equals(s))
                                d.setFavorite(true);
                    attachToRecycleViewIngredient(drinkList);
                } else if (selected == 0){
                    Log.e("ALERT", "ALERT");
                    List<Drink> favorites = new ArrayList<>(((Result.Success<Map<String, Drink>>) this.drinkViewModel.getFavoritesLiveData().getValue()).getData().values());
                    for(Drink d : drinkList){
                        d.setFavorite(false);
                        for(Drink s : favorites){
                            if(d.getName().equals(s.getName()))
                                d.setFavorite(true);
                        }
                    }
                    this.attachToRecycleViewDrink(drinkList);
                }else
                    this.attachToRecycleViewDrink(drinkList);
                removeLoadingScreen(view);
            } else {
                handleDrinkError(view);
            }
        });
        logout.setOnClickListener(el -> {
            this.userViewModel.logOut();
            Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_registrationActivity);
        });
        return view;
    }

    private void removeLoadingScreen(View view) {
        CircularProgressIndicator loading = view.findViewById(R.id.profile_progress);
        loading.setVisibility(View.INVISIBLE);
        RecyclerView recyclerView = view.findViewById(R.id.profile_rcv);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void activeLoadingScreen(View view) {
        CircularProgressIndicator loading = view.findViewById(R.id.profile_progress);
        loading.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = view.findViewById(R.id.profile_rcv);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void addButtonsToList(List<Button> arrayList, View view) {
        arrayList.add(view.findViewById(R.id.profile_grd_btnTopDrink));
        arrayList.add(view.findViewById(R.id.profile_grd_btnTopIngredient));
        arrayList.add(view.findViewById(R.id.profile_grd_btnFavoriteDrink));
        arrayList.add(view.findViewById(R.id.profile_grd_btnFavoriteIngredient));
    }

    private void handleCall(int selected, View view) {
        switch (selected) {
            case -1:
                if (this.favorites) {
                    this.attachToRecycleViewDrink(new ArrayList<>());
                    favorites = false;
                } else
                    this.drinkViewModel.clearDrinkMutableLiveData();
                break;
            case 0:
                this.drinkViewModel.getTopDrinksLiveData();
                break;
            case 1:
                this.drinkViewModel.getTopIngredientsLiveData();
                break;
            case 2:
                favorites = true;
                if (!this.drinkViewModel.getFavoriteDrinks())
                    this.attachToRecycleViewDrink(new ArrayList<>(this.drinkViewModel.getFavoriteMap().values()));
                removeLoadingScreen(view);
                break;
            case 3:
                if (!this.drinkViewModel.getFavoriteIngredient()) {
                    List<Drink> dl = new ArrayList<>();
                    for (String n : this.drinkViewModel.getFavoriteIngredientsList())
                        dl.add(new Drink(n, true));
                    this.attachToRecycleViewIngredient(dl);
                }
                removeLoadingScreen(view);
                break;
            default:
                break;
        }
    }

    private void attachToRecycleViewDrink(List<Drink> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        this.recyclerView.setLayoutManager(linearLayoutManager);
        DrinkSmallInfoRecyclerViewAdapter adapter = new DrinkSmallInfoRecyclerViewAdapter(list, drink -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", drink.getName());
            bundle.putBoolean("fav", drink.isFavorite());
            Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_detailsActivity, bundle);
        }, drinkViewModel);
        this.recyclerView.setAdapter(adapter);
    }

    private void attachToRecycleViewIngredient(List<Drink> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        this.recyclerView.setLayoutManager(linearLayoutManager);
        IngredientSmallInfoRecyclerViewAdapter adapter = new IngredientSmallInfoRecyclerViewAdapter(list, drinkViewModel);
        this.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.drinkViewModel.forceFavoriteFetch();
    }

}