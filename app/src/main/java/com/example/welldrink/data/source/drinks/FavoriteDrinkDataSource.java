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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
                    //call back che aggiunge il dirnk alla mutablelivedata dei favorites
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
                Map<String, String> favorites = (HashMap<String, String>) task.getResult().getValue();
//                for(String s : favorites.values()){
//                    Log.d("FIRE", s);
//                }
                drinkResponseCallback.onSuccessFromFetchFavorite(new ArrayList<>(favorites.values()));
            }else{
                Log.d("FIRE", "ERROR task.isSuccessful()");
            }
        });
    }
}
