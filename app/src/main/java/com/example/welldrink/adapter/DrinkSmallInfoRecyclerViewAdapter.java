package com.example.welldrink.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
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

    private int selectedPos = RecyclerView.NO_POSITION;
    private static final String TAG = DrinkSmallInfoRecyclerViewAdapter.class.getSimpleName();

    public DrinkSmallInfoRecyclerViewAdapter(List<Drink> array, OnItemClickListener onItemClickListener) {
    }

    public interface OnItemClickListener {
        void onDrinkClick(Drink drink);
    }

    private List<Drink> drinkList;
    private OnItemClickListener onItemClickListener;

    private DrinkViewModel drinkViewModel;

    public DrinkSmallInfoRecyclerViewAdapter(List<Drink> drinkList, OnItemClickListener onItemClickListener, DrinkViewModel drinkviewModel) {
        this.drinkList = drinkList;
        this.onItemClickListener = onItemClickListener;
        this.drinkViewModel = drinkviewModel;
    }

    public DrinkSmallInfoRecyclerViewAdapter(List<Drink> drinkList, DrinkViewModel drinkviewModel) {
        this.drinkList = drinkList;
        this.onItemClickListener = drink -> {};
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
            Drawable filled = itemView.getResources().getDrawable(R.drawable.ic_baseline_thumb_up_alt_24, itemView.getContext().getTheme());
            Drawable unfilled = itemView.getResources().getDrawable(R.drawable.ic_baseline_thumb_up_off_alt_24, itemView.getContext().getTheme());
            likeButton.setOnClickListener(el -> {
                Log.d("LIKE", "CREATE " + isFavorite);
                if (isFavorite) {
                    likeButton.setBackground(unfilled);
                    drinkViewModel.setDrinkUnfavorite((String) drinkName.getText());
                } else {
                    likeButton.setBackground(filled);
                    drinkViewModel.setDrinkFavorite((String) drinkName.getText());
                }
                //clicked = !clicked;
                isFavorite = !isFavorite;
            });
        }

        public void bind(Drink drink) {
            drinkName.setText(drink.getName());
            Picasso.get().load(drink.getImageUrl()).into(drinkImage);
            this.isFavorite = drink.isFavorite();
            Button likeButton = itemView.findViewById(R.id.drink_btnLike);
            Drawable filled = itemView.getResources().getDrawable(R.drawable.ic_baseline_thumb_up_alt_24, itemView.getContext().getTheme());
            Drawable unfilled = itemView.getResources().getDrawable(R.drawable.ic_baseline_thumb_up_off_alt_24, itemView.getContext().getTheme());
            Log.d("LIKE", drinkName.getText() + " " + isFavorite);
            if(isFavorite){
                likeButton.setBackground(filled);
            }else{
                likeButton.setBackground(unfilled);
            }
        }
    }
}
