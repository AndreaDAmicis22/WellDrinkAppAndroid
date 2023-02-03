package com.example.welldrink.data.source.user;

import android.util.Log;

import com.example.welldrink.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAuthenticationRemoteDataSource extends BaseUserAuthenticationRemoteDataSource{

    private final FirebaseAuth firebaseAuth;

    public UserAuthenticationRemoteDataSource() { firebaseAuth = FirebaseAuth.getInstance();}

    @Override
    public void signUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.d("AUTH", "Signup-taskSuccessful");
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                Log.d("AUTH", "Signup-firebaseUser");
                if(firebaseUser != null){
                    Log.d("AUTH", "Signup-firebaseUser != null");
                    userResponseCallback.onSuccessFromAuthentication(
                            new User(firebaseUser.getDisplayName(), email, firebaseUser.getUid())
                    );
                }else{
                    Log.d("AUTH", "FirebaseUser == null");
                    Log.e("AUTH", task.getException().getMessage());
                }
            }else{
                Log.d("AUTH", "Signup-taskFailed");
                Log.e("AUTH", task.getException().getMessage());
            }
        });
    }

}
