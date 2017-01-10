package com.hpe.rumarco.enotepager;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by rumarco on 15/12/2016.
 */
public class ShowDialogs extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.over_flow_about)
                .setMessage("Version: 1.00\n\nApp programming:\n\tmruizm@hpe.com\n\neNote SMS template:\n\tjerry.gdr@hpe.com" +
                        "\n\nApp testers:\n\tmruizm@hpe.com\n\tjerry.gdr@hpe.com\n\tjborge@hpe.com")
                .setNegativeButton(android.R.string.ok, null).create();
    }
}
