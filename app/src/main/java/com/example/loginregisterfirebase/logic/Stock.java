package com.example.loginregisterfirebase.logic;

public class Stock {

    private String name;
    private double quantity;
    private double value;

    public Stock(String name, double quantity, double value) {
        this.name = name;
        this.quantity = quantity;
        this.value = value;
    }

    public Stock() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
