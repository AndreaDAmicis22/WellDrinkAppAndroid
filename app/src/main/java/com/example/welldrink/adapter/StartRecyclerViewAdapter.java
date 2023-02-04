package com.example.welldrink.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.welldrink.model.Drink;
import com.example.welldrink.R;

import java.util.List;


public class StartRecyclerViewAdapter extends RecyclerView.Adapter<StartRecyclerViewAdapter.StartViewHolder> {
    private List<Drink> drinkList;

    public StartRecyclerViewAdapter(List<Drink> drinkList){
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public StartRecyclerViewAdapter.StartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_start_small, parent, false);
        return new StartRecyclerViewAdapter.StartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartRecyclerViewAdapter.StartViewHolder holder, int position) {
        holder.bind(drinkList.get(position));
    }

    @Override
    public int getItemCount() {
        if (drinkList != null){
            return drinkList.size();
        }
        return 0;
    }

    public class StartViewHolder extends RecyclerView.ViewHolder {
        private final TextView drinkName;

        public StartViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.first_start_small_txt);

        }

        public void bind(Drink drink){
            drinkName.setText(drink.getName());
        }
    }
}
