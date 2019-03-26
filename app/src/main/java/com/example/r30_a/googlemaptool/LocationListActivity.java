package com.example.r30_a.googlemaptool;

import android.app.Fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.r30_a.googlemaptool.adapter.MyLocationAdapter;
import com.example.r30_a.googlemaptool.data.LocationData;
import com.example.r30_a.googlemaptool.fragment.LocationAddFragment;
import com.example.r30_a.googlemaptool.fragment.LocationListFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentTransaction;

public class LocationListActivity extends AppCompatActivity
//        implements OnMapReadyCallback
{

    private RecyclerView recyclerView;
    private LocationData[] datas;

    private LinearLayoutManager linearLayoutManager;
    private MyLocationAdapter adapter;

    private GoogleMap map;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_title, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_locationAdd:
                LocationListFragment locationListFragment = LocationListFragment.newInstance(this,datas);
                FragmentTransaction transaction_List = getSupportFragmentManager().beginTransaction();
                transaction_List.replace(R.id.frameLayout,locationListFragment);
                transaction_List.commit();
                break;
            case R.id.menu_locationInTaiwan:
//                recyclerView.setVisibility(View.INVISIBLE);
                LocationAddFragment locationAddFragment = LocationAddFragment.newInstance(this, datas);
                FragmentTransaction transaction_add = getSupportFragmentManager().beginTransaction();
                transaction_add.replace(R.id.frameLayout, locationAddFragment);
                transaction_add.commit();
                break;
        }
        return true;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        init();

    }


    private void init() {


        datas = new LocationData[]{
                new LocationData(R.string.nangangStation, R.string.nangangAdd, new LatLng(25.0531893, 121.6070639)),
                new LocationData(R.string.taipeiStation, R.string.taipeiAdd, new LatLng(25.0477019, 121.5173734)),
                new LocationData(R.string.banciaoStation, R.string.banCiaoAdd, new LatLng(25.0143985, 121.4634921)),
                new LocationData(R.string.taoyuanStation, R.string.taoYuanAdd, new LatLng(25.0130935, 121.2152170)),
                new LocationData(R.string.hisnZhuStation, R.string.hsinZhuAdd, new LatLng(24.8080601, 121.0404152)),
                new LocationData(R.string.miaoLiStation, R.string.miaoLiAdd, new LatLng(24.6057663, 120.82557279)),
                new LocationData(R.string.taiChungStation, R.string.taiChungAdd, new LatLng(24.1117411, 120.6157315)),
                new LocationData(R.string.changHuaStaion, R.string.changHuaAdd, new LatLng(23.8736329, 120.5745022)),
                new LocationData(R.string.yunLingStation, R.string.yunLingAdd, new LatLng(23.7363314, 120.4164943)),
                new LocationData(R.string.chiaYiStation, R.string.chiaYiAdd, new LatLng(23.4591718, 120.32296969)),
                new LocationData(R.string.taiNangStation, R.string.taiNangAdd, new LatLng(22.924127, 120.2863897)),
                new LocationData(R.string.ZuoYingStation, R.string.ZhoYingAdd, new LatLng(22.6871352, 120.3076584))
        };

        LocationListFragment fragment = LocationListFragment.newInstance(this, datas);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }


}

