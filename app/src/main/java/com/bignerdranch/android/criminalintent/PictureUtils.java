package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by gwc on 3/22/2017.
 */

public class PictureUtils
{
    public static Bitmap getScaledBitmap(String path, Activity activity)
    {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return(getScaledBitmap(path, size.x, size.y));
    }
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //Bitmap bitmapOrg = BitmapFactory.decodeFile(path, options);
/*        boolean portriat = false;

        try {
            ExifInterface exif = new ExifInterface(path);
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Log.i("PictureUtil", "rotation: " + rotation);
            if (rotation == ExifInterface.ORIENTATION_ROTATE_90) {
                portriat = true;
            }
        } catch (IOException io) {
        }*/



        int srcWidth = options.outWidth;
        Log.i("PictureUtils", "SRCwidth:" + srcWidth);
        int srcHeight = options.outHeight;
        Log.i("PictureUtils", "SRCheight:" + srcHeight);

        int inSampleSize = 1;
        if(srcHeight > destHeight || srcWidth > destWidth)
        {
            if(srcWidth > srcHeight)
            {
                inSampleSize = Math.round(srcHeight / destHeight);
            }
            else
            {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

/*       if (portriat) {
           Matrix matrix = new Matrix();
           matrix.postRotate(90);
           Bitmap resizedBitmap = Bitmap.createBitmap(BitmapFactory.decodeFile(path, options), 0, 0, destWidth, destHeight, matrix, true);
           return resizedBitmap;
       }*/

        return BitmapFactory.decodeFile(path, options);
    }
}
