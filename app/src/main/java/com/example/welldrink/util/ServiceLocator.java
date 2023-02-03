package com.example.welldrink.util;


import android.app.Application;
import android.util.Log;

import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.data.repository.user.UserRepository;
import com.example.welldrink.data.source.user.BaseUserAuthenticationRemoteDataSource;
import com.example.welldrink.data.source.user.BaseUserRemoteDataSource;
import com.example.welldrink.data.source.user.UserAuthenticationRemoteDataSource;
import com.example.welldrink.data.source.user.UserRemoteDataSource;

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

    public IUserRepository getUserRepository(){
        BaseUserRemoteDataSource userRemoteDataSource = new UserRemoteDataSource();
        Log.d("serv", "end");
        BaseUserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource = new UserAuthenticationRemoteDataSource();
        return new UserRepository(userRemoteDataSource, userAuthenticationRemoteDataSource);
    }

}
