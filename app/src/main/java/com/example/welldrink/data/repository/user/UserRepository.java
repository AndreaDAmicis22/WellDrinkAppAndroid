package com.example.welldrink.data.repository.user;

import com.example.welldrink.data.source.user.BaseUserRemoteDataSource;

public class UserRepository implements IUserRepository, UserResponseCallback{

    private final BaseUserRemoteDataSource userRemoteDataSource;

    public UserRepository(BaseUserRemoteDataSource userRemoteDataSource){
        this.userRemoteDataSource = userRemoteDataSource;
        this.userRemoteDataSource.setUserResponseCallback(this);
    }

}
