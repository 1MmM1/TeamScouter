package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeCursorWrapper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class TeamLab {
    private static TeamLab sTeamLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TeamLab get(Context context) {
        if (sTeamLab == null) {
            sTeamLab = new TeamLab(context);
        }
        return sTeamLab;
    }

    private TeamLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public void addTeam(Team c)
    {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void deleteTeam(Team c)
    {
        String uuidString = c.getId().toString();
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public ArrayList<Team> getTeam()
    {
        ArrayList<Team> teams = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                teams.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }
        Collections.sort(teams);
        return(teams);
    }

    public Team getTeam(UUID id)
    {
        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " = ?", new String[] { id.toString() });

        try
        {
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return(cursor.getCrime());
        }
        finally
        {
            cursor.close();
        }
    }

    public File getPhotoFile(Team team)
    {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null)
        {
            return null;
        }

        return(new File(externalFilesDir, team.getPhotoFilename()));
    }

    public void updateTeam(Team team)
    {
        String uuidString = team.getId().toString();
        ContentValues values = getContentValues(team);
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public static ContentValues getContentValues(Team team)
    {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, team.getId().toString());
        values.put(CrimeTable.Cols.NUMBER, team.getNumber());
        values.put(CrimeTable.Cols.TITLE, team.getTitle());
        values.put(CrimeTable.Cols.DATE, team.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, team.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, team.getSuspect());
        values.put(CrimeTable.Cols.WINS, team.getWins());
        values.put(CrimeTable.Cols.TIES, team.getTies());
        values.put(CrimeTable.Cols.LOSSES, team.getLosses());
        values.put(CrimeTable.Cols.DISQUALS, team.getDisquals());
        values.put(CrimeTable.Cols.TYPE, team.getType());
        values.put(CrimeTable.Cols.HANG, team.getHang());
        return(values);
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(CrimeTable.NAME, null, whereClause, whereArgs, null, null, null);
        return(new CrimeCursorWrapper(cursor));
    }
}
