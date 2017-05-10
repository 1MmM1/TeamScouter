package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.TeamCursorWrapper;
import com.bignerdranch.android.criminalintent.database.TeamDbSchema.TeamTable;

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

        mDatabase.insert(TeamTable.NAME, null, values);
    }

    public void deleteTeam(Team c)
    {
        String uuidString = c.getId().toString();
        mDatabase.delete(TeamTable.NAME, TeamTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public ArrayList<Team> getTeam()
    {
        ArrayList<Team> teams = new ArrayList<>();

        TeamCursorWrapper cursor = queryCrimes(null, null);

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
        TeamCursorWrapper cursor = queryCrimes(TeamTable.Cols.UUID + " = ?", new String[] { id.toString() });

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
        mDatabase.update(TeamTable.NAME, values, TeamTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public static ContentValues getContentValues(Team team)
    {
        ContentValues values = new ContentValues();
        values.put(TeamTable.Cols.UUID, team.getId().toString());
        values.put(TeamTable.Cols.NUMBER, team.getNumber());
        values.put(TeamTable.Cols.TITLE, team.getTitle());
        values.put(TeamTable.Cols.DATE, team.getDate().getTime());
        values.put(TeamTable.Cols.SOLVED, team.isSolved() ? 1 : 0);
        values.put(TeamTable.Cols.SUSPECT, team.getSuspect());
        values.put(TeamTable.Cols.WINS, team.getWins());
        values.put(TeamTable.Cols.TIES, team.getTies());
        values.put(TeamTable.Cols.LOSSES, team.getLosses());
        values.put(TeamTable.Cols.DISQUALS, team.getDisquals());
        values.put(TeamTable.Cols.TYPE, team.getType());
        values.put(TeamTable.Cols.HANG, team.getHang());
        return(values);
    }

    private TeamCursorWrapper queryCrimes(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(TeamTable.NAME, null, whereClause, whereArgs, null, null, null);
        return(new TeamCursorWrapper(cursor));
    }
}
