package com.rideflow.vehicle.model;

import com.rideflow.vehicle.Model;

/**
 * Created by stephen on 1/26/18.
 */

public class Route extends Model {

    public String id;
    public String name;

    public Route() {}

    static public String url() {
        return "/routes";
    }
}
