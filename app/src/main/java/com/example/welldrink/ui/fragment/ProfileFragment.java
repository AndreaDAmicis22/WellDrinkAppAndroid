package com.example.welldrink.ui.fragment;

import static com.example.welldrink.util.ButtonHandler.handleClick;
import static com.example.welldrink.util.ButtonHandler.isDarkMode;

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
import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Result;
import com.example.welldrink.model.User;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.example.welldrink.ui.viewModel.UserViewModelFactory;
import com.example.welldrink.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private UserViewModel userViewModel;
    private DrinkViewModel drinkViewModel;
    private User user;
    private final List<Button> buttonsList;
    private int selected;
    private boolean darkMode;

    private static final String TAG = ProfileFragment.class.getSimpleName();

    public ProfileFragment() {
        buttonsList = new ArrayList<>();
        selected = -1;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        darkMode = isDarkMode(requireContext());
        RecyclerView profileRecycleView = view.findViewById(R.id.profile_rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        profileRecycleView.setLayoutManager(linearLayoutManager);
        Button logout = view.findViewById(R.id.profile_btnLogOut);
        TextView profileName = view.findViewById(R.id.profile_txtNameProfile);
        SearchView search = requireActivity().findViewById(R.id.home_inpSearch);
        search.onActionViewCollapsed();
        profileName.setText(user.getEmail());
        buttonsList.add(view.findViewById(R.id.profile_grd_btnTopDrink));
        buttonsList.add(view.findViewById(R.id.profile_grd_btnTopIngredient));
        buttonsList.add(view.findViewById(R.id.profile_grd_btnFavoriteDrink));
        buttonsList.add(view.findViewById(R.id.profile_grd_btnFavoriteIngredient));
        int bgDark = getResources().getColor(R.color.md_theme_dark_inverseOnSurface);
        int txtDark = getResources().getColor(R.color.md_theme_dark_primary);
        int bgLight = getResources().getColor(R.color.md_theme_light_inverseOnSurface);
        int txtLight = getResources().getColor(R.color.md_theme_light_primary);
        for (Button button : buttonsList) {
            button.setOnClickListener(el -> {
                if (darkMode)
                    selected = handleClick(getResources(), selected, buttonsList, button, bgDark, txtDark);
                else
                    selected = handleClick(getResources(), selected, buttonsList, button, bgLight, txtLight);
                this.handleCall(selected);
            });
        }

        drinkViewModel.getDrinkMutableLiveData().observe(getViewLifecycleOwner(), result -> {
            Log.d("RES", "PROFILE OBSERVER");
            if(result.isSuccess()){
                List<Drink> drinkList = ((Result.Success<List<Drink>>) result).getData();
                Log.d("RES", "ProfileFragment: " + drinkList.toString());
                RecyclerView researchRecycleView = view.findViewById(R.id.profile_rcv);
                researchRecycleView.setLayoutManager(linearLayoutManager);
                DrinkSmallInfoRecyclerViewAdapter adapter = new DrinkSmallInfoRecyclerViewAdapter(drinkList, drink -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", drink.getName());
                    Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_detailsActivity, bundle);
                }, drinkViewModel);
                researchRecycleView.setAdapter(adapter);
            }else{
                Log.d("RES", "ERROR Result.isSuccessfull");
            }
        });

        logout.setOnClickListener(el -> Navigation.findNavController(requireView()).navigate(R.id.action_fragment_profile_to_registrationActivity));
        return view;
    }

    private void handleCall(int selected){
        switch(selected){
            case -1:
                Log.d("RES", "CALL -1");
                this.drinkViewModel.clearDrinkMutableLiveData();
                break;
            case 0:
                this.drinkViewModel.getTopDrinksLiveData();
                break;
            case 1:
                this.drinkViewModel.getTopIngredientsLiveData();
                break;
            case 2:
                this.drinkViewModel.getFavoriteDrinks();
            default:
                Log.d("RES", "Click on -> " + selected);
        }
    }

}