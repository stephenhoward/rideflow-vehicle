package com.rideflow.vehicle;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by stephen on 2/6/18.
 */

public class RideFlowModel {

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

    public RideFlowModel newFromJSON(JSONObject jso) {

        RideFlowModel model = null;

        try {
            model = this.getClass().newInstance();
            model.setFromJSON(jso);
        }
        catch( InstantiationException e ) {
            // couldn't create new instance
        }
        catch( IllegalAccessException e ) {
            // couldn't access something'
        }

        return model;
    }

    public void updateFromJSON(JSONObject jso) {

        this.setFromJSON(jso);

    }

    private void setFromJSON(JSONObject jso) {

        JSONArray names = jso.names();

        for ( int i=0; i < names.length(); i++ ) {
            try {
                String name  = names.getString(i);
                Field  field = this.getClass().getField(name);

                field.set(this,jso.get(name));
            }
            catch (NoSuchFieldException e) {
                // Skip it;
            }
            catch (IllegalAccessException e ) {
                // Not allowed to set the field?
            }
            catch (JSONException e){
                // Indexing error?;
            }
        }
    }
}
