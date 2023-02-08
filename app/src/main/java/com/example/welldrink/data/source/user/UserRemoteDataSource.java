package com.example.welldrink.data.source.user;

import static com.example.welldrink.util.Constants.DB_REALTIME;
import static com.example.welldrink.util.Constants.DB_USER;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.welldrink.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRemoteDataSource extends BaseUserRemoteDataSource {

    private final DatabaseReference databaseReference;

    public UserRemoteDataSource() {
        Log.d("dataSource", "startConst");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(DB_REALTIME);
        Log.d("dataSource", "firebaseDatabase");
        databaseReference = firebaseDatabase.getReference().getRef();
        Log.d("dataSource", "getRef");
    }

    @Override
    public void saveUser(User user) {
        databaseReference.child(DB_USER).child(user.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("AUTH", "saveUser user alrady in db");
                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                } else {
                    Log.d("AUTH", "saveUser user was not in db");
                    databaseReference.child(DB_USER).child(user.getId()).setValue(user).addOnSuccessListener(a -> {
                        Log.d("AUTH", "user saved in db");
                        userResponseCallback.onSuccessFromRemoteDatabase(user);
                    }).addOnFailureListener(e -> {
                        Log.d("AUTH", "FAIL saving user in db");
                        userResponseCallback.onFailureFromRemoteDatabase(e.getLocalizedMessage());
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("AUTH", "saveUser -> onCancelled");
                Log.d("AUTH", error.getMessage());
                userResponseCallback.onFailureFromRemoteDatabase(error.getMessage());
            }
        });
    }
}
