package com.rideflow.vehicle.model;

import android.location.Location;

import com.android.volley.VolleyError;
import com.rideflow.vehicle.RideFlowAPI;
import com.rideflow.vehicle.RideFlowModel;

import java.util.function.Consumer;

/**
 * Created by stephen on 2/4/18.
 */

public class SessionStop extends RideFlowModel {

        Location       location;
        RouteSession session;
        Integer        pick_ups;
        Integer        drop_offs;

        public void recordStop( Consumer<SessionStop> callback, Consumer<VolleyError> errorCallback) {

                RideFlowAPI.getInstance().post( "/sessions/" + this.session.id + "/stop", this,
                        (json)  -> { this.updateFromJSON(json); callback.accept(this); },
                        (error) -> { errorCallback.accept(error); }
                );
        }
}
