package com.rideflow.vehicle;

import com.android.volley.VolleyError;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class Models {

    static public <M extends Model> void get(Class<M> model_class, String id, Consumer<M> callback, Consumer<VolleyError> errorCallback ) {

        API.getInstance().get( M.url() + "/" + id,
                (json)  -> { callback.accept( newFromJSON(model_class,json) ); },
                (error) -> { errorCallback.accept(error); }
        );
    }

    static public <M extends Model> void getAll(Class<M> model_class, Consumer<List<M>> callback, Consumer<VolleyError> errorCallback ) {

        List<M> models = new ArrayList<M>();

        API.getInstance().getList(M.url(),
                (json) -> {

                    try {
                        for (int i = 0; i < json.length(); i++) {
                            models.add(Models.newFromJSON(model_class, json.getJSONObject(i)));
                        }
                    } catch (Exception e) {
                        // TODO Exception?
                    }

                    callback.accept(models);
                },
                (error) -> {
                    errorCallback.accept(error);
                }
        );
    }

    static public <T extends Model> T newFromJSON(Class<T> clazz, JSONObject jso) {

        T model = null;

        try {
            model = clazz.newInstance();
            model.updateFromJSON(jso);
        }
        catch( Exception e ) {
            // TODO Exception?
        }

        return model;
    }
}
