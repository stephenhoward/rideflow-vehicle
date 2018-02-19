package com.rideflow.vehicle.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.rideflow.vehicle.API;
import com.rideflow.vehicle.R;
import com.rideflow.vehicle.RouteManager;
import com.rideflow.vehicle.model.RouteSession;


public class MainActivity extends NFCActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void beginProcessingNFC() {
        startWaiting();
    }

    @Override
    public void endProcessingNFC() {
        endWaiting();
    }

    @Override
    public void nfcCallback( String string ) {
        API.getInstance().authorize(string,
            (Boolean authorized) -> {
                if ( authorized ) {

                    RouteManager application     = (RouteManager) getApplication();
                    RouteSession current_session = application.getSession();

                    startWaiting();

                    if ( current_session == null ) {
                        gotoActivity( StartSession.class );
                    }
                    else {
                        gotoActivity( ManageSession.class );
                    }
                }
                else {
                    showError("Unrecognized Driver");
                }
            }
        );
        TextView label =  findViewById(R.id.scan_pass_tip);
        label.setText(string);
    }

}
