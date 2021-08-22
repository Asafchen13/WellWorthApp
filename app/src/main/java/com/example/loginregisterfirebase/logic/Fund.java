package com.example.loginregisterfirebase.logic;

public class Fund {

    private String Name;
    private String company;
    private double value;

    public Fund() {
    }

    public Fund(String name, String company, double value) {
        Name = name;
        this.company = company;
        this.value = value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
