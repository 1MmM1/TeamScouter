package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by gwc on 3/16/2017.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                    CrimeTable.Cols.UUID + ", " + CrimeTable.Cols.TITLE + ", " +
                    CrimeTable.Cols.NUMBER + ", " + CrimeTable.Cols.DATE + ", " +
                    CrimeTable.Cols.SOLVED + ", " + CrimeTable.Cols.SUSPECT + ", " +
                    CrimeTable.Cols.WINS + ", " + CrimeTable.Cols.LOSSES + ", " +
                    CrimeTable.Cols.TIES + ", " + CrimeTable.Cols.DISQUALS + "," +
                    CrimeTable.Cols.TYPE + ", " + CrimeTable.Cols.HANG + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olcVersion, int newVersion)
    {

    }
}
