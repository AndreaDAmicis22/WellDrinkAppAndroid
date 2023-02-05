package com.example.welldrink.ui;

import com.example.welldrink.R;
import com.example.welldrink.adapter.MainFavoriteRecyclerViewAdapter;
import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.model.Favorite;
import com.example.welldrink.util.ServiceLocator;
import com.squareup.picasso.Picasso;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private IDrinkRepository drinkRepository;

    private final String[] names;
    private String imgLink;

    public MainFragment() {
        names = new String[]{"Name 1", "Name 2", "Name 3", "Name 4", "Name 5"};
        imgLink = "https://www.thecocktaildb.com//images//media//drink//2x8thr1504816928.jpg";
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.drinkRepository = ServiceLocator.getInstance().getDrinkRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = view.findViewById(R.id.home_random_btn);
        TextView name = view.findViewById(R.id.home_random_txtTitle);
        TextView category = view.findViewById(R.id.home_random_txtCategory);
        TextView glass = view.findViewById(R.id.home_random_txtGlass);
        TextView alcol = view.findViewById(R.id.home_random_txtAlcol);
        ImageView image = view.findViewById(R.id.home_random_img);
        Picasso.get().load(imgLink).into(image);
        CardView card = view.findViewById(R.id.home_random_card);
        Random rand = new Random();
        card.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", (String) name.getText());
            bundle.putString("category", (String) category.getText());
            bundle.putString("glass", (String) glass.getText());
            bundle.putString("alcol", (String) alcol.getText());
            bundle.putString("img", imgLink);
            Navigation.findNavController(requireView()).navigate(R.id.action_fragment_main_to_fragment_details, bundle);
        });
        button.setOnClickListener(view1 -> {
            String actualName = (String) name.getText();
            int pos;
            do {
                pos = rand.nextInt(names.length);
            } while (names[pos].equals(actualName));
            name.setText(names[pos]);
            category.setText(names[pos].toLowerCase());
            this.drinkRepository.getDrinksByName("Gin");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Favorite> favs = getFavsUser();
        for (int i = 0; i < 5; i++) {
            favs.add(new Favorite(false, "Soda"));
        }
        RecyclerView recyclerView = view.findViewById(R.id.home_rcvFavorite);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MainFavoriteRecyclerViewAdapter adapter = new MainFavoriteRecyclerViewAdapter(favs);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Favorite> getFavsUser() {
        return new ArrayList<>();
    }
}