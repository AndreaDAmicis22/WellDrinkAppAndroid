package com.example.welldrink.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.welldrink.R;
import com.example.welldrink.model.Ingredient;

import java.util.List;
public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailsViewHolder> {

    private List<Ingredient> ingredientList;
    public DetailRecyclerViewAdapter(List<Ingredient> ingredientList){
        this.ingredientList = ingredientList;
    }
    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_small, parent, false);

        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        holder.bind(ingredientList.get(position));

    }

    @Override
    public int getItemCount() {
        if (ingredientList != null){
            return ingredientList.size();
        }
        return 0;
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {

        private final TextView ingredientName;
        private final TextView ingredientQt;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.details_ingrName);
            ingredientQt =itemView.findViewById(R.id.details_ingrQt);
        }

        public void bind (Ingredient ingredient){
            ingredientName.setText(ingredient.getName());
            ingredientQt.setText(ingredient.getMeasure());
        }
    }


}
