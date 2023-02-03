package com.example.welldrink.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.DetailRecyclerViewAdapter;
import com.example.welldrink.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment  extends Fragment {

    private boolean onLike;
    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance(String param1, String param2) {
        return new DetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ImageButton btnLike = view.findViewById(R.id.details_btnLike);
        btnLike.setOnClickListener(view1 -> {
             onLike = likeOn(btnLike, onLike);
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Ingredient> array = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            array.add(new Ingredient(0,"lemon",false,1542,null, "10oz"));
        }
        RecyclerView detailsRecycleView = view.findViewById(R.id.details_recView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        detailsRecycleView.setLayoutManager(linearLayoutManager);
        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(array);
        detailsRecycleView.setAdapter(adapter);

    }

    private Boolean likeOn(ImageButton button, Boolean bool){
        if(bool == false){
            button.setPressed(true);
            button.setActivated(true);
            return true;
        }
        else {
            button.setPressed(false);
            button.setActivated(false);
        }
        return false;
    }
}