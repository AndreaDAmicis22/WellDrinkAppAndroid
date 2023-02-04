package com.example.welldrink.data.repository.drink;

import com.example.welldrink.model.DrinkApiResponse;

public interface IDrinkResponseCallback {

    void onSuccessFromRemote(DrinkApiResponse drinkApiResponse);

}
