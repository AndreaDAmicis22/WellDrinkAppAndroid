package com.example.welldrink;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private String[] names;
    private String[] ingrs;

    public MainFragment() {
        names = new String[] {"Name 1", "Name 2", "Name 3", "Name 4", "Name 5"};
        ingrs = new String[] {"Ingr 1", "Ingr 2", "Ingr 3", "Ingr 4", "Ingr 5"};
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//test
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = view.findViewById(R.id.home_random_btn);
        TextView name = view.findViewById(R.id.home_random_txtTitle);
        TextView desc = view.findViewById(R.id.home_random_txtValue);
        CardView card = view.findViewById(R.id.home_card1);
        LinearLayout linFav = view.findViewById(R.id.home_linFavorite);
        TextView textView = new TextView(view.getContext());
        textView.setText("Test");
        CardView test = new CardView(view.getContext());
        linFav.addView(textView);
        Random rand = new Random();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actualName = (String) name.getText();
                int pos;
                do {
                    pos = rand.nextInt(names.length);
                } while (names[pos].equals(actualName));
                name.setText(names[pos]);
                desc.setText(names[pos].toLowerCase());
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}