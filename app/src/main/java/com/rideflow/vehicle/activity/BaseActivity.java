package com.rideflow.vehicle.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rideflow.vehicle.R;


public abstract class BaseActivity extends Activity {


    protected <A extends Activity> void gotoActivity(Class<A> clazz ) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startWaiting() {

        findViewById(R.id.errorMessage).setVisibility( View.INVISIBLE );
        findViewById(R.id.progressBar ).setVisibility( View.VISIBLE );
    }

    protected void endWaiting() {
        findViewById(R.id.progressBar ).setVisibility( View.INVISIBLE );

    }

    protected void showError(String error_message ) {
        TextView error_text = findViewById(R.id.errorMessage);

        error_text.setText( error_message );
        error_text.setVisibility( View.VISIBLE );

    }
}
