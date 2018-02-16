package com.rideflow.vehicle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rideflow.vehicle.R;
import com.rideflow.vehicle.model.RouteSession;


public class MainActivity extends NFCActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void nfcCallback( String string ) {
        TextView label =  findViewById(R.id.scan_pass_tip);
        label.setText(string);
    }

    public void scanCard (View view) {
        authSession();
    }

    protected void authSession() {

        findViewById(R.id.sessionError).setVisibility( View.INVISIBLE );
        findViewById(R.id.progressBar ).setVisibility( View.VISIBLE );

        RouteSession.beginSession(this::authSessionOk, this::authSessionFail );

    }

    private void authSessionOk( RouteSession session ) {

        findViewById(R.id.progressBar).setVisibility( View.INVISIBLE );

        Intent intent = new Intent(this, StartSession.class);
        intent.putExtra("com.rideflow.vehicle.DRIVER_ID", "Hal");
        startActivity(intent);
    }
    private void authSessionFail( VolleyError error ) {

        findViewById(R.id.progressBar ).setVisibility( View.INVISIBLE );
        TextView error_text = findViewById(R.id.sessionError);

        error_text.setText( error.getMessage() );
        error_text.setVisibility( View.VISIBLE );
    }
}
