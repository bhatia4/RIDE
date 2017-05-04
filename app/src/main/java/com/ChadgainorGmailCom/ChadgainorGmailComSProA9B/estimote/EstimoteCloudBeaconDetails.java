package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote;

import com.estimote.sdk.cloud.model.Color;

public class EstimoteCloudBeaconDetails {

    private String beaconName;
    private Color beaconColor;
    private BeaconID beaconID;


    public EstimoteCloudBeaconDetails(String beaconName, Color beaconColor, BeaconID beaconID) {
        this.beaconName = beaconName;
        this.beaconColor = beaconColor;
        this.beaconID = beaconID;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public Color getBeaconColor() {
        return beaconColor;
    }

    public BeaconID getBeaconID() { return beaconID; }

    @Override
    public String toString() {
        return "[beaconName: " + getBeaconName() + ", beaconColor: " + getBeaconColor() + "]";
    }
}
