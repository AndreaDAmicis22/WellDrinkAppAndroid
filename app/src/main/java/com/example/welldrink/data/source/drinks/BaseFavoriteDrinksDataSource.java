package com.example.welldrink.data.source.drinks;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.data.repository.drink.IDrinkResponseCallback;
import com.example.welldrink.model.Result;

public abstract class BaseFavoriteDrinksDataSource {

    protected IDrinkResponseCallback drinkResponseCallback;

    public void setDrinkResponseCallback(IDrinkResponseCallback drinkResponseCallback) {
        this.drinkResponseCallback = drinkResponseCallback;
    }

    public abstract void setDrinkFavorite(String name);

    public abstract void fetchDrinkFavorite();

}
