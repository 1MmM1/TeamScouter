package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by gwc on 3/14/2017.
 */

public class DeleteConfirmationFragment extends DialogFragment
{
    public static DeleteConfirmationFragment newInstance()
    {
        DeleteConfirmationFragment fragment = new DeleteConfirmationFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_delete, null);

        return (new AlertDialog.Builder(getActivity()).setView(v)
                .setTitle(R.string.delete_confirmation_title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        getTargetFragment()
                                .onActivityResult(getTargetRequestCode(),
                                        Activity.RESULT_OK, null);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        getTargetFragment()
                                .onActivityResult(getTargetRequestCode(),
                                        Activity.RESULT_CANCELED, null);
                    }
                }).create());
    }
}
