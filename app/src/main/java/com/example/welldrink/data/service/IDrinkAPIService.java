package com.example.welldrink.data.service;

import static com.example.welldrink.util.Constants.API_BYINGREDIENT;
import static com.example.welldrink.util.Constants.API_BYINGREDIENTPARAMETER;
import static com.example.welldrink.util.Constants.API_BYNAME;
import static com.example.welldrink.util.Constants.API_BYNAMEPARAMETER;
import static com.example.welldrink.util.Constants.API_DETAILS;
import static com.example.welldrink.util.Constants.API_DETAILSPARAMETER;
import static com.example.welldrink.util.Constants.API_GETBYCATEGORY;
import static com.example.welldrink.util.Constants.API_GETBYCATEGORYPARAMETER;
import static com.example.welldrink.util.Constants.API_GETBYGLASS;
import static com.example.welldrink.util.Constants.API_GETBYGLASSPARAMETER;
import static com.example.welldrink.util.Constants.API_GETINGREDIENTSBYNAME;
import static com.example.welldrink.util.Constants.API_GETINGREDIENTSBYNAMEPARAMETER;
import static com.example.welldrink.util.Constants.API_GETTOPDRINKS;
import static com.example.welldrink.util.Constants.API_GETTOPINGREDIENTS;
import static com.example.welldrink.util.Constants.API_RANDOMDRINK;

import com.example.welldrink.model.DrinkApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDrinkAPIService {
    @GET(API_BYNAME)
    Call<DrinkApiResponse> getDrinksByName(
        @Query(API_BYNAMEPARAMETER) String name
    );

    @GET(API_RANDOMDRINK)
    Call<DrinkApiResponse> getDrinkRandom();

    @GET(API_DETAILS)
    Call<DrinkApiResponse> getDrinkDetails(
            @Query(API_DETAILSPARAMETER) String name
    );

    @GET(API_BYINGREDIENT)
    Call<DrinkApiResponse> getDrinkByIngredient(
            @Query(API_BYINGREDIENTPARAMETER) String ingredientName
    );

    @GET(API_GETBYGLASS)
    Call<DrinkApiResponse> getDrinksByGlass(
            @Query(API_GETBYGLASSPARAMETER) String glassName
    );

    @GET(API_GETBYCATEGORY)
    Call<DrinkApiResponse> getDrinkByCategory(
            @Query(API_GETBYCATEGORYPARAMETER) String category
    );

    @GET(API_GETTOPDRINKS)
    Call<DrinkApiResponse> getTopDrinks();

    @GET(API_GETTOPINGREDIENTS)
    Call<DrinkApiResponse> getTopIngredients();

    @GET(API_GETINGREDIENTSBYNAME)
    Call<DrinkApiResponse> getIngredientsByName(
            @Query(API_GETINGREDIENTSBYNAMEPARAMETER) String name
    );

}
