package com.rideflow.vehicle;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.VolleyError;
import com.rideflow.vehicle.model.Driver;
import com.rideflow.vehicle.model.RouteSession;
import com.rideflow.vehicle.model.Vehicle;


public class RouteManager extends Application {

    private RouteSession current_session  = null;
    private Driver       current_driver   = null;
    private Vehicle      vehicle          = null;
    public static final String PREFS_FILE = "VehiclePrefs";

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

    public Driver getUser() { return current_driver; }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle v) {
        if ( vehicle == null || ( v != null && v.id != vehicle.id ) ) {
            getOurPreferences().edit().putString("vehicle_id", v.id );
        }
        vehicle = v;
    }

    private void volleyError(VolleyError e) {
        Log.i("API", e.getMessage() );
    }

    @Override
    public void onCreate() {
        super.onCreate();

        API.setContext(this);

        String vehicle_id = getOurPreferences().getString("vehicle_id", null );

        if ( vehicle_id != null ) {
            Models.get(Vehicle.class, vehicle_id, this::setVehicle, this::volleyError );
        }

    }

    private SharedPreferences getOurPreferences() {
        return getSharedPreferences(PREFS_FILE,0);
    }

}
