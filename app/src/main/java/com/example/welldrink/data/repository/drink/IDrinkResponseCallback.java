package com.example.welldrink.data.repository.drink;

import com.example.welldrink.model.DrinkApiResponse;

public interface IDrinkResponseCallback {

    void onSuccessFromRemote(DrinkApiResponse drinkApiResponse);

    void onFailureFromRemote(String message);

    void onSuccessFromRemoteRandom(DrinkApiResponse drinkApiREsponse);

    void onSuccessFromRemoteDetails(DrinkApiResponse drinkApiResponse);

}
