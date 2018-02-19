package com.rideflow.vehicle.activity;

import com.google.android.things.pio.UartDeviceCallback;
import com.rideflow.vehicle.API;
import com.rideflow.vehicle.drivers.PN532Driver;

import java.io.IOException;

public abstract class NFCActivity extends BaseActivity {

    private UartDeviceCallback nfc_listener = null;

    @Override
    public void onResume() {
        super.onResume();
        try {
            nfc_listener = PN532Driver.listen(this::processNFC);
        }
        catch( IOException e ) {
            //TODO handle exception
        }
    }

    @Override
    public void onPause() {
        if ( nfc_listener != null ) {
            try {
                PN532Driver.stopListening(nfc_listener);
            }
            catch( IOException e ) {
                //TODO handle exception
            }
        }
        super.onPause();
    }

    public void processNFC(String string) {
        beginProcessingNFC();
        API.getInstance().authorize(string,
            (Boolean authorized) -> {
                endProcessingNFC();
                if ( authorized ) {
                    nfcCallback(string);
                }
            }
        );

    }
    public void beginProcessingNFC() {
        // Override Me.
    }

    public void endProcessingNFC() {
        // Override Me.
    }

    public void nfcCallback( String string ) {
        // Override Me.
    }

}
