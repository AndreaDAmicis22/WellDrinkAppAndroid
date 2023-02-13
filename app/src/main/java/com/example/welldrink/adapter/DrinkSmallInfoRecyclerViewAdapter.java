package com.example.welldrink.adapter;

import static com.example.welldrink.util.LikeHandler.getFilled;
import static com.example.welldrink.util.LikeHandler.getUnfilled;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.welldrink.model.Drink;
import com.example.welldrink.R;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkSmallInfoRecyclerViewAdapter extends RecyclerView.Adapter<DrinkSmallInfoRecyclerViewAdapter.DrinkSmallInfoViewHolder> {

    public interface OnItemClickListener {
        void onDrinkClick(Drink drink);
    }

    private final List<Drink> drinkList;
    private final OnItemClickListener onItemClickListener;

    private final DrinkViewModel drinkViewModel;

    public DrinkSmallInfoRecyclerViewAdapter(List<Drink> drinkList, OnItemClickListener onItemClickListener, DrinkViewModel drinkviewModel) {
        this.drinkList = drinkList;
        this.onItemClickListener = onItemClickListener;
        this.drinkViewModel = drinkviewModel;
    }

    public DrinkSmallInfoRecyclerViewAdapter(List<Drink> drinkList, DrinkViewModel drinkviewModel) {
        this.drinkList = drinkList;
        this.onItemClickListener = drink -> {
        };
        this.drinkViewModel = drinkviewModel;
    }

    @NonNull
    @Override
    public DrinkSmallInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_small_info, parent, false);
        return new DrinkSmallInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkSmallInfoViewHolder holder, int position) {
        holder.bind(drinkList.get(position));
    }

    @Override
    public int getItemCount() {
        if (drinkList != null) {
            return drinkList.size();
        }
        return 0;
    }

    public class DrinkSmallInfoViewHolder extends RecyclerView.ViewHolder {
        private final TextView drinkName;
        private final ImageView drinkImage;

        private boolean isFavorite;

        public DrinkSmallInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.drink_small_txt);
            drinkImage = itemView.findViewById(R.id.drink_small_img);
            CardView card = itemView.findViewById(R.id.drink_small_card);
            card.setOnClickListener(el -> {
                drinkViewModel.clearDrinkDetails();
                drinkViewModel.getDrinkDetail((String) drinkName.getText());
                onItemClickListener.onDrinkClick(drinkList.get(getAdapterPosition()));
            });
            Button likeButton = itemView.findViewById(R.id.drink_btnLike);
            likeButton.setOnClickListener(el -> {
                if (isFavorite) {
                    likeButton.setBackground(getUnfilled(itemView.getResources(), itemView.getContext().getTheme()));
                    drinkViewModel.setDrinkUnfavorite((String) drinkName.getText());
                } else {
                    likeButton.setBackground(getFilled(itemView.getResources(), itemView.getContext().getTheme()));
                    drinkViewModel.setDrinkFavorite((String) drinkName.getText());
                }
                isFavorite = !isFavorite;
                drinkList.get(getAdapterPosition()).setFavorite(isFavorite);
            });
        }

        public void bind(Drink drink) {
            drinkName.setText(drink.getName());
            Picasso.get().load(drink.getImageUrl()).into(drinkImage);
            this.isFavorite = drink.isFavorite();
            Button likeButton = itemView.findViewById(R.id.drink_btnLike);
            if (isFavorite) {
                likeButton.setBackground(getFilled(itemView.getResources(), itemView.getContext().getTheme()));
            } else {
                likeButton.setBackground(getUnfilled(itemView.getResources(), itemView.getContext().getTheme()));
            }
        }
    }
}
