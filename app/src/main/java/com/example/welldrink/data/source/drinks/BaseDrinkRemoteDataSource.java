package com.example.welldrink.data.source.drinks;


import com.example.welldrink.data.repository.drink.IDrinkResponseCallback;

public abstract class BaseDrinkRemoteDataSource {

    protected IDrinkResponseCallback drinkCallback;

    public void setDrinkCallback(IDrinkResponseCallback drinkCallback) {
        this.drinkCallback = drinkCallback;
    }

    public abstract void fetchDrinkByName(String name);

}
