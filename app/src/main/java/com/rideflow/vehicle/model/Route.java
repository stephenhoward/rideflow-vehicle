package com.rideflow.vehicle.model;

import com.rideflow.vehicle.Model;

public class Route extends Model {

    public String name;

    public Route() {}

    static public String url() {
        return "/routes";
    }
}
