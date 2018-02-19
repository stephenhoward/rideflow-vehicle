package com.rideflow.vehicle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rideflow.vehicle.R;
import com.rideflow.vehicle.Models;
import com.rideflow.vehicle.RouteManager;
import com.rideflow.vehicle.model.Driver;
import com.rideflow.vehicle.model.Route;
import com.rideflow.vehicle.model.RouteSession;

public class StartSession extends BaseActivity implements logoutCountdown.OnTimeoutListener {

    public RouteSession route_session;
    private logoutCountdown countdown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);

        Models.getAll(Route.class,
                (routes) -> {
                },
                (error) -> {
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();

        RouteManager application = (RouteManager) getApplication();
        Driver current_user      = application.getUser();

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.driverName);
        textView.setText( current_user.name );

        countdown = (logoutCountdown) getFragmentManager().findFragmentById(R.id.logoutCountdown);
        countdown.startCountdown();
    }

    public void onLogoutTimeout() {
        logOut(findViewById(R.id.cancelSessionButton));
    }

    public void logOut(View view) {
        countdown.endCountdown();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startSession(View view) {
        countdown.endCountdown();

        RouteSession.beginSession(this::authSessionOk, this::authSessionFail );

    }

    private void authSessionOk( RouteSession session ) {

        endWaiting();
        gotoActivity(ManageSession.class);
    }
    private void authSessionFail( VolleyError error ) {

        endWaiting();
        showError( error.getMessage() );
    }

}
