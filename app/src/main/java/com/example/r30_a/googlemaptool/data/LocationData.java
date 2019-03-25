package com.example.r30_a.googlemaptool.data;

import com.google.android.gms.maps.model.LatLng;

public class LocationData {

    private int nameId;//名稱
    private int addressId;//地址
    private LatLng latLng;//經緯度位置

    public LocationData(int nameId,int addressId, LatLng latLng) {
        this.nameId = nameId;
        this.addressId = addressId;
        this.latLng = latLng;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
