package com.rideflow.vehicle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;
import com.rideflow.stephen.rideflowvehicle.R;
import com.rideflow.vehicle.RideFlowAPI;
import com.rideflow.vehicle.model.RouteSession;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */

public class MainActivity extends Activity {

    public static final String DRIVER_ID = "com.rideflow.stephen.rideflowvehicle.DRIVER_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RideFlowAPI.setContext(this.getApplicationContext());
    }

    public void scanCard (View view) {
        startSession();
    }

    protected void startSession() {

        View spinner = findViewById(R.id.progressBar);
        spinner.setVisibility( View.VISIBLE );

        RouteSession.beginSession(this::sessionOk, this::sessionFail );

    }

    private void sessionOk( RouteSession session ) {

        View spinner = findViewById(R.id.progressBar);
        spinner.setVisibility( View.INVISIBLE );

        Intent intent = new Intent(this, StartSession.class);
        intent.putExtra(DRIVER_ID, "Hal");
        startActivity(intent);
    }
    private void sessionFail( VolleyError error ) {
        View spinner = findViewById(R.id.progressBar);
        spinner.setVisibility( View.INVISIBLE );

    }
}
