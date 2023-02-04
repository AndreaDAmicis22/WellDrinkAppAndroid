package com.example.welldrink.util;


import static com.example.welldrink.util.Constants.API_URL;

import android.app.Application;
import android.util.Log;

import com.example.welldrink.data.repository.drink.DrinkRepository;
import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.data.repository.user.UserRepository;
import com.example.welldrink.data.service.IDrinkAPIService;
import com.example.welldrink.data.source.drinks.BaseDrinkRemoteDataSource;
import com.example.welldrink.data.source.drinks.DrinkRemoteDataSource;
import com.example.welldrink.data.source.user.BaseUserAuthenticationRemoteDataSource;
import com.example.welldrink.data.source.user.BaseUserRemoteDataSource;
import com.example.welldrink.data.source.user.UserAuthenticationRemoteDataSource;
import com.example.welldrink.data.source.user.UserRemoteDataSource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//singleton
public class ServiceLocator {

    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance(){
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public IDrinkAPIService getDrinkAPIService(){
        Retrofit r = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return r.create(IDrinkAPIService.class);
    }

    public IUserRepository getUserRepository(){
        BaseUserRemoteDataSource userRemoteDataSource = new UserRemoteDataSource();
        BaseUserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource = new UserAuthenticationRemoteDataSource();
        return new UserRepository(userRemoteDataSource, userAuthenticationRemoteDataSource);
    }

    public IDrinkRepository getDrinkRepository(){
        BaseDrinkRemoteDataSource drinkRemoteDataSource = new DrinkRemoteDataSource();
        return new DrinkRepository(drinkRemoteDataSource);
    }

}
