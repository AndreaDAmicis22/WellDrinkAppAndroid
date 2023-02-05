package com.example.welldrink.data.service;

import static com.example.welldrink.util.Constants.API_BYNAME;
import static com.example.welldrink.util.Constants.API_BYNAMEPARAMETER;
import static com.example.welldrink.util.Constants.API_DETAILS;
import static com.example.welldrink.util.Constants.API_DETAILSPARAMETER;
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

}
