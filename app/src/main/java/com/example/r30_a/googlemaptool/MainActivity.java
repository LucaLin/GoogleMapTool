package com.example.r30_a.googlemaptool;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.util.ArraySet;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.r30_a.googlemaptool.view.MyFloatButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener ,AdapterView.OnItemSelectedListener{

    private GoogleMap mMap;
    private LocationManager locationManager;
    private MyFloatButton floatButton;
    private double gpsLat, gpsLng;
    private Spinner mapSpinner;

    public static final int LOCATION_UPDATE_MIN_DISTANCE = 2000;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getCurrentLocation();

    }

    private void init() {
        floatButton = (MyFloatButton) findViewById(R.id.fab);
        floatButton.setOnClickListener(this);
        mapSpinner = (Spinner)findViewById(R.id.mapSpinner);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ArrayAdapter adapter =ArrayAdapter.createFromResource(this,R.array.mapSpinnerList,
                android.R.layout.simple_dropdown_item_1line);
        mapSpinner.setAdapter(adapter);
        mapSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();

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

            gpsLat = location.getLatitude();
            gpsLng = location.getLongitude();
            LatLng gps = new LatLng(gpsLat,gpsLng);

            mMap.addMarker(new MarkerOptions()
                    .position(gps)
                    .title("you are here"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 15));
        }


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
//            if (isGPSEnable) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                getCurrentLocation();
                break;

        }
    }

//取得周遭附設愛心停車位的資料
    public void getParkingData() {
        //來源網址：台北市政府資料開放平台
        String dataSrc = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=69be3cc8-5099-4cc1-8c0d-94098188bb71";
        JsonObjectRequest objectRequest = new JsonObjectRequest(dataSrc,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ResponseError:", error.toString());
            }
        });
        Volley.newRequestQueue(this).add(objectRequest);


    }

    public void parseJson(JSONObject jsonObject) {
        try {
            JSONArray data = jsonObject.getJSONObject("result").getJSONArray("results");

            for (int i = 0; i < 100; i++) {

                JSONObject object = data.getJSONObject(i);
                String address = object.getString("PKROAD");//取得地址

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                if (!TextUtils.isEmpty(address)) {
                    //地址轉成經緯度
                    List<Address> location = geocoder.getFromLocationName(address, 1);
                    if (location != null && location.size()>0) {
                        double lat = location.get(0).getLatitude();
                        double lng = location.get(0).getLongitude();

                            LatLng latLng = new LatLng(lat, lng);
                            //加入地標
                            mMap.addMarker(new MarkerOptions().position(latLng)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_wheelchair_24))
                                    .title(address));
                        }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 1://愛心停車格
                getParkingData();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
