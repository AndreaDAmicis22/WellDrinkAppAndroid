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
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.welldrink.model.Drink;
import com.example.welldrink.R;
import com.example.welldrink.adapter.ProfileRecyclerViewAdapter;

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
        Button favoriteDrinkButton = view.findViewById(R.id.profile_grd_btnFavouriteDrink);
        Button favoriteIngredientButton = view.findViewById(R.id.profile_grd_btnFavouriteIngredient);
        Button tastedListButton = view.findViewById(R.id.profile_grd_btnTastedList);
        Button visitedBarButton = view.findViewById(R.id.profile_grd_btnVisitedBar);

        favoriteDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        favoriteIngredientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        tastedListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        visitedBarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView profileRecycleView = view.findViewById(R.id.profile_rcv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        profileRecycleView.setLayoutManager(linearLayoutManager);

        List<Drink> array = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            //array.add(new Drink(i,"i","i","i","i","i","i",
            //        "i", "i","i", "i", null, null));
        }
        Log.d(TAG, "cacca");
        ProfileRecyclerViewAdapter adapter = new ProfileRecyclerViewAdapter(array);
        profileRecycleView.setAdapter(adapter);
    }
}