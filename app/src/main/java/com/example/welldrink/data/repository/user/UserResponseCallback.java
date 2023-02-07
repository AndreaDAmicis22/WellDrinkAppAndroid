package com.example.welldrink.data.repository.user;

import com.example.welldrink.model.User;

public interface UserResponseCallback {

    void onSuccessFromAuthentication(User user);
    void onSuccessFromRemoteDatabase(User user);
    void onFailureFromRemoteDatabase(String error);
    void onFailureFromAuthentication(String error);
    void onSuccessFromLogOut();

}
