package com.example.r30_a.googlemaptool.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r30_a.googlemaptool.R;
import com.example.r30_a.googlemaptool.data.LocationData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class LocationAddFragment extends Fragment  {

    ArrayList localAddList = new ArrayList();
    ArrayList latlngs = new ArrayList();
    private Context context;

    GoogleMap map;
    MapView mapView;
    UiSettings uiSettings;

    public LocationAddFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LocationAddFragment newInstance(Context context, LocationData[] datas) {
        LocationAddFragment fragment = new LocationAddFragment();
        Bundle args = new Bundle();

        ArrayList addList = new ArrayList<>();
        ArrayList latLngs = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            addList.add(context.getResources().getString(datas[i].getAddressId()));
            latLngs.add(datas[i].getLatLng());
        }
        args.putCharSequenceArrayList("addList", addList);
        args.putCharSequenceArrayList("latlng", latLngs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            latlngs = getArguments().getCharSequenceArrayList("latlng");
            localAddList = getArguments().getCharSequenceArrayList("addList");
        }
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_location_add, container, false);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(null);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                map = googleMap;

                for (int i = 0; i < latlngs.size(); i++) {
                    map.addMarker(new MarkerOptions().
                            position((LatLng) latlngs.get(i))
                            .title((String) localAddList.get(i)));
                }

                map.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng)latlngs.get(6),8f));
                uiSettings = map.getUiSettings();
                uiSettings.setAllGesturesEnabled(true);

            }
        });

//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                MapsInitializer.initialize(view.getContext().getApplicationContext());
//                map = googleMap;
//
//                for (int i = 0; i < latlngs.size(); i++) {
//                    map.addMarker(new MarkerOptions().
//                            position((LatLng) latlngs.get(i))
//                            .title((String) localAddList.get(i)));
//                }
//
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng)latlngs.get(5),3f));
//                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            }
//        });


        return view;
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        MapsInitializer.initialize(context.getApplicationContext());
////        map = googleMap;
//
//        for (int i = 0; i < latlngs.size(); i++) {
//            googleMap.addMarker(new MarkerOptions().
//                    position((LatLng) latlngs.get(i))
//                    .title((String) localAddList.get(i)));
//        }
////        map.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng)latlngs.get(5),15f));
////        map.moveCamera(CameraUpdateFactory.zoomTo(3f));
//    }
}
