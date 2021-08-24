package com.example.loginregisterfirebase.managers;

import android.util.Log;

import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.example.loginregisterfirebase.logic.Fund;
import com.example.loginregisterfirebase.logic.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public static final String FUNDS_NAME = "name";
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

    public void addNewCryptocurrency(Cryptocurrency cryptocurrency) {
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(CRYPTO)
                    .child(cryptocurrency.getId())
                    .setValue(cryptocurrency);
            Log.d(TAG + "addNewCrypto", "deleteCryptocurrency(), crypto added");
        } catch (Exception e) {
            Log.e(TAG + "addNewCrypto", e.getMessage());

        }
    }

    public void deleteCryptocurrency(Cryptocurrency cryptocurrency) {
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(CRYPTO)
                    .child(cryptocurrency.getId())
                    .removeValue();
            Log.d(TAG, "deleteCryptocurrency(), crypto deleted");
        } catch (Exception e) {
            Log.e(TAG, "deleteCryptocurrency() " + e.getMessage());

        }
    }

    public void deleteAsset(Asset asset) {
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(ASSETS)
                    .child(asset.getName())
                    .removeValue();
            Log.d(TAG, "deleteAsset(), asset deleted");
        } catch (Exception e) {
            Log.e(TAG, "deleteAsset() " + e.getMessage());

        }
    }

    public void addNewAsset(Asset a) {
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(ASSETS)
                    .child(a.getName())
                    .setValue(a);
            Log.d(TAG + "addNewAsset", "asset added");
        } catch (Exception e) {
            Log.e(TAG + "addNewAsset", e.getMessage());

        }
    }

    public void updateAsset(Asset oldAsset, Asset newAsset) {
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(ASSETS)
                    .child(oldAsset.getName())
                    .removeValue();
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(ASSETS).child(newAsset.getName()).setValue(newAsset);

            Log.d(TAG + "updateAsset", "asset updated");
        } catch (Exception e) {
            Log.e(TAG + "updateAsset", e.getMessage());

        }
    }

    public void addNewFund(Fund f) {
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(FUNDS)
                    .child(f.getName())
                    .setValue(f);
            Log.d(TAG + "addNewFund", "fund added");
        } catch (Exception e) {
            Log.e(TAG + "addNewFund", e.getMessage());

        }
    }

    public void deleteFund(Fund fund) {
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(FUNDS)
                    .child(fund.getName())
                    .removeValue();
            Log.d(TAG, "deleteFund(), fund deleted");
        } catch (Exception e) {
            Log.e(TAG, "deleteFund() " + e.getMessage());

        }
    }

    public void updateFund(Fund oldFund, Fund newFund) {
        try {
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(FUNDS)
                    .child(oldFund.getName())
                    .removeValue();
            myDatabaseReference.child(USERS).child(AuthenticationManager.getInstance().getCurrentUser().getUid())
                    .child(FUNDS).child(newFund.getName()).setValue(newFund);

            Log.d(TAG + "updateFund", "fund updated");
        } catch (Exception e) {
            Log.e(TAG + "updateFund", e.getMessage());

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



}

