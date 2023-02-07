package com.example.welldrink.adapter;

import static com.example.welldrink.util.Constants.AMAZON_LINK;

import android.content.Intent;
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

public class DetailsRecyclerViewAdapter extends RecyclerView.Adapter<DetailsRecyclerViewAdapter.DetailsViewHolder> {
    private final List<Ingredient> ingredientList;
    private static final String TAG = DetailsRecyclerViewAdapter.class.getSimpleName();

    public DetailsRecyclerViewAdapter(List<Ingredient> ingredientList){
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
                String element = encode((String) ingredientName.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AMAZON_LINK+element));
                Log.d(TAG, AMAZON_LINK+ingredientName.getText());
                itemView.getContext().startActivity(intent);
            });
        }

        private String encode(String element) {
            element = element.replace(" ", "+");
            element = element.replace("'", "%27");
            element = element.replace("\\", "%5C");
            return element;
        }

        public void bind (Ingredient ingredient){
            ingredientName.setText(ingredient.getName());
            ingredientQt.setText(ingredient.getMeasure());
        }
    }

}
