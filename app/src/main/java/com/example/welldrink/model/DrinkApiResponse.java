package com.example.welldrink.model;

import androidx.annotation.NonNull;

import java.util.List;

public class DrinkApiResponse extends DrinkResponse {


    public DrinkApiResponse() {
        super();
    }

    public DrinkApiResponse(List<Drink> drinkList) {
        super(drinkList);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
