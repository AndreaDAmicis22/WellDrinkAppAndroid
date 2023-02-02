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

public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.ProfileDrinkViewHolder>{

    private List<Drink> drinkList;

    public ProfileRecyclerViewAdapter(List<Drink> drinkList){
        this.drinkList = drinkList;
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
        if (drinkList != null){
            return drinkList.size();
        }
        return 0;
    }

    public class ProfileDrinkViewHolder extends RecyclerView.ViewHolder {
        private final TextView drinkName;
        //private final ImageView drinkImage;

        public ProfileDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.drink_small_txt);
            //drinkImage = itemView.findViewById(R.id.drink_small_img);
        }

        public void bind(Drink drink){
            drinkName.setText(drink.getName());
            //drinkImage.setImageURI();
        }
    }
}
