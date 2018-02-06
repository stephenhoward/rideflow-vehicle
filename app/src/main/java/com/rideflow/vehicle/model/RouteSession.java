package com.rideflow.vehicle.model;

import android.location.Location;
import android.util.Log;

import com.android.volley.VolleyError;
import com.rideflow.vehicle.RideFlowAPI;
import com.rideflow.vehicle.RideFlowModel;

import java.time.OffsetDateTime;
import java.util.function.Consumer;

/**
 * Created by stephen on 1/26/18.
 */

public class RouteSession extends RideFlowModel {

    public String         id;
    public Location       location;
    public Route route;
    public Driver driver;
    public OffsetDateTime session_start;
    public OffsetDateTime session_end;
    public Vehicle vehicle;

    static public void beginSession(Consumer<RouteSession> callback, Consumer<VolleyError> errorCallback) {

        RouteSession session = new RouteSession();
        session.session_start = OffsetDateTime.now();
        Log.i( "API", "create a session");

        RideFlowAPI.getInstance().post( "/sessions", session,
                (json)  -> { session.updateFromJSON(json); callback.accept(session); },
                (error) -> { errorCallback.accept(error); }
        );
    }

    public void updateStatus(Consumer<RouteSession> callback, Consumer<VolleyError> errorCallback) {

        RideFlowAPI.getInstance().post( "/sessions/" + this.id, this,
                (json)  -> { this.updateFromJSON(json); callback.accept(this); },
                (error) -> { errorCallback.accept(error); }
        );
    }

    public void endSession( Consumer<RouteSession> callback, Consumer<VolleyError> errorCallback ) {

        this.session_end = OffsetDateTime.now();
        updateStatus(callback,errorCallback);
    }

}
