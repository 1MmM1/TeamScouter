package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by gwc on 3/22/2017.
 */

public class PictureFragment extends DialogFragment
{
    private static final String ARG_FILE = "file";

    private ImageView mPhotoView;
    private File mPhotoFile;

    public static PictureFragment newInstance(File image)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILE, image);

        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);

        mPhotoFile = (File) getArguments().getSerializable(ARG_FILE);
        mPhotoView = (ImageView) v.findViewById(R.id.team_photo_zoom);

        if(mPhotoFile == null || !mPhotoFile.exists())
        {
            mPhotoView.setImageDrawable(null);
        }
        else
        {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }

        return (new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.photo_zoom_title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    }
                }).create());
    }
}
