package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bhatia on 5/5/2017.
 */

public class ConfigurationsAndSettings {
    @SerializedName("chargeValue") private double chargeValue;
    @SerializedName("chargeEveryIntervalInSecs") private long chargeEveryIntervalInSecs;

    public ConfigurationsAndSettings() { /*no-args constructor*/ }

    public ConfigurationsAndSettings(double chargeValue, long chargeEveryIntervalInSecs) {
        this.chargeValue = chargeValue;
        this.chargeEveryIntervalInSecs = chargeEveryIntervalInSecs;
    }

    public double getChargeValue() {
        return chargeValue;
    }

    public long getChargeEveryIntervalInSecs() {
        return chargeEveryIntervalInSecs;
    }
}
