package com.example.welldrink.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.welldrink.model.Drink;
import com.example.welldrink.R;
import com.example.welldrink.model.Ingredient;

import java.util.List;


public class IngredientSmallInfoRecyclerViewAdapter extends RecyclerView.Adapter<IngredientSmallInfoRecyclerViewAdapter.StartViewHolder> {
    private List<Ingredient> ingredientList;

    public IngredientSmallInfoRecyclerViewAdapter(List<Ingredient> ingredientList){
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientSmallInfoRecyclerViewAdapter.StartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_start_small, parent, false);
        return new IngredientSmallInfoRecyclerViewAdapter.StartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientSmallInfoRecyclerViewAdapter.StartViewHolder holder, int position) {
        holder.bind(ingredientList.get(position));
    }

    @Override
    public int getItemCount() {
        if (ingredientList != null){
            return ingredientList.size();
        }
        return 0;
    }

    public class StartViewHolder extends RecyclerView.ViewHolder {
        private final TextView drinkName;

        public StartViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.first_start_small_txt);

        }

        public void bind(Ingredient ingredient){
            drinkName.setText(ingredient.getName());
        }
    }
}
