package com.example.r30_a.googlemaptool.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r30_a.googlemaptool.R;
import com.example.r30_a.googlemaptool.adapter.MyLocationAdapter;
import com.example.r30_a.googlemaptool.data.LocationData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class LocationListFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList localAddList;
    private ArrayList localNameList;
    private ArrayList list;

    private LinearLayoutManager linearLayoutManager;
    private MyLocationAdapter adapter;

    LocationData[] datas;

    public LocationListFragment() {
        // Required empty public constructor
    }


    public static LocationListFragment newInstance(Context context, LocationData[] datas) {
        LocationListFragment fragment = new LocationListFragment();

        ArrayList addList = new ArrayList<>();
        ArrayList nameList = new ArrayList();

        for (int i = 0; i < datas.length; i++) {
            addList.add(context.getResources().getString(datas[i].getAddressId()));
            nameList.add(context.getResources().getString(datas[i].getNameId()));
        }



        Bundle args = new Bundle();

        args.putCharSequenceArrayList("addList", addList);
        args.putCharSequenceArrayList("nameList", nameList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            list = new ArrayList();
            localAddList = new ArrayList();
            localAddList = getArguments().getIntegerArrayList("addList");
            localNameList = new ArrayList();
            localNameList = getArguments().getIntegerArrayList("nameList");
        }
            setData();

    }

    private void setData() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        adapter = new MyLocationAdapter(view.getContext(),datas);


        recyclerView = view.findViewById(R.id.mapRecyclerView);
        recyclerView.setHasFixedSize(true);//確保每個進來的item是同一個size，不隨著item本身變化
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setRecyclerListener(adapter.recyclerListener);

        return view;
    }


}
