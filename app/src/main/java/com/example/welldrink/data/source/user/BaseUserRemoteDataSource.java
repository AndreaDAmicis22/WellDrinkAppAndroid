package com.example.welldrink.data.source.user;

import com.example.welldrink.data.repository.user.UserResponseCallback;
import com.example.welldrink.model.User;

public abstract class BaseUserRemoteDataSource {

    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUser(User user);

}
