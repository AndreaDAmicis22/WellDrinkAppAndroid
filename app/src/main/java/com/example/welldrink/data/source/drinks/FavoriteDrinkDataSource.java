package com.example.welldrink.data.source.drinks;

import static com.example.welldrink.util.Constants.DB_FAVORITEDRINK;
import static com.example.welldrink.util.Constants.DB_REALTIME;
import static com.example.welldrink.util.Constants.DB_USER;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FavoriteDrinkDataSource extends BaseFavoriteDrinksDataSource{

    private final DatabaseReference databaseReference;
    private final String userToken;

    public FavoriteDrinkDataSource(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(DB_REALTIME);
        databaseReference = firebaseDatabase.getReference().getRef();
        userToken = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    @Override
    public void setDrinkFavorite(String name) {
        databaseReference.child(DB_USER).child(userToken)
                .child(DB_FAVORITEDRINK)
                .child(String.valueOf(name.hashCode()))
                .setValue(name).addOnSuccessListener(a -> {
                    Log.d("FIRE", "Messo a favorite");
                }).addOnFailureListener(err -> {
                    Log.d("FIRE", "ERRORE in favorite");
                });
    }

    @Override
    public void fetchDrinkFavorite() {
        databaseReference.child(DB_USER).child(userToken).child(DB_FAVORITEDRINK).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.d("FIRE", "task.isSuccessful()");
                Log.d("FIRE", task.getResult().getValue().toString());
            }else{
                Log.d("FIRE", "ERROR task.isSuccessful()");
            }
        });
    }
}
