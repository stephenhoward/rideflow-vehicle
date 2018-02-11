package com.rideflow.vehicle.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.rideflow.vehicle.R;


public class SessionOptionsFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView( inflater.inflate(R.layout.fragment_item_list_dialog, null));

        return builder.create();
    }


}
