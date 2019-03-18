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
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.r30_a.googlemaptool.data.DragPosData;
import com.example.r30_a.googlemaptool.data.Result;
import com.example.r30_a.googlemaptool.data.SpeedCamera;
import com.example.r30_a.googlemaptool.view.MyCustomInfoWindow;
import com.example.r30_a.googlemaptool.view.MyCustomSearchView;
import com.example.r30_a.googlemaptool.view.MyFloatButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private MyFloatButton floatButton;
    private double gpsLat, gpsLng;
    private Spinner mapSpinner;
    private List<SpeedCamera> speedCamList = new ArrayList<>();
    private List<LinkedTreeMap<String, String>> dragPosList = new ArrayList<>();
    Geocoder geocoder;

    //搜尋欄
    private MyCustomSearchView searchView;

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
        AndroidNetworking.initialize(getApplicationContext());
        geocoder = new Geocoder(this, Locale.getDefault());
        floatButton = (MyFloatButton) findViewById(R.id.fab);
        floatButton.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.mapSpinnerList,
                android.R.layout.simple_dropdown_item_1line);
        mapSpinner = (Spinner) findViewById(R.id.mapSpinner);
        mapSpinner.setAdapter(adapter);
        mapSpinner.setOnItemSelectedListener(this);

        searchView = new MyCustomSearchView(this);
        searchView.edtInput = (EditText) findViewById(R.id.search_input_text);
        searchView.btnStartSearch = (View) findViewById(R.id.start_search_button);
        searchView.btnStartSearch.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();//取得當前定位

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
            LatLng gps = new LatLng(gpsLat, gpsLng);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                mMap.clear();
                getCurrentLocation();
                break;
            case R.id.start_search_button:
                String address = searchView.edtInput.getText().toString();
                LatLng latLng = getAddToLatLng(address);
                if (latLng != null) {
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng)
                            .title(address));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
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
            mMap.clear();
            for (int i = 0; i < 100; i++) {

                JSONObject object = data.getJSONObject(i);
                String address = object.getString("PKROAD");//取得地址


                if (!TextUtils.isEmpty(address)) {
                    //地址轉成經緯度
                    LatLng latLng = getAddToLatLng(address);

                    //加入地標
                    if (latLng != null) {
                        mMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_wheelchair_24))
                                .title(address));
                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //台北市拖吊場位置分布資料
    public void getDragPos() {
//        String dataSrc = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=24045907-b7c3-4351-b0b8-b93a54b55367";
//        JsonObjectRequest objectRequest = new JsonObjectRequest(dataSrc,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        parseJson(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("ResponseError:", error.toString());
//            }
//        });
//        Volley.newRequestQueue(this).add(objectRequest);
        AndroidNetworking.get("https://data.taipei/opendata/datalist/apiAccess")
                .addQueryParameter("scope", "resourceAquire")
                .addQueryParameter("rid", "24045907-b7c3-4351-b0b8-b93a54b55367")
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<Result<DragPosData>>() {
                }, new ParsedRequestListener<Result<DragPosData>>() {
                    @Override
                    public void onResponse(Result<DragPosData> response) {
                        dragPosList = response.getResult().getResults();
                        mMap.clear();

                        for (int i = 0; i < dragPosList.size(); i++) {
                            DragPosData data = new DragPosData();

                            String address = dragPosList.get(i).get(data.getAddress());
                            String phoneNum = dragPosList.get(i).get(data.getPhoneNumber());
                            if (!TextUtils.isEmpty(address)) {
                                LatLng latLng = getAddToLatLng(address);
                                if (latLng != null) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(latLng)
                                            .title(address)
                                            .snippet(phoneNum)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_dragpos)));
                                    mMap.setInfoWindowAdapter(new MyCustomInfoWindow(getLayoutInflater()));
                                }

                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    //------取得北市各路段測速照相區域與速限標示
    public void getSpeedCamData() {
        AndroidNetworking.get("https://data.taipei/opendata/datalist/apiAccess")
                .addQueryParameter("scope", "resourceAquire")
                .addQueryParameter("rid", "5012e8ba-5ace-4821-8482-ee07c147fd0a")
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<Result<SpeedCamera>>() {
                }, new ParsedRequestListener<Result<SpeedCamera>>() {

                    @Override
                    public void onResponse(Result<SpeedCamera> response) {
                        speedCamList = response.getResult().getResults();
                        mMap.clear();

                        for (int i = 0; i < speedCamList.size(); i++) {
                            SpeedCamera data = speedCamList.get(i);
                            String address = data.getArea() + data.getRoad() + data.getLocation();
                            if (!TextUtils.isEmpty(address)) {
                                LatLng latLng = getAddToLatLng(address);
                                if (latLng != null) {
                                    switch (data.getSpeed_limit()) {
                                        case "40":
                                            mMap.addMarker(new MarkerOptions().position(latLng)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.speed_limit_40))
                                                    .title(address));
                                            break;
                                        case "50":
                                            mMap.addMarker(new MarkerOptions().position(latLng)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.speed_limit_50))
                                                    .title(address));
                                            break;
                                        case "60":
                                            mMap.addMarker(new MarkerOptions().position(latLng)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.speed_limit_60))
                                                    .title(address));
                                            break;

                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1://愛心停車格
                getParkingData();
                break;
            case 2://測速照相
                getSpeedCamData();
                break;
            case 3://拖吊場位置
                getDragPos();
                ;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //將地址轉成經緯度坐標
    public LatLng getAddToLatLng(String address) {

        List<Address> location = null;
        LatLng latLng = null;
        try {
            location = geocoder.getFromLocationName(address, 1);
            if (location != null && location.size() > 0) {
                double lat = location.get(0).getLatitude();
                double lng = location.get(0).getLongitude();
                latLng = new LatLng(lat, lng);
                return latLng;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return latLng;
    }
}
