package com.example.welldrink.ui;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.welldrink.R;
import com.example.welldrink.adapter.StartRecyclerViewAdapter;
import com.example.welldrink.model.Drink;

import java.util.ArrayList;
import java.util.List;


public class FirstStartFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FirstStartFragment() {
        // Required empty public constructor
    }

    public static FirstStartFragment newInstance(String param1, String param2) {
        return new FirstStartFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_start, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Drink> array = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            array.add(new Drink(i, Integer.toString(5),null, null, null, null, null, null));
        }
        RecyclerView researchRecycleView = view.findViewById(R.id.first_start_rscv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        researchRecycleView.setLayoutManager(linearLayoutManager);
        StartRecyclerViewAdapter adapter = new StartRecyclerViewAdapter(array);
        researchRecycleView.setAdapter(adapter);
    }
}