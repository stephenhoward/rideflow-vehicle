package com.rideflow.vehicle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rideflow.stephen.rideflowvehicle.R;

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
