package com.example.loginregisterfirebase.managers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.example.loginregisterfirebase.logic.Fund;
import com.example.loginregisterfirebase.logic.Stock;
import com.example.loginregisterfirebase.logic.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

    public static final String TAG = "DBManager";

    /**
     * Static strings for data base access
     */
    public static final String USERS = "Users";
    public static final String USER_NAME = "name";
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";

    public static final String CRYPTO = "Crypto";
    public static final String CRYPTO_AMOUNT = "amount";
    public static final String CRYPTO_CHNG_PERC = "changePercent24Hr";
    public static final String CRYPTO_ID = "id";
    public static final String CRYPTO_NAME = "name";
    public static final String CRYPTO_PRICE = "priceUsd";

    public static final String ASSETS = "Assets";
    public static final String ASSET_NAME = "name";
    public static final String ASSET_TYPE = "type";
    public static final String ASSET_VALUE = "value";

    public static final String FUNDS = "Funds";
    public static final String FUND_VALUE = "value";
    public static final String FUND_COMP = "company";

    public static final String STOCKS = "Stocks";


    public static DatabaseManager instance = null;

    private DatabaseReference myDatabaseReference;
    private User loggedUser = null;

    private DatabaseManager() {
        myDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }


    public void writeNewUser(String userId, String name, String email, String phone) {
        User user = new User(userId, name, email, phone);
        try {
            myDatabaseReference.child(USERS).child(userId).setValue(user);
            Log.d(TAG + "writeNewUser", "user added");

        } catch (Exception e) {
            Log.e(TAG + "writeNewUser", e.getMessage());
        }
    }

    public void addNewCryptocurrency(String id, String name, double priceUsd, double changePercent24Hr, double amount) {
        Cryptocurrency cryptocurrency = new Cryptocurrency(id, name, priceUsd, changePercent24Hr, amount);
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid()).child(CRYPTO).child(id).setValue(cryptocurrency);
            Log.d(TAG + "addNewCrypto", "crypto added");
        } catch (Exception e) {
            Log.e(TAG + "addNewCrypto", e.getMessage());

        }
    }

    public void addNewAsset(String name, String type, double value) {
        Asset asset = new Asset(name, type, value);

        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid()).child(CRYPTO).child(name).setValue(asset);
            Log.d(TAG + "addNewAsset", "asset added");
        } catch (Exception e) {
            Log.e(TAG + "addNewAsset", e.getMessage());

        }
    }

    public DatabaseReference getCurrentUserReference() {
        DatabaseReference reference = myDatabaseReference.child(USERS)
                .child(AuthenticationManager
                        .getInstance()
                        .getCurrentUser()
                        .getUid());
        return reference;
    }

    public DatabaseReference getUsersReference() {
        DatabaseReference reference = myDatabaseReference.child(USERS);
        return reference;
    }

    public DatabaseReference getUserCryptoRef() {

        DatabaseReference reference = myDatabaseReference.child(USERS)
                .child(AuthenticationManager
                        .getInstance()
                        .getCurrentUser()
                        .getUid())
                .child(CRYPTO);
        return reference;
    }

    public DatabaseReference getUserAssetsRef() {
        DatabaseReference reference = myDatabaseReference.child(USERS)
                .child(AuthenticationManager
                        .getInstance()
                        .getCurrentUser()
                        .getUid())
                .child(ASSETS);
        return reference;
    }

    public DatabaseReference getUserFundsRef() {
        DatabaseReference reference = myDatabaseReference.child(USERS)
                .child(AuthenticationManager
                        .getInstance()
                        .getCurrentUser()
                        .getUid())
                .child(FUNDS);
        return reference;
    }

    public DatabaseReference getUserStocksRef() {
        DatabaseReference reference = myDatabaseReference.child(USERS)
                .child(AuthenticationManager
                        .getInstance()
                        .getCurrentUser()
                        .getUid())
                .child(STOCKS);
        return reference;
    }

    public void updateCryptoData(Map<String, Object> map) {
        this.getUserCryptoRef().child(map.get(CRYPTO_ID).toString()).updateChildren(map);
    }

    private interface FireBaseCallback {
        void onCallback(User user);
    }

}

