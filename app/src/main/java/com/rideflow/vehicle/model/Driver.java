package com.rideflow.vehicle.model;

import com.rideflow.vehicle.Model;

/**
 * Created by stephen on 1/26/18.
 */

public class Driver extends Model {

    public String id;

    public Driver() {}

    static public String url() {
        return "/users";
    }

}
