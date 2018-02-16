package com.rideflow.vehicle;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class API {

    private static API INSTANCE = new API();
    private static RequestQueue queue   = null;
    private static String baseUrl = "https://rideapi.ourtransit.com/v1";
    private static Context context = null;
    private static String auth_key = null;
    private static String auth_id = null;

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

    public void authorize(String new_auth_id, Consumer<Boolean> callback, Consumer<Boolean> errorCallback) {

        HashMap<String,String> params = new HashMap();
        params.put("id", new_auth_id );

        doStringRequest( Request.Method.POST, "/auth", params,
            (response) -> {

                auth_key = response;
                callback.accept(true);
            },
            (e) -> {

                callback.accept(false);
            }
        );
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
        ) {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = super.getHeaders();

                addAuthHeader(headers);

                return headers;
            };
        };
        getQueue().add(jsr);
    }
    private void doStringRequest(int request_method, String url, Map<String,String> payload, Consumer<String> callback, Consumer<VolleyError> errorCallback ) {

        url = baseUrl + url;

        StringRequest str = new StringRequest( request_method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
        ) {
            @Override
            protected Map<String,String> getParams() {
                return payload;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = super.getHeaders();

                addAuthHeader(headers);

                return headers;
            };

        };
        getQueue().add(str);
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
        ) {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = super.getHeaders();

                addAuthHeader(headers);

                return headers;
            };
        };
        getQueue().add(jsr);
    }

    public void addAuthHeader(Map<String,String> headers) {
        if ( auth_key != null ) {
            headers.put("Authorization", "Bearer " + auth_key );
        }
    }

}
