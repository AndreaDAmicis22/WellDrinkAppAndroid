package com.example.welldrink.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.welldrink.model.Drink;
import com.example.welldrink.R;
import com.example.welldrink.model.Ingredient;
import com.example.welldrink.ui.viewModel.DrinkViewModel;

import java.util.List;


public class IngredientSmallInfoRecyclerViewAdapter extends RecyclerView.Adapter<IngredientSmallInfoRecyclerViewAdapter.StartViewHolder> {
    private List<Drink> ingredientList;
    private final DrinkViewModel drinkViewModel;


    public IngredientSmallInfoRecyclerViewAdapter(List<Drink> ingredientList, DrinkViewModel drinkViewModel){
        this.ingredientList = ingredientList;
        this.drinkViewModel = drinkViewModel;
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
        private boolean isFavorite;

        public StartViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.first_start_small_txt);
            CardView card = itemView.findViewById(R.id.first_start_small_card);
            card.setOnClickListener(el -> {
                if(isFavorite)
                    drinkViewModel.setIngredientUnfavorite((String) drinkName.getText());
                else
                    drinkViewModel.setIngredientFavorite((String) drinkName.getText());
                isFavorite = !isFavorite;
            });
        }

        public void bind(Drink ingredient){
            drinkName.setText(ingredient.getName());
            this.isFavorite = ingredient.isFavorite();
        }
    }
}
