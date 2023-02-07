package com.example.welldrink.data.source.drinks;

import com.example.welldrink.data.repository.drink.IDrinkResponseCallback;

public abstract class BaseFavoriteDrinkDataSource {

    protected IDrinkResponseCallback drinkResponseCallback;

    public void setDrinkResponseCallback(IDrinkResponseCallback drinkResponseCallback) {
        this.drinkResponseCallback = drinkResponseCallback;
    }

    public abstract void setDrinkFavorite(String name);

    public abstract void setDrinkUnFavorite(String name);

    public abstract void fetchDrinkFavorite();

}
