package com.rideflow.vehicle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rideflow.stephen.rideflowvehicle.R;
import com.rideflow.vehicle.model.RouteSession;

/**
 * Skeleton of an Android Things activity.
 *
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 *
 * <pre>`PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
`</pre> *
 *
 *
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see [https://github.com/androidthings/contrib-drivers.readme](https://github.com/androidthings/contrib-drivers.readme)
 */

public class StartSession extends Activity implements logoutCountdown.OnTimeoutListener {

    public RouteSession route_session;
    private logoutCountdown countdown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.DRIVER_ID);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.driverName);
        textView.setText(message);

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

        Intent intent = new Intent(this, ManageSession.class);
        startActivity(intent);
    }


}
