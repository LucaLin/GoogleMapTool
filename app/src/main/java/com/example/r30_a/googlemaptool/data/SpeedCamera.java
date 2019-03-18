package com.example.r30_a.googlemaptool.data;

/**
 * Created by R30-A on 2019/3/15.
 */

public class SpeedCamera {

    private String _id = "_id";
    private String no = "no";
    private String functions = "functions";//監測功能
    private String road = "road";//路名
    private String location = "location";//地點
    private String area = "area";//區域
    private String direction = "direction";//方向
    private String speed_limit = "speed_limit";//速限

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeed_limit() {
        return speed_limit;
    }

    public void setSpeed_limit(String speed_limit) {
        this.speed_limit = speed_limit;
    }
}
