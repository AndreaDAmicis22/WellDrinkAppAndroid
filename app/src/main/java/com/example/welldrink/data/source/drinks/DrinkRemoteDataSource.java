package com.example.welldrink.data.source.drinks;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.welldrink.data.service.IDrinkAPIService;
import com.example.welldrink.model.DrinkApiResponse;
import com.example.welldrink.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkRemoteDataSource extends BaseDrinkRemoteDataSource {

    private final IDrinkAPIService drinkAPIService;

    public DrinkRemoteDataSource() {
        this.drinkAPIService = ServiceLocator.getInstance().getDrinkAPIService();
    }


    @Override
    public void fetchDrinkByName(String name) {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getDrinksByName(name);
        this.handleCall(drinkApiResponseCall);
    }

    @Override
    public void fetchRandomDrink() {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getDrinkRandom();
        drinkApiResponseCall.enqueue(new Callback<DrinkApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<DrinkApiResponse> call, @NonNull Response<DrinkApiResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d("API", response.body().toString());
                    if (response.body().getDrinkList().isEmpty()) {
                        drinkCallback.onFailureFromRemote("Error in API call");
                    } else {
                        drinkCallback.onSuccessFromRemoteRandom(response.body());
                    }
                } else {
                    Log.d("API", String.valueOf(response.isSuccessful()));
                    Log.d("API", "else ");
                    drinkCallback.onFailureFromRemote("Error in API call");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DrinkApiResponse> call, @NonNull Throwable t) {
                Log.d("API", t.getMessage());
                Log.d("API", "onFailure");
                drinkCallback.onFailureFromRemote(t.getMessage());
            }
        });
    }

    @Override
    public void fetchDrinkDetail(String name) {
        Call<DrinkApiResponse> res = this.drinkAPIService.getDrinkDetails(name);

        res.enqueue(new Callback<DrinkApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<DrinkApiResponse> call, @NonNull Response<DrinkApiResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d("API", response.body().toString());
                    if (response.body().getDrinkList().isEmpty()) {
                        drinkCallback.onFailureFromRemote("Error in API call");
                    } else {
                        drinkCallback.onSuccessFromRemoteDetails(response.body());
                    }
                } else {
                    Log.d("API", String.valueOf(response.isSuccessful()));
                    Log.d("API", "else ");
                    drinkCallback.onFailureFromRemote("Error in API call");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DrinkApiResponse> call, @NonNull Throwable t) {
                Log.d("API", t.getMessage());
                Log.d("API", "onFailure");
                drinkCallback.onFailureFromRemote(t.getMessage());
            }
        });
    }

    @Override
    public void fetchDrinkByIngredient(String ingredientName) {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getDrinkByIngredient(ingredientName);
        this.handleCall(drinkApiResponseCall);
    }

    @Override
    public void fetchDrinkByGlass(String glassName) {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getDrinksByGlass(glassName);
        this.handleCall(drinkApiResponseCall);
    }

    @Override
    public void fetchDrinkByCategory(String category) {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getDrinkByCategory(category);
        this.handleCall(drinkApiResponseCall);
    }

    @Override
    public void fetchTopDrinks() {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getTopDrinks();
        this.handleCall(drinkApiResponseCall);
    }

    @Override
    public void fetchTopIngredients() {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getTopIngredients();
        this.handleCall(drinkApiResponseCall);
    }

    @Override
    public void fetchIngredientsByName(String ingredientName) {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getIngredientsByName(ingredientName);
        this.handleCall(drinkApiResponseCall);
    }

    @Override
    public void fetchFavoritesByName(String name) {
        Call<DrinkApiResponse> drinkApiResponseCall = this.drinkAPIService.getDrinkDetails(name);

        drinkApiResponseCall.enqueue(new Callback<DrinkApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<DrinkApiResponse> call, @NonNull Response<DrinkApiResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d("API", response.body().toString());
                    if (response.body().getDrinkList().isEmpty()) {
                        drinkCallback.onFailureFromRemote("Error in API call");
                    } else {
                        drinkCallback.onSuccessFromFetchFavoriteRemote(response.body());
                    }

                } else {
                    Log.d("API", String.valueOf(response.isSuccessful()));
                    Log.d("API", "else ");
                    drinkCallback.onFailureFromRemote("Error in API call");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DrinkApiResponse> call, @NonNull Throwable t) {

            }
        });

    }

    private void handleCall(Call<DrinkApiResponse> drinkApiResponseCall) {
        drinkApiResponseCall.enqueue(new Callback<DrinkApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<DrinkApiResponse> call, @NonNull Response<DrinkApiResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d("API", response.body().toString());
                    drinkCallback.onSuccessFromRemote(response.body());
                } else {
                    Log.d("API", String.valueOf(response.isSuccessful()));
                    Log.d("API", "else ");
                    drinkCallback.onFailureFromRemote("Error in API call");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DrinkApiResponse> call, @NonNull Throwable t) {
                Log.d("API", t.getMessage());
                Log.d("API", "onFailure");
                drinkCallback.onFailureFromRemote(t.getMessage());
            }
        });
    }


}
