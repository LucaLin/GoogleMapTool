package com.example.r30_a.googlemaptool;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CameraMapActivity extends FragmentActivity implements OnMapReadyCallback
        ,View.OnClickListener {

    private GoogleMap mMap;
    private Button btnUp, btnDown, btnLeft, btnRight;
    private Button btnZoomIn, btnZoomOut;
    private Button btnTiltUp, btnTiltDown;

    private SeekBar mDurationBar;
    private Switch mAniMateSwitch;

    public static final int Move_PX =100;
    public static final int Tilt_Radius = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_maps);
        init();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void init() {
        btnUp = (Button)findViewById(R.id.btn_up);
        btnUp.setOnClickListener(this);
        btnDown = (Button)findViewById(R.id.btn_down);
        btnDown.setOnClickListener(this);
        btnLeft = (Button)findViewById(R.id.btn_left);
        btnLeft.setOnClickListener(this);
        btnRight = (Button)findViewById(R.id.btn_right);
        btnRight.setOnClickListener(this);

        btnZoomIn = (Button)findViewById(R.id.btn_zoomIn);
        btnZoomIn.setOnClickListener(this);
        btnZoomOut = (Button)findViewById(R.id.btn_zoomOut);
        btnZoomOut.setOnClickListener(this);

        btnTiltUp = (Button)findViewById(R.id.btn_tiltUp);
        btnTiltUp.setOnClickListener(this);
        btnTiltDown = (Button)findViewById(R.id.btn_tiltDown);
        btnTiltDown.setOnClickListener(this);

        mDurationBar = (SeekBar)findViewById(R.id.durationBar);
        mAniMateSwitch = (Switch)findViewById(R.id.animateSwitch);
        mAniMateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateAnimateStatus();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        updateAnimateStatus();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_up:changeCamera(CameraUpdateFactory.scrollBy(0,-Move_PX),null);break;
            case R.id.btn_down:changeCamera(CameraUpdateFactory.scrollBy(0,Move_PX),null);break;
            case R.id.btn_left:changeCamera(CameraUpdateFactory.scrollBy(-Move_PX,0),null);break;
            case R.id.btn_right:changeCamera(CameraUpdateFactory.scrollBy(Move_PX,0),null);break;

            case R.id.btn_zoomIn:changeCamera(CameraUpdateFactory.zoomIn(),null);break;
            case R.id.btn_zoomOut:changeCamera(CameraUpdateFactory.zoomOut(),null);break;

            case R.id.btn_tiltUp:tiltMap(Tilt_Radius);break;
            case R.id.btn_tiltDown:tiltMap(-Tilt_Radius);break;




        }
    }
    public void tiltMap(int radius){

        CameraPosition position = mMap.getCameraPosition();
        float nowTilt = position.tilt;
        float newTilt = nowTilt + (radius);

        if(radius >0){
            newTilt = newTilt > 90? 90:newTilt;
        }else {
            newTilt = newTilt < 0 ? 0: newTilt;
        }
        CameraPosition cameraPosition = new CameraPosition.Builder(position).tilt(newTilt).build();
        changeCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),null);

    };

    public void updateAnimateStatus(){
        if(mAniMateSwitch.isChecked()){
            mDurationBar.setEnabled(true);
        }else {
            mDurationBar.setEnabled(false);
        }
    }

    public void changeCamera(CameraUpdate update, GoogleMap.CancelableCallback callback){

        if(mAniMateSwitch.isChecked()){
            int speed = mDurationBar.getProgress();
            mMap.animateCamera(update,Math.max(speed,1),callback);

        }else {
            mMap.moveCamera(update);
        }

    }
}
