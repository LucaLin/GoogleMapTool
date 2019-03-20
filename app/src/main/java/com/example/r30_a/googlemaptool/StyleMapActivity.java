package com.example.r30_a.googlemaptool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.r30_a.googlemaptool.BaseMapActivity.LOCATION_UPDATE_MIN_DISTANCE;
import static com.example.r30_a.googlemaptool.BaseMapActivity.LOCATION_UPDATE_MIN_TIME;

public class StyleMapActivity extends FragmentActivity implements OnMapReadyCallback {

//    地圖樣式來源：https://snazzymaps.com/explore?sort=popular


    private GoogleMap mMap;
    private LocationManager locationManager;
    private RadioGroup mapGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_map);
        init();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getCurrentLocation();
    }

    private void init() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mapGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_default:
                        mMap.setMapStyle(null);
                        break;
                    case R.id.rb_retro://復古
                        setMapStyle(R.raw.mapstyle_retro);
                        break;
                    case R.id.rb_night://夜景
                        setMapStyle(R.raw.mapstyle_night);
                        break;
                    case R.id.rb_gray://灰景
                        setMapStyle(R.raw.mapstyle_grayscale);
                        break;
                    case R.id.rb_aubergine://茄子風
                        setMapStyle(R.raw.aubergine);
                        break;
                    case R.id.rb_megazineMap://都會雜誌
                        setMapStyle(R.raw.magazinemap);
                        break;
                    case R.id.rb_goldenAge://黃金年代
                        setMapStyle(R.raw.goldenage);
                        break;
                    case R.id.rb_blackAndWhite://簡約黑白
                        setMapStyle(R.raw.blackwhite);
                        break;
                    case R.id.rb_iceAge://冰雪奇緣
                        setMapStyle(R.raw.ice_age);
                        break;
                    case R.id.rb_modern://摩登時代
                        setMapStyle(R.raw.modern);
                        break;
                    case R.id.rb_redAlert://紅色警戒
                        setMapStyle(R.raw.redalert);
                        break;

                }
            }
        });
    }

    private void setMapStyle(int mapstyle) {
        MapStyleOptions options = MapStyleOptions.loadRawResourceStyle(this, mapstyle);
        mMap.setMapStyle(options);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    //取得當前位置
    private void getCurrentLocation() {

        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetWorkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            if (isNetWorkEnable) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE,
                        locationListener);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }
//            if (isGPSEnable) {這個會不準= =
//
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE,
//                        locationListener);
//                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            }

        }
        if (location != null) {
            drawMarker(location);
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                drawMarker(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void drawMarker(Location location) {
        if (mMap != null) {
            mMap.clear();
            double gpsLat = location.getLatitude();
            double gpsLng = location.getLongitude();
            LatLng gps = new LatLng(gpsLat, gpsLng);

            mMap.addMarker(new MarkerOptions()
                    .position(gps)
                    .title("you are here"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 15));
        }

    }
}
