package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by bhatia on 4/1/2017.
 */

public class Transaction {
    @SerializedName("_id") private TransactonID transactionID;
    @SerializedName("account_id") private String accountID;
    @SerializedName("date_and_time") private Date dateAndTime;
    @SerializedName("amt_charged") private double amtCharged;
    @SerializedName("gps_long") private double gpsLongitude;
    @SerializedName("gps_lat") private double gpsLatitude;

    public Transaction() { /*no-args constructor*/ }
    public Transaction(String accountID, Date dateAndTime, double amtCharged, double gpsLongitude, double gpsLatitude) {
        //constructor uses all fields accept transactionID
        this.accountID = accountID;
        this.dateAndTime = dateAndTime;
        this.amtCharged = amtCharged;
        this.gpsLongitude = gpsLongitude;
        this.gpsLatitude = gpsLatitude;
    }

    public TransactonID getTransactionID() {
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

    public class TransactonID
    {
        @SerializedName("$oid") private String oid;
        public TransactonID() { /*no-args constructor*/ }

        public String getOid() {
            return oid;
        }
    }
}
