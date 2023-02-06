package com.example.welldrink.ui.fragment;

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

    public FirstStartFragment() {

    }

    public static FirstStartFragment newInstance() {
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
        Bundle args = getArguments();
        List<Drink> array = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            if (args != null && !args.getBoolean("first"))
                array.add(new Drink(i, Integer.toString(5),null, null, null, null, null, null));
            else
                array.add(new Drink(i, Integer.toString(0),null, null, null, null, null, null));
        }
        RecyclerView researchRecycleView = view.findViewById(R.id.first_start_rscv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        researchRecycleView.setLayoutManager(linearLayoutManager);
        StartRecyclerViewAdapter adapter = new StartRecyclerViewAdapter(array);
        researchRecycleView.setAdapter(adapter);
    }
}