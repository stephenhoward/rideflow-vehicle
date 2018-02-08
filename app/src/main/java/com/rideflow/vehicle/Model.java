package com.rideflow.vehicle;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * Created by stephen on 2/6/18.
 */

public abstract class Model implements hasID {

    static public String url() {
        return "";
    }

    public JSONObject toJSON() {
        JSONObject jso = null;

        Gson gson = new Gson();
        String json = gson.toJson(this);
        try {
            jso = new JSONObject(json);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return jso;
    }

    public void updateFromJSON(JSONObject jso) {

        JSONArray names = jso.names();

        for ( int i=0; i < names.length(); i++ ) {
            try {
                String name  = names.getString(i);
                Field  field = this.getClass().getField(name);

                field.set(this,jso.get(name));
            }
            catch (Exception e) {
                // TODO Exception
            }
        }
    }

    public <M extends Model> void save(Consumer<M> callback, Consumer<VolleyError> errorCallback) {

        String url = url();

        if ( this.id != null && this.id != "" ) {
            url += "/" + this.id;
        }

        API.getInstance().post( url, this,
                (json)  -> { this.updateFromJSON(json); callback.accept((M) this); },
                (error) -> { errorCallback.accept(error); }
        );
    }

}
