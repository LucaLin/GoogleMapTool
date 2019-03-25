package com.example.r30_a.googlemaptool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_BaseMap;
    Button btn_CameraMap;
    Button btn_StyleMap;
    Button btn_HighSpeedRailMap;


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
        btn_StyleMap = (Button)findViewById(R.id.btn_StyleMap);
        btn_StyleMap.setOnClickListener(this);
        btn_HighSpeedRailMap = (Button)findViewById(R.id.btn_LocationListMap);
        btn_HighSpeedRailMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_BaseMap://基本樣式
                startActivity(new Intent(this,BaseMapActivity.class));
                break;
            case R.id.btn_CameraMap://基本鏡頭地圖
                startActivity(new Intent(this,CameraMapActivity.class));
                break;
            case R.id.btn_StyleMap://主題地圖
                startActivity(new Intent(this,StyleMapActivity.class));
                break;
            case R.id.btn_LocationListMap://高鐵地址分布地圖
                startActivity(new Intent(this,LocationListActivity.class));
                break;
        }
    }
}
