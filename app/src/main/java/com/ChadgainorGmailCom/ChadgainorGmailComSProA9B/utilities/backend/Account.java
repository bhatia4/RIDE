package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bhatia on 4/1/2017.
 */

public class Account implements Cloneable {
    @SerializedName("_id") private DataID accountID;
    private String name;
    private String address;
    private String phone;
    private String age;
    private Character gender;
    private String race;
    private double balance;
    @SerializedName("avg_travel_time") private String avgTravelTime;
    @SerializedName("avg_travel_freq") private String avgTravelFreq;

    public Account() { /*no-args constructor*/ }
    public Account(String oid, String name, String address, String phone, String age, Character gender, String race, double balance, String avgTravelTime, String avgTravelFreq) {
        this.accountID = new DataID(oid);
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.race = race;
        this.balance = balance;
        this.avgTravelTime = avgTravelTime;
        this.avgTravelFreq = avgTravelFreq;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public DataID getAccountID() {
        return accountID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public Character getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAvgTravelTime() {
        return avgTravelTime;
    }

    public String getAvgTravelFreq() {
        return avgTravelFreq;
    }
}
