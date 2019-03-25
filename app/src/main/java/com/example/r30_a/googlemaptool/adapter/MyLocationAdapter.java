package com.example.r30_a.googlemaptool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.r30_a.googlemaptool.R;
import com.example.r30_a.googlemaptool.data.LocationData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xml.sax.Locator;

public class MyLocationAdapter extends RecyclerView.Adapter<MyLocationAdapter.ViewHolder> {

    private LocationData[] locationData;
    private Context context;

    public MyLocationAdapter(Context context, LocationData[] locationData) {
        this.context = context;
        this.locationData = locationData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.locationlist_layout, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder == null) return;
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return locationData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        private MapView mapView;
        private TextView txvLocationTitle;
        private TextView txvLocationAdd;
        private GoogleMap map;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            txvLocationTitle = view.findViewById(R.id.txvLocationTitle);
            txvLocationAdd = (view.findViewById(R.id.txvLocationAdd));
            mapView = view.findViewById(R.id.locationMapView);


            if (mapView != null) {
                mapView.onCreate(null);
                mapView.getMapAsync(this);
            }

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(context.getApplicationContext());
            map = googleMap;
            setLocation();
        }

        private void setLocation() {

            if (map == null) return;

            LocationData data = (LocationData) mapView.getTag();
            if (data == null) return;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.getLatLng(), 15f));
            map.addMarker(new MarkerOptions().position(data.getLatLng()));

            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        private void bindView(int pos) {
            LocationData data = locationData[pos];

            view.setTag(this);
            mapView.setTag(data);
            setLocation();
            txvLocationTitle.setText(context.getResources().getText(data.getNameId()));
            txvLocationAdd.setText(context.getResources().getText(data.getAddressId()));

        }
    }


    public RecyclerView.RecyclerListener recyclerListener = new RecyclerView.RecyclerListener() {
        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            MyLocationAdapter.ViewHolder viewHolder = (MyLocationAdapter.ViewHolder) holder;

            if (viewHolder != null && viewHolder.map != null) {
                viewHolder.map.clear();
                viewHolder.map.setMapType(GoogleMap.MAP_TYPE_NONE);
            }
        }
    };

}

