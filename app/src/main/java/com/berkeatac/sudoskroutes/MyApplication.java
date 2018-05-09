package com.berkeatac.sudoskroutes;

import android.app.Application;

import com.berkeatac.sudoskroutes.Model.RouteObject;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private List<RouteObject> routeList = new ArrayList<>();
    private String userName = "";
    private Boolean isLoggedIn = false;
    private Boolean firstTime = true;

    public void setRouteList(List<RouteObject> routeList) {
        this.routeList = routeList;
    }

    public List<RouteObject> getRouteList() {
        return routeList;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Boolean firstTime) {
        this.firstTime = firstTime;
    }




}
