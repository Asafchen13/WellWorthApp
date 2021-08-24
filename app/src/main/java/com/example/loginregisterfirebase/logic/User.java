package com.example.loginregisterfirebase.logic;

import com.example.loginregisterfirebase.managers.DatabaseManager;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private List<Cryptocurrency> cryptocurrencies;
    private List<Asset> assets;
    private List<Fund> funds;


    public User() {
        this.cryptocurrencies = new ArrayList<>();
        this.assets = new ArrayList<>();
        this.funds = new ArrayList<>();
    }

    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;

        this.cryptocurrencies = new ArrayList<>();
        this.assets = new ArrayList<>();
        this.funds = new ArrayList<>();
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Cryptocurrency> getCryptocurrencies() {
        return cryptocurrencies;
    }

    public void setCryptocurrencies(List<Cryptocurrency> cryptocurrencies) {
        this.cryptocurrencies = (ArrayList)cryptocurrencies;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = (ArrayList)assets;
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public void setFunds(List<Fund> funds) {
        this.funds = (ArrayList)funds;
    }

    public double getTotalValue(){
        double totalValue = 0;
        totalValue = getCoinsValue()+getAssetsValue()+getFundsValue();
        return totalValue;
    }

    public double getCoinsValue(){
        double coinsValue = 0;

        for(Cryptocurrency c : cryptocurrencies) {
            coinsValue += c.getAmount() *c.getPriceUsd();
        }
        return coinsValue;
    }

    public double getAssetsValue() {
        double assetsValue = 0;

        for (Asset a :assets) {
            assetsValue += a.getValue();
        }
        return assetsValue;
    }

    public double getFundsValue() {
        double fundsValue = 0;

        for (Fund f: funds) {
            fundsValue += f.getValue();
        }
        return fundsValue;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> res = new HashMap<>();
        res.put(DatabaseManager.USER_ID, this.userId);
        res.put(DatabaseManager.USER_NAME, this.name);
        res.put(DatabaseManager.USER_EMAIL, this.email);
        res.put(DatabaseManager.USER_PHONE, this.phone);
        return res;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cryptocurrencies=" + cryptocurrencies +
                ", assets=" + assets +
                ", funds=" + funds +
                '}';
    }
}
