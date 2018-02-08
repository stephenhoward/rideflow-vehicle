package com.rideflow.vehicle.model;

import android.util.Log;

import com.android.volley.VolleyError;
import com.rideflow.vehicle.Model;

import java.time.OffsetDateTime;
import java.util.function.Consumer;

/**
 * Created by stephen on 1/26/18.
 */

public class RouteSession extends Model {

    public String         id;
    public double         latitude;
    public double         longitude;
    public String         route_id;
    public String         driver_id;
    public String         vehicle_id;
    public OffsetDateTime session_start;
    public OffsetDateTime session_end;

    Route route;
    Driver driver;
    Vehicle vehicle;

    public RouteSession() {}

    static public String url() {
        return "/sessions";
    }

    static public void beginSession(Consumer<RouteSession> callback, Consumer<VolleyError> errorCallback) {

        RouteSession session = new RouteSession();
        session.session_start = OffsetDateTime.now();
        Log.i( "API", "create a session");

        session.save(callback, errorCallback);
    }

    public void updateStatus(Consumer<RouteSession> callback, Consumer<VolleyError> errorCallback) {

        this.save(callback, errorCallback);
    }

    public void endSession( Consumer<RouteSession> callback, Consumer<VolleyError> errorCallback ) {

        this.session_end = OffsetDateTime.now();
        this.save(callback,errorCallback);
    }

}
