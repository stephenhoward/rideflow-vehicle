package com.rideflow.vehicle.model;

import com.android.volley.VolleyError;
import com.rideflow.vehicle.RideFlowModel;

import java.util.function.Consumer;

/**
 * Created by stephen on 1/26/18.
 */

public class Route extends RideFlowModel {
    String id;
    String name;

    public void getRoutes(Consumer<Route[]> callback, Consumer<VolleyError> errorCallback ) {
        String url = "/routes";
    }

}
