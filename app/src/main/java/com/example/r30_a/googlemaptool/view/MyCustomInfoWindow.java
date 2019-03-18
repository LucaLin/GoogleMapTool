package com.example.r30_a.googlemaptool.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.r30_a.googlemaptool.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by R30-A on 2019/3/18.
 */

public class MyCustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater;
    public MyCustomInfoWindow(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override//未點選前
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override//點選後的樣式
    public View getInfoContents(Marker marker) {
        View v = inflater.inflate(R.layout.custom_infowindow_layout,null);
        TextView txvTitle = v.findViewById(R.id.txvTitleName);
        TextView txvPhoneNum = v.findViewById(R.id.txvTitlePhoneNum);
        txvTitle.setText(marker.getTitle());
        txvPhoneNum.setText(marker.getSnippet());


        return v;
    }
}
