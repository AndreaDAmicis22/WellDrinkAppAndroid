package com.example.welldrink.model;

import androidx.annotation.NonNull;

import java.util.List;

public class DrinkResponse {

    private boolean isLoading;

    private List<Drink> drinkList;

    public DrinkResponse() {}

    public DrinkResponse(List<Drink> drinkList){
        this.drinkList = drinkList;
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    @NonNull
    @Override
    public String toString() {
        return "ToString Response " + drinkList.toString();
    }
}
