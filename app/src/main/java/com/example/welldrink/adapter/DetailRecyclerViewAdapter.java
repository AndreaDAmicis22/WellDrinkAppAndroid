package com.example.welldrink.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.welldrink.R;
import com.example.welldrink.model.Ingredient;

import java.util.List;

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailsViewHolder> {
    private final static String URL_AMAZON = "https://www.amazon.it/s?k=";
    private final List<Ingredient> ingredientList;
    private static final String TAG = DetailRecyclerViewAdapter.class.getSimpleName();

    public DetailRecyclerViewAdapter(List<Ingredient> ingredientList){
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ingredient_small, parent, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        holder.bind(ingredientList.get(position));
    }

    @Override
    public int getItemCount() {
        if (ingredientList != null)
            return ingredientList.size();
        else
            return 0;
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView ingredientName;
        private final TextView ingredientQt;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.details_ingrName);
            ingredientQt =itemView.findViewById(R.id.details_ingrQt);
            Button buyButton = itemView.findViewById(R.id.details_btnCart);
            buyButton.setOnClickListener(el -> {
                Log.d(TAG, "btnCart clicked");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(URL_AMAZON+ingredientName));
                itemView.getContext().startActivity(browserIntent);
            });
        }

        public void bind (Ingredient ingredient){
            ingredientName.setText(ingredient.getName());
            ingredientQt.setText(ingredient.getMeasure());
        }
    }

}
