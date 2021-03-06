package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.criminalintent.database.TeamDbSchema.TeamTable;

/**
 * Created by gwc on 3/16/2017.
 */

public class TeamBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "teamBase.db";

    public TeamBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TeamTable.NAME + "(" +
                    TeamTable.Cols.UUID + ", " + TeamTable.Cols.TITLE + ", " +
                    TeamTable.Cols.NUMBER + ", " + TeamTable.Cols.DATE + ", " +
                    TeamTable.Cols.SOLVED + ", " + TeamTable.Cols.SUSPECT + ", " +
                    TeamTable.Cols.WINS + ", " + TeamTable.Cols.LOSSES + ", " +
                    TeamTable.Cols.TIES + ", " + TeamTable.Cols.DISQUALS + "," +
                    TeamTable.Cols.TYPE + ", " + TeamTable.Cols.HANG + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olcVersion, int newVersion)
    {

    }
}
