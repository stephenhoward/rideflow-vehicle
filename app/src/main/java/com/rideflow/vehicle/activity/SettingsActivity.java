package com.rideflow.vehicle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rideflow.vehicle.Models;
import com.rideflow.vehicle.R;
import com.rideflow.vehicle.RouteManager;
import com.rideflow.vehicle.model.Route;
import com.rideflow.vehicle.model.Vehicle;

import java.util.List;


public class SettingsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    List<Vehicle> vehicles = null;
    Vehicle current_vehicle = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        startWaiting();
        Models.getAll(Vehicle.class, this::loadVehicles, (error) -> {
            endWaiting();
        });
    }

    private void loadVehicles(List<Vehicle> vs) {
        endWaiting();
        vehicles = vs;

        Spinner select_vehicle = findViewById(R.id.vehicle_selector);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, vehicles);

        select_vehicle.setAdapter(adapter);
        select_vehicle.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        current_vehicle = vehicles.get(i);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        current_vehicle = null;
    }

    public void saveSettings() {
        RouteManager application = (RouteManager) getApplication();
        application.setVehicle(current_vehicle);
    }
}
