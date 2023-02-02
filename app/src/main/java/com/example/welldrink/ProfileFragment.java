package com.example.welldrink;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private List<Drink> drinkList;
    private View view;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private Button favoriteDrinkButton;
    private Button favoriteIngredientButton;
    private Button tastedListButton;
    private Button visitedBarButton;

    public ProfileFragment() {

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
        Button favoriteDrinkButton = view.findViewById(R.id.profile_grd_btnFavouriteDrink);
        Button favoriteIngredientButton = view.findViewById(R.id.profile_grd_btnFavouriteIngredient);
        Button tastedListButton = view.findViewById(R.id.profile_grd_btnTastedList);
        Button visitedBarButton = view.findViewById(R.id.profile_grd_btnVisitedBar);
        Button top20Button = view.findViewById(R.id.profile_btnTop20);

        favoriteDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Drink> array = new ArrayList<>();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(1),"i","i","i","i","i",
                            "i", "i","i", "i", null, null));
                }
                ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
                profileRecycleView.setAdapter(adapter);
            }
        });

        favoriteIngredientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                List<Drink> array = new ArrayList<>();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(2),"i","i","i","i","i",
                            "i", "i","i", "i", null, null));
                }
                ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
                profileRecycleView.setAdapter(adapter);
            }
        });

        tastedListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                List<Drink> array = new ArrayList<>();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(3),"i","i","i","i","i",
                            "i", "i","i", "i", null, null));
                }
                ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
                profileRecycleView.setAdapter(adapter);
            }
        });

        visitedBarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                List<Drink> array = new ArrayList<>();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(4),"i","i","i","i","i",
                            "i", "i","i", "i", null, null));
                }
                ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
                profileRecycleView.setAdapter(adapter);
            }
        });
        top20Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                List<Drink> array = new ArrayList<>();
                for (int i = 0; i < 1000; i++){
                    array.add(new Drink(i, Integer.toString(5),"i","i","i","i","i",
                            "i", "i","i", "i", null, null));
                }
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
}