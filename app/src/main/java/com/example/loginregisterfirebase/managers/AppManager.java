package com.example.loginregisterfirebase.managers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.example.loginregisterfirebase.logic.Fund;
import com.example.loginregisterfirebase.logic.Stock;
import com.example.loginregisterfirebase.logic.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AppManager {

    private static final String TAG = "AppManager";

    private static AppManager instance = null;


    private User currentUser;
    private ArrayList<Cryptocurrency> cryptocurrencies;
    private ArrayList<Asset> assets;
    private ArrayList<Fund> funds;
    private ArrayList<Stock> stocks;


    private AppManager() {
    }

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void setUpCurrentUser(){
        String uid = AuthenticationManager.getInstance().getCurrentUser().getUid();
        DatabaseReference mRef = DatabaseManager.getInstance().getUsersReference().child(uid);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                currentUser =(User) snapshot.getValue(User.class);
                Log.d(TAG + " setUpUser", "User set up : " + currentUser.toString());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e(TAG + "setUpCurrUser", error.getMessage());
            }
        });

    }
}
