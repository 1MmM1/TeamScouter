package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by gwc on 5/5/2017.
 */

public class HangFragment extends DialogFragment {

    private static final String ARG_POSITION = "position";
    public static final String EXTRA_POSITION = "com.bignerdranch.android.criminalintent.position";

    private SeekBar mHangBar;

    public static HangFragment newInstance(int pos)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, pos);

        HangFragment fragment = new HangFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hang, null);

        final int position = getArguments().getInt(ARG_POSITION);
        mHangBar = (SeekBar) v.findViewById(R.id.hang_seek_bar);
        mHangBar.setProgress(position);
        mHangBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Toast.makeText(getActivity(), Crime.hangingTypes.get(progress), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return (new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.hang_title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        sendResult(Activity.RESULT_OK, mHangBar.getProgress());
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        sendResult(Activity.RESULT_CANCELED, position);
                    }
                }).create());
    }

    private void sendResult(int resultCode, int pos)
    {
        if(getTargetFragment() == null)
        {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_POSITION, pos);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}