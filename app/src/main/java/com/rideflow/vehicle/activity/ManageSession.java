package com.rideflow.vehicle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rideflow.vehicle.R;

public class ManageSession extends Activity implements logoutCountdown.OnTimeoutListener {

    private logoutCountdown countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_session);

        countdown = (logoutCountdown) getFragmentManager().findFragmentById(R.id.logoutCountdown);
        countdown.startCountdown();
    }
    public void onLogoutTimeout() {
        endSession(findViewById(R.id.cancelSessionButton));
    }

    public void toggleSession( View view ) {
        countdown.resetCountdown();
    }
    public void endSession( View view ) {
        countdown.endCountdown();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
