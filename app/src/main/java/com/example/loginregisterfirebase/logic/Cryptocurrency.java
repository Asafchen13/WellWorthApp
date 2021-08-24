package com.example.loginregisterfirebase.logic;

import com.example.loginregisterfirebase.managers.DatabaseManager;

import java.util.HashMap;
import java.util.Map;

public class Cryptocurrency {

    private String id;
    private String name;
    private double priceUsd;
    private double changePercent24Hr;
    private double amount;

    public Cryptocurrency() {
    }

    public Cryptocurrency(String id, String name, double priceUsd, double changePercent24Hr, double amount) {
        this.id = id;
        this.name = name;
        this.priceUsd = priceUsd;
        this.changePercent24Hr = changePercent24Hr;
        this.amount = amount;
    }

    public Cryptocurrency(String id, String name, double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public double getChangePercent24Hr() {
        return changePercent24Hr;
    }

    public void setChangePercent24Hr(double changePercent24Hr) {
        this.changePercent24Hr = changePercent24Hr;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(DatabaseManager.CRYPTO_ID, this.id);
        map.put(DatabaseManager.CRYPTO_NAME, this.name);
        map.put(DatabaseManager.CRYPTO_PRICE, this.priceUsd);
        map.put(DatabaseManager.CRYPTO_AMOUNT, this.amount);
        return map;
    }

    @Override
    public String toString() {
        return "Cryptocurrency{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", priceUsd='" + priceUsd + '\'' +
                ", changePercent24Hr='" + changePercent24Hr + '\'' +
                '}';
    }
}