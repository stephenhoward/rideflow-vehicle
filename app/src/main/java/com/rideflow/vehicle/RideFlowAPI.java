package com.rideflow.vehicle;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.function.Consumer;

/**
 * Created by stephen on 2/4/18.
 */

public class RideFlowAPI {

    private static RideFlowAPI INSTANCE = new RideFlowAPI();
    private static RequestQueue queue   = null;
    private static String baseUrl = "https://rideapi.ourtransit.com/v1";
    private static Context context = null;

    private RideFlowAPI() {};

    public static void setContext( Context ctx ) {
        context = ctx;
    }

    public static RideFlowAPI getInstance() {
        return INSTANCE;
    }

    private RequestQueue getQueue () {
        if ( queue == null ) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public <T extends RideFlowModel> void post(String url, T payload, Consumer<JSONObject> callback, Consumer<VolleyError> errorCallback) {

        JSONObject json = payload.toJSON();

        url = baseUrl + url;

        JsonObjectRequest jsr = new JsonObjectRequest( Request.Method.POST, url, json,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("API","got response");
                    callback.accept(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("API",error.toString() );
                    errorCallback.accept(error);
                }
            }
        );

        getQueue().add(jsr);
    }

}
