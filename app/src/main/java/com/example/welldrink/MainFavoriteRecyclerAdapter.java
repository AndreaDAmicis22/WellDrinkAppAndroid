package com.example.welldrink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainFavoriteRecyclerAdapter extends RecyclerView.Adapter<MainFavoriteRecyclerAdapter.FavoriteViewHolder> {

    private final List<Favorite> favs;

    public MainFavoriteRecyclerAdapter(List<Favorite> favs) {
        this.favs = favs;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.bind(favs.get(position));
    }

    @Override
    public int getItemCount() {
        if (favs != null)
            return favs.size();
        else
            return 0;
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView value;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.fav_card_txtTitle);
            value = itemView.findViewById(R.id.fav_card_txtValue);
        }

        public void bind(Favorite favorite) {
            if (favorite.isDrink())
                title.setText(R.string.fav_card_title_drink);
            else
                title.setText(R.string.fav_card_title_ingredient);
            value.setText(favorite.getName());
        }
    }

}
