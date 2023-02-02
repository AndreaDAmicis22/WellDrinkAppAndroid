package com.example.welldrink;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<String> drinkList;
    private View view;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private Button favoriteDrinkButton;
    private Button favoriteIngredientButton;
    private Button tastedListButton;
    private Button visitedBarButton;

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.profile_linFirst);
        Button favoriteDrinkButton = view.findViewById(R.id.profile_grd_btnFavouriteDrink);
        Button favoriteIngredientButton = view.findViewById(R.id.profile_grd_btnFavouriteIngredient);
        Button tastedListButton = view.findViewById(R.id.profile_grd_btnTastedList);
        Button visitedBarButton = view.findViewById(R.id.profile_grd_btnVisitedBar);

        favoriteDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemCardView();
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

    public void addItemCardView(){
        linearLayout.addView(copyCardView());
    }

    public CardView copyCardView(){
        CardView cardView = view.findViewById(R.id.profile_cardview);
        CardView newCardView = cardView;
        return newCardView;
    }

}