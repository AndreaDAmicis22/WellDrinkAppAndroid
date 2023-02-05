package com.example.welldrink.adapter;

import android.graphics.drawable.Drawable;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkSmallInfoRecyclerViewAdapter extends RecyclerView.Adapter<DrinkSmallInfoRecyclerViewAdapter.ProfileDrinkViewHolder> {

    private int selectedPos = RecyclerView.NO_POSITION;
    private static final String TAG = DrinkSmallInfoRecyclerViewAdapter.class.getSimpleName();

    public interface OnItemClickListener {
        void onDrinkClick(Drink drink);
    }

    private List<Drink> drinkList;
    private OnItemClickListener onItemClickListener;

    public DrinkSmallInfoRecyclerViewAdapter(List<Drink> drinkList, OnItemClickListener onItemClickListener) {
        this.drinkList = drinkList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProfileDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_small_info, parent, false);
        return new ProfileDrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileDrinkViewHolder holder, int position) {
        holder.bind(drinkList.get(position));
    }

    @Override
    public int getItemCount() {
        if (drinkList != null) {
            return drinkList.size();
        }
        return 0;
    }

    public class ProfileDrinkViewHolder extends RecyclerView.ViewHolder {
        private final TextView drinkName;
        private final ImageView drinkImage;

        private boolean clicked;

        public ProfileDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            clicked = false;
            drinkName = itemView.findViewById(R.id.drink_small_txt);
            drinkImage = itemView.findViewById(R.id.drink_small_img);
            CardView card = itemView.findViewById(R.id.drink_small_card);
            Button likeButton = itemView.findViewById(R.id.drink_btnLike);
            card.setOnClickListener(el -> {
                onItemClickListener.onDrinkClick(drinkList.get(getAdapterPosition()));
            });
            likeButton.setOnClickListener(el -> {
                Drawable filled = itemView.getResources().getDrawable(R.drawable.ic_baseline_thumb_up_alt_24, itemView.getContext().getTheme());
                Drawable unfilled = itemView.getResources().getDrawable(R.drawable.ic_baseline_thumb_up_off_alt_24, itemView.getContext().getTheme());
                if (clicked) {
                    likeButton.setBackground(unfilled);
                } else {
                    likeButton.setBackground(filled);
                }
                clicked = !clicked;
            });
        }

        public void bind(Drink drink) {
            drinkName.setText(drink.getName());
            Picasso.get().load(drink.getImageUrl()).into(drinkImage);
        }
    }
}
