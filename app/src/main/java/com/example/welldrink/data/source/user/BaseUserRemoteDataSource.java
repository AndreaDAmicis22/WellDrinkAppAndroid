package com.example.welldrink.data.source.user;

import com.example.welldrink.data.repository.user.UserResponseCallback;

public abstract class BaseUserRemoteDataSource {

    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback){
        this.userResponseCallback = userResponseCallback;
    }

}
