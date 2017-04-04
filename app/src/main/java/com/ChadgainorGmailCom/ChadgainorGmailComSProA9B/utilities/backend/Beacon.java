package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bhatia on 4/4/2017.
 */

public class Beacon {
    @SerializedName("_id") private DataID beaconID;
    @SerializedName("estimote_beacon_id") private EstimoteBeaconID estimoteBeaconID;
    private String name;
    private String address;
    private boolean active;
    @SerializedName("detect_motion") private boolean detectMotion;
    private String description;

    public Beacon() { /*no-args constructor*/ }

    public Beacon(DataID beaconID, EstimoteBeaconID estimoteBeaconID, String name, String address, boolean active, boolean detectMotion, String description) {
        this.beaconID = beaconID;
        this.estimoteBeaconID = estimoteBeaconID;
        this.name = name;
        this.address = address;
        this.active = active;
        this.detectMotion = detectMotion;
        this.description = description;
    }

    public DataID getBeaconID() {
        return beaconID;
    }

    public EstimoteBeaconID getEstimoteBeaconID() {
        return estimoteBeaconID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isDetectMotion() {
        return detectMotion;
    }

    public String getDescription() {
        return description;
    }

    public class EstimoteBeaconID
    {
        @SerializedName("proximity_uuid") private String proximityUuid;
        @SerializedName("major") private String major;
        @SerializedName("minor") private String minor;
        public EstimoteBeaconID() { /*no-args constructor*/ }
        public EstimoteBeaconID(String proximityUuid, String major, String minor) {
            this.proximityUuid = proximityUuid;
            this.major = major;
            this.minor = minor;
        }

        public String getProximityUuid() {
            return proximityUuid;
        }

        public String getMajor() {
            return major;
        }

        public String getMinor() {
            return minor;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof EstimoteBeaconID)
            {
                EstimoteBeaconID object = (EstimoteBeaconID) o;
                return this.getProximityUuid().equals(object.getProximityUuid()) &&
                        this.getMajor().equals(object.getMajor()) &&
                        this.getMinor().equals(object.getMinor());
            }
            else
                return false;
        }

        @Override
        public String toString() {
            return new StringBuilder("{\"estimote_beacon_id\":{")
                        .append("\"proximity_uuid\":\"" + proximityUuid + "\"")
                        .append(",\"major\":\"" + major + "\"")
                        .append(",\"minor\":\"" + minor + "\"")
                        .append("}}")
                    .toString();
        }
    }
}
