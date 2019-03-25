package com.example.r30_a.googlemaptool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.r30_a.googlemaptool.adapter.MyLocationAdapter;
import com.example.r30_a.googlemaptool.data.LocationData;
import com.google.android.gms.maps.model.LatLng;

public class LocationListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationData[] datas;

    private LinearLayoutManager linearLayoutManager ;
    private MyLocationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        init();


    }

    private void init() {

        datas = new LocationData[]{
                new LocationData(R.string.nangangStation,R.string.nangangAdd,new LatLng(25.0531893,121.6070639)),
                new LocationData(R.string.taipeiStation,R.string.taipeiAdd,new LatLng(25.0477019,121.5173734)),
                new LocationData(R.string.banciaoStation,R.string.banCiaoAdd,new LatLng(25.0143985,121.4634921)),
                new LocationData(R.string.taoyuanStation,R.string.taoYuanAdd,new LatLng(25.0130935,121.2152170)),
                new LocationData(R.string.hisnZhuStation,R.string.hsinZhuAdd,new LatLng(24.8080601,121.0404152)),
                new LocationData(R.string.miaoLiStation,R.string.miaoLiAdd,new LatLng(24.6057663,120.82557279)),
                new LocationData(R.string.taiChungStation,R.string.taiChungAdd,new LatLng(24.1117411,120.6157315)),
                new LocationData(R.string.changHuaStaion,R.string.changHuaAdd,new LatLng(23.8736329,120.5745022)),
                new LocationData(R.string.yunLingStation,R.string.yunLingAdd,new LatLng(23.7363314,120.4164943)),
                new LocationData(R.string.chiaYiStation,R.string.chiaYiAdd,new LatLng(23.4591718,120.32296969)),
                new LocationData(R.string.taiNangStation,R.string.taiNangAdd,new LatLng(22.924127,120.2863897)),
                new LocationData(R.string.ZuoYingStation,R.string.ZhoYingAdd,new LatLng(22.6871352,120.3076584))
        };

        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new MyLocationAdapter(this,datas);

        recyclerView = (RecyclerView)findViewById(R.id.mapRecyclerView);
        recyclerView.setHasFixedSize(true);//確保每個進來的item是同一個size，不隨著item本身變化
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setRecyclerListener(adapter.recyclerListener);

    }
}
