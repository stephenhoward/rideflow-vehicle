package com.rideflow.vehicle.activity;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rideflow.stephen.rideflowvehicle.R;

import java.util.Timer;
import java.util.TimerTask;

public class logoutCountdown extends Fragment {
    private static Timer preCountdownTimer = new Timer();
    private static Timer countdownTimer    = new Timer();
    private OnTimeoutListener mCallback;

    // Container Activity must implement this interface
    public interface OnTimeoutListener {
        public void onLogoutTimeout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout_countdown, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimeoutListener ) {
            mCallback = (OnTimeoutListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimeoutListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void startCountdown() {
        View countdown = getView().findViewById(R.id.countdownWrapper);
        countdown.setVisibility( View.INVISIBLE );
        preCountdownTimer = new Timer();
        TextView textView = (TextView) getView().findViewById(R.id.countdownValue);
        textView.setText("10");

        preCountdownTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        View countdown = getView().findViewById(R.id.countdownWrapper);
                        countdown.setVisibility( View.VISIBLE );
                        countdown();
                    }
                });
            }
        },3000);
    }

    public void countdown() {

        countdownTimer = new Timer();
        countdownTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        TextView textView = (TextView) getView().findViewById(R.id.countdownValue);
                        String message = textView.getText().toString();
                        Integer count = Integer.parseInt(message);
                        count--;

                        if ( count < 1 ) {
                            mCallback.onLogoutTimeout();
                        }
                        else {
                            textView.setText(count.toString());
                        }
                    }
                });

            }
        },1000,1000);
    }

    public void endCountdown() {
        preCountdownTimer.cancel();
        countdownTimer.cancel();
    }

    public void resetCountdown() {
        endCountdown();
        startCountdown();
    }

}
