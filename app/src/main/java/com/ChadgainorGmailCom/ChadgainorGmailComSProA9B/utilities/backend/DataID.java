package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bhatia on 4/4/2017.
 */
public class DataID {
    @SerializedName("$oid")
    private String oid;

    public DataID() { /*no-args constructor*/ }

    public DataID(String oid) {
        this.oid = oid;
    }

    public String getOid() {
        return oid;
    }
}
