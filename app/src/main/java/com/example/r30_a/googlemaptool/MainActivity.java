package com.example.r30_a.googlemaptool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_BaseMap;
    Button btn_CameraMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {

        btn_BaseMap = (Button)findViewById(R.id.btn_BaseMap);
        btn_BaseMap.setOnClickListener(this);
        btn_CameraMap = (Button)findViewById(R.id.btn_CameraMap);
        btn_CameraMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_BaseMap://基本樣式
                startActivity(new Intent(this,BaseMapActivity.class));
                break;
            case R.id.btn_CameraMap:
                startActivity(new Intent(this,CameraMapsActivity.class));
                break;
        }
    }
}
