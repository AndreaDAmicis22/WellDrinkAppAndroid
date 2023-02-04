package com.example.welldrink.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.welldrink.model.Drink;
import com.example.welldrink.R;

import java.util.List;

public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.ProfileDrinkViewHolder>{

    public interface OnItemClickListener {
        void onDrinkClick(Drink drink);
    }
    private List<Drink> drinkList;
    private OnItemClickListener onItemClickListener;
    public ProfileRecyclerViewAdapter(List<Drink> drinkList, OnItemClickListener onItemClickListener){
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
        if (drinkList != null){
            return drinkList.size();
        }
        return 0;
    }

    public class ProfileDrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView drinkName;
        private final Button likeBUtton;
        //private final ImageView drinkImage;

        public ProfileDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.drink_small_txt);
            //drinkImage = itemView.findViewById(R.id.drink_small_img);
            likeBUtton = itemView.findViewById(R.id.drink_btnLike);
            itemView.setOnClickListener(this);
            likeBUtton.setOnClickListener(this);
        }

        public void bind(Drink drink){
            drinkName.setText(drink.getName());
            //drinkImage.setImageURI();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.drink_btnLike) {
                //cambia icona e aggiungi drink alla lista dei piaciuti
            } else{
                onItemClickListener.onDrinkClick(drinkList.get(getAdapterPosition()));
            }
        }
    }
}
