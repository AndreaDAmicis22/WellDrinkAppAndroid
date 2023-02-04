package com.example.welldrink.data.service;

import static com.example.welldrink.util.Constants.API_BYNAME;
import static com.example.welldrink.util.Constants.API_BYNAMEPARAMETER;

import com.example.welldrink.model.DrinkApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDrinkAPIService {
    @GET(API_BYNAME)
    Call<DrinkApiResponse> getDrinksByName(
        @Query(API_BYNAMEPARAMETER) String name
    );

}
