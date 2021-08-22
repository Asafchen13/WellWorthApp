package com.example.loginregisterfirebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.example.loginregisterfirebase.logic.Fund;
import com.example.loginregisterfirebase.logic.Stock;
import com.example.loginregisterfirebase.logic.User;
import com.example.loginregisterfirebase.managers.DatabaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserViewModel extends ViewModel {

    private static final String TAG = "USER_VIEW_MODEL";

    private User user;

    private MutableLiveData<User> mUser;
    private MutableLiveData<List<Cryptocurrency>> cryptocurrencies;
    private MutableLiveData<List<Asset>> assets;
    private MutableLiveData<List<Fund>> funds;
    private MutableLiveData<List<Stock>> stocks;


    public MutableLiveData<User> getUser() {
        if (mUser == null) {
            mUser = new MutableLiveData<>();
            loadUser(user -> mUser.setValue(user));
        }
        return mUser;
    }


    public MutableLiveData<List<Cryptocurrency>> getCryptocurrencies() {
        if (cryptocurrencies == null) {
            cryptocurrencies = new MutableLiveData<>();
            loadCryptoCoins(list -> cryptocurrencies.setValue((ArrayList<Cryptocurrency>) list));
        }

        return cryptocurrencies;
    }

    public MutableLiveData<List<Asset>> getAssets() {
        if (assets == null) {
            assets = new MutableLiveData<>();
            loadAssets(list -> assets.setValue((ArrayList<Asset>) list));


        }
        return assets;
    }

    public MutableLiveData<List<Fund>> getFunds() {
        if (funds == null) {
            funds = new MutableLiveData<>();
            loadFunds(list -> funds.setValue((ArrayList<Fund>) list));
        }
        return funds;
    }

    public MutableLiveData<List<Stock>> getStocks() {
        if (stocks == null) {
            stocks = new MutableLiveData<>();
            loadStocks(list -> {
            });
        }
        return stocks;
    }

    private void loadUser(FireBaseUserCallback fireBaseUserCallback) {
        try {
            if (user == null) {
                user = new User();
            }
            DatabaseManager.getInstance().getCurrentUserReference().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    user.setName(snapshot.child("name").getValue(String.class));
                    user.setUserId(snapshot.child("userId").getValue(String.class));
                    user.setPhone(snapshot.child("phone").getValue(String.class));
                    user.setEmail(snapshot.child("email").getValue(String.class));

                    fireBaseUserCallback.onCallback(user);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "in loadUser() " + e.getMessage());
        }
    }

    private void loadCryptoCoins(FireBaseCallback fireBaseCallback) {
        try {
            if (user == null) {
                user = new User();
            }
            DatabaseManager.getInstance().getUserCryptoRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    user.getCryptocurrencies().clear();
                    for (DataSnapshot coinSnapshot : snapshot.getChildren()) {

                        double changePercent24Hr = coinSnapshot.child(DatabaseManager.CRYPTO_CHNG_PERC).getValue(double.class);
                        double priceUsd = coinSnapshot.child(DatabaseManager.CRYPTO_PRICE).getValue(double.class);
                        String name = coinSnapshot.child(DatabaseManager.CRYPTO_NAME).getValue(String.class);
                        String id = coinSnapshot.child(DatabaseManager.CRYPTO_ID).getValue(String.class);
                        double amount = coinSnapshot.child(DatabaseManager.CRYPTO_AMOUNT).getValue(double.class);
                        Cryptocurrency cryptocurrency = new Cryptocurrency(id, name, priceUsd, changePercent24Hr, amount);
                        if (!user.getCryptocurrencies().contains(cryptocurrency)) {
                            user.getCryptocurrencies().add(cryptocurrency);
                        }

                    }
                    fireBaseCallback.onCallback(user.getCryptocurrencies());
                    Log.d(TAG, "loadCryptoCoins crypto coins loaded");
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.e(TAG, "loadCryptoCoins() : " + error.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }

    }

    private void loadAssets(FireBaseCallback fireBaseCallback) {
        try {
            if (user == null) {
                user = new User();
            }
            DatabaseManager.getInstance().getUserAssetsRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    user.getAssets().clear();
                    for (DataSnapshot assetSnapshot : snapshot.getChildren()) {
                        String name = assetSnapshot.child(DatabaseManager.ASSET_NAME).getValue(String.class);
                        String type = assetSnapshot.child(DatabaseManager.ASSET_TYPE).getValue(String.class);
                        double value = assetSnapshot.child(DatabaseManager.ASSET_VALUE).getValue(double.class);

                        Asset asset = new Asset(name, type, value);
                        user.getAssets().add(asset);
                    }
                    fireBaseCallback.onCallback(user.getAssets());
                    Log.d(TAG, "loadAssets assets loaded");

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.e(TAG, "loadAssets() :" + error.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }
    }

    private void loadFunds(FireBaseCallback fireBaseCallback) {
        try {
            if (user == null) {
                user = new User();
            }
            DatabaseManager.getInstance().getUserFundsRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    user.getFunds().clear();
                    for (DataSnapshot fundSnapshot : snapshot.getChildren()) {
                        String name = fundSnapshot.getValue(String.class);
                        String company = fundSnapshot.child("company").getValue(String.class);
                        double value = Double.parseDouble(Objects.requireNonNull(fundSnapshot.child("type").getValue(String.class)));

                        Fund fund = new Fund(name, company, value);
                        user.getFunds().add(fund);
                    }
                    fireBaseCallback.onCallback(user.getFunds());
                    Log.d(TAG, "loadFunds funds loaded");

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.e(TAG, "loadFunds() : " + error.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }

    }

    private void loadStocks(FireBaseCallback fireBaseCallback) {

    }

    private interface FireBaseCallback {
        void onCallback(List<?> list);
    }

    private interface FireBaseUserCallback {
        void onCallback(User user);
    }

}
