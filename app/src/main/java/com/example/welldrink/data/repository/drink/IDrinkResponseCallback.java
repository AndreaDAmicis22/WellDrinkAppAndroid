package com.example.welldrink.data.repository.drink;

import com.example.welldrink.model.DrinkApiResponse;

import java.util.List;

public interface IDrinkResponseCallback {

    void onSuccessFromRemote(DrinkApiResponse drinkApiResponse);

    void onFailureFromRemote(String message);

    void onSuccessFromRemoteRandom(DrinkApiResponse drinkApiREsponse);

    void onSuccessFromRemoteDetails(DrinkApiResponse drinkApiResponse);

    void onSuccessFromFetchFavorite(List<String> favoriteList);

    void onSuccessFromFetchFavoriteRemote(DrinkApiResponse drinkApiResponse);

    void onSuccessFromAddingFavorite(String name);

    void onSuccessFromRemovingFavorite(String name);

    void onSuccessFromAddingFavoriteIngredient(String name);

    void onSuccessFromRemovingFavoriteIngredient(String name);

    void onSuccessFromFetchFavoriteIngredients(List<String> name);

}
