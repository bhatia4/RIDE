package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by bhatia on 4/1/2017.
 */

public class Transaction {
    @SerializedName("_id") private DataID transactionID;
    @SerializedName("account_id") private String accountID;
    @SerializedName("date_and_time") private Date dateAndTime;
    @SerializedName("amt_charged") private double amtCharged;
    @SerializedName("gps_long") private double gpsLongitude;
    @SerializedName("gps_lat") private double gpsLatitude;
    @SerializedName("beacon_id") private String beaconId;

    public Transaction() { /*no-args constructor*/ }
    public Transaction(String accountID, Date dateAndTime, double amtCharged, double gpsLongitude, double gpsLatitude, String beaconId) {
        //constructor uses all fields accept transactionID
        this.accountID = accountID;
        this.dateAndTime = dateAndTime;
        this.amtCharged = amtCharged;
        this.gpsLongitude = gpsLongitude;
        this.gpsLatitude = gpsLatitude;
        this.beaconId = beaconId;
    }

    public DataID getTransactionID() {
        return transactionID;
    }

    public String getAccountID() {
        return accountID;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public double getAmtCharged() {
        return amtCharged;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public String getBeaconId() {
        return beaconId;
    }
}
