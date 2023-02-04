package com.example.welldrink.data.source.drinks;

import android.util.Log;

import com.example.welldrink.data.service.IDrinkAPIService;
import com.example.welldrink.model.DrinkApiResponse;
import com.example.welldrink.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkRemoteDataSource extends BaseDrinkRemoteDataSource{

    private final IDrinkAPIService drinkAPIService;

    public DrinkRemoteDataSource(){
        this.drinkAPIService = ServiceLocator.getInstance().getDrinkAPIService();
    }


    @Override
    public void fetchDrinkByName(String name) {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getDrinksByName(name);

        drinkApiResponseCall.enqueue(new Callback<DrinkApiResponse>() {
            @Override
            public void onResponse(Call<DrinkApiResponse> call, Response<DrinkApiResponse> response) {
                if(response.body() != null && response.isSuccessful()){
                    Log.d("API", response.body().toString());
                }else{
                    Log.d("API", String.valueOf(response.isSuccessful()));
                    Log.d("API", "else ");
                }
            }

            @Override
            public void onFailure(Call<DrinkApiResponse> call, Throwable t) {
                Log.d("API", t.getMessage());
                Log.d("API", "onFailure");
            }
        });

    }
}
