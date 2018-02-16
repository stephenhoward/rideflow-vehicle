package com.rideflow.vehicle;

import android.app.Application;

import com.rideflow.vehicle.model.RouteSession;


public class RouteManager extends Application {

    private RouteSession current_session = null;

    public RouteSession setSession(RouteSession session) {
        current_session = session;

        return current_session;
    }
    public RouteSession getSession() {
        return current_session;
    }

    public void clearSession() {
        current_session = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        API.setContext(this);
    }
}
