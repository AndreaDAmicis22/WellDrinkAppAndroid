package com.example.welldrink.data.repository.drink;

import android.util.Log;

import com.example.welldrink.data.source.drinks.BaseDrinkRemoteDataSource;

public class DrinkRepository implements IDrinkRepository, IDrinkResponseCallback {

    private final BaseDrinkRemoteDataSource drinkRemoteDataSource;

    public DrinkRepository(BaseDrinkRemoteDataSource drinkRemoteDataSource){
        this.drinkRemoteDataSource = drinkRemoteDataSource;
        this.drinkRemoteDataSource.setDrinkCallback(this);
    }

    @Override
    public void getDrinksByName(String name) {
        Log.d("API", "DrinkRepository->getDrinksByName");
        this.drinkRemoteDataSource.fetchDrinkByName(name);
    }
}
