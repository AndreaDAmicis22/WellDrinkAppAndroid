package com.example.welldrink.data.repository.user;

import com.example.welldrink.data.source.user.BaseUserAuthenticationRemoteDataSource;
import com.example.welldrink.data.source.user.BaseUserRemoteDataSource;
import com.example.welldrink.model.User;

public class UserRepository implements IUserRepository, UserResponseCallback{

    private final BaseUserRemoteDataSource userRemoteDataSource;
    private final BaseUserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource;

    public UserRepository(BaseUserRemoteDataSource userRemoteDataSource, BaseUserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource){
        this.userRemoteDataSource = userRemoteDataSource;
        this.userAuthenticationRemoteDataSource = userAuthenticationRemoteDataSource;
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userAuthenticationRemoteDataSource.setUserResponseCallback(this);
    }


    @Override
    public void signUp(String mail, String password) {
        userAuthenticationRemoteDataSource.signUp(mail, password);
    }


    @Override
    public void onSuccessFromAuthentication(User user) {

    }
}
