package com.berkeatac.sudoskroutes;

import android.app.Application;

import com.berkeatac.sudoskroutes.Model.RouteObject;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private List<RouteObject> routeList = new ArrayList<>();

    public void setRouteList(List<RouteObject> routeList) {
        this.routeList = routeList;
    }

    public List<RouteObject> getRouteList() {
        return routeList;
    }

    private String userName = null;

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }
}
