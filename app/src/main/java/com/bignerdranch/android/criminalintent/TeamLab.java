package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.criminalintent.database.TeamBaseHelper;
import com.bignerdranch.android.criminalintent.database.TeamCursorWrapper;
import com.bignerdranch.android.criminalintent.database.TeamDbSchema.TeamTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class TeamLab {
    private static TeamLab sTeamLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static final List<String> hangingTypes = Arrays.asList("None", "Low", "High");
    public static final Map<String, String> criteriaList = createCriteriaMap();

    public static TeamLab get(Context context) {
        if (sTeamLab == null) {
            sTeamLab = new TeamLab(context);
        }
        return sTeamLab;
    }

    private TeamLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new TeamBaseHelper(mContext).getWritableDatabase();

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

        TeamCursorWrapper cursor = queryTeams(null, null);

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                teams.add(cursor.getTeam());
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
        TeamCursorWrapper cursor = queryTeams(TeamTable.Cols.UUID + " = ?", new String[] { id.toString() });

        try
        {
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return(cursor.getTeam());
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
        values.put(TeamTable.Cols.TITLE, team.getName());
        values.put(TeamTable.Cols.DATE, team.getDate().getTime());
        values.put(TeamTable.Cols.SOLVED, team.isCubes() ? 1 : 0);
        values.put(TeamTable.Cols.SUSPECT, team.getContact());
        values.put(TeamTable.Cols.WINS, team.getWins());
        values.put(TeamTable.Cols.TIES, team.getTies());
        values.put(TeamTable.Cols.LOSSES, team.getLosses());
        values.put(TeamTable.Cols.DISQUALS, team.getDisquals());
        values.put(TeamTable.Cols.TYPE, team.getType());
        values.put(TeamTable.Cols.HANG, team.getHang());
        return(values);
    }

    private TeamCursorWrapper queryTeams(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(TeamTable.NAME, null, whereClause, whereArgs, null, null, null);
        return(new TeamCursorWrapper(cursor));
    }

    private static Map<String, String> createCriteriaMap()
    {
        Map<String, String> currentCriteria =  new TreeMap<String, String>();
        currentCriteria.put("Team Name", "String");
        currentCriteria.put("Team Number", "String");
        currentCriteria.put("Wins", "Number");
        currentCriteria.put("Ties", "Number");
        currentCriteria.put("Losses", "Number");
        currentCriteria.put("Robot Type", "String");
        currentCriteria.put("Hanging", "String");
        currentCriteria.put("Cubes", "True/False");
        currentCriteria.put("Disqualifications", "Number");
        currentCriteria.put("Last Date Played", "Date");
        currentCriteria.put("Team Contact", "String");
        return(Collections.unmodifiableMap(currentCriteria));
    }

    public static String getCriteriaAt(int position)
    {
        int curr = 0;

        for(String criteria : criteriaList.keySet())
        {
            if(curr == position)
            {
                return (criteria);
            }
            curr++;
        }

        return null;
    }
}
