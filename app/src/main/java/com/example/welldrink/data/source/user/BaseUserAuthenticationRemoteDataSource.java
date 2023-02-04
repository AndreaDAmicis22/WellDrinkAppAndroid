package com.example.welldrink.data.source.user;

import com.example.welldrink.data.repository.user.UserResponseCallback;
import com.example.welldrink.model.User;

public abstract class BaseUserAuthenticationRemoteDataSource {

    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void signUp(String email, String password);

    public abstract void signIn(String email, String password);

    public abstract User getLoggedUser();

}
