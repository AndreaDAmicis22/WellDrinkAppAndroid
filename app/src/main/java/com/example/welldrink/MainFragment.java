package com.example.welldrink;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private final String[] names;

    public MainFragment() {
        names = new String[]{"Name 1", "Name 2", "Name 3", "Name 4", "Name 5"};
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = view.findViewById(R.id.home_random_btn);
        TextView name = view.findViewById(R.id.home_random_txtTitle);
        TextView desc = view.findViewById(R.id.home_random_txtValue);
        Random rand = new Random();
        button.setOnClickListener(view1 -> {
            String actualName = (String) name.getText();
            int pos;
            do {
                pos = rand.nextInt(names.length);
            } while (names[pos].equals(actualName));
            name.setText(names[pos]);
            desc.setText(names[pos].toLowerCase());
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
        MainFavoriteRecyclerAdapter adapter = new MainFavoriteRecyclerAdapter(favs);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Favorite> getFavsUser() {
        return new ArrayList<>();
    }
}