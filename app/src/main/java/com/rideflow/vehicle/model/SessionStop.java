package com.rideflow.vehicle.model;

import android.location.Location;

import com.android.volley.VolleyError;
import com.rideflow.vehicle.API;
import com.rideflow.vehicle.Model;

import java.util.function.Consumer;

/**
 * Created by stephen on 2/4/18.
 */

public class SessionStop extends Model {

        Location       location;
        RouteSession   session;
        Integer        pick_ups;
        Integer        drop_offs;

        static public String url() {
                return "/sessions/stops";
        }

        public void recordStop( Consumer<SessionStop> callback, Consumer<VolleyError> errorCallback) {

            API.getInstance().post( "/sessions/" + this.session.id + "/stops", this,
                (json)  -> { this.updateFromJSON(json); callback.accept(this); },
                (error) -> { errorCallback.accept(error); }
            );
        }
}
