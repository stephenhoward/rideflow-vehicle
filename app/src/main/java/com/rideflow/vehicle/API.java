package com.rideflow.vehicle;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.function.Consumer;

/**
 * Created by stephen on 2/4/18.
 */

public class API {

    private static API INSTANCE = new API();
    private static RequestQueue queue   = null;
    private static String baseUrl = "https://rideapi.ourtransit.com/v1";
    private static Context context = null;

    private API() {};

    public static void setContext( Context ctx ) {
        context = ctx;
    }

    public static API getInstance() {
        return INSTANCE;
    }

    private RequestQueue getQueue () {
        if ( queue == null ) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public void get(String url, Consumer<JSONObject> callback, Consumer<VolleyError> errorCallback) {

        doObjectRequest( Request.Method.GET, url, null, callback, errorCallback );
    }

    public <M extends Model> void post(String url, M payload, Consumer<JSONObject> callback, Consumer<VolleyError> errorCallback) {

        JSONObject json = payload.toJSON();

        doObjectRequest( Request.Method.POST, url, json, callback, errorCallback );
    }

    private void doObjectRequest(int request_method, String url, JSONObject payload, Consumer<JSONObject> callback, Consumer<VolleyError> errorCallback ) {

        url = baseUrl + url;

        JsonObjectRequest jsr = new JsonObjectRequest( request_method, url, payload,
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

    public void getList(String url, Consumer<JSONArray> callback, Consumer<VolleyError> errorCallback) {

        url = baseUrl + url;

        JsonArrayRequest jsr = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
