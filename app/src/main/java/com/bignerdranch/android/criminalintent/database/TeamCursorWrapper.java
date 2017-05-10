package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.Team;
import com.bignerdranch.android.criminalintent.database.TeamDbSchema.TeamTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by gwc on 3/16/2017.
 */

public class TeamCursorWrapper extends CursorWrapper {
    public TeamCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Team getCrime()
    {
        String uuidString = getString(getColumnIndex(TeamTable.Cols.UUID));
        String title = getString(getColumnIndex(TeamTable.Cols.TITLE));
        String number = getString(getColumnIndex(TeamTable.Cols.NUMBER));
        long date = getLong(getColumnIndex(TeamTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(TeamTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(TeamTable.Cols.SUSPECT));
        String wins = getString(getColumnIndex(TeamTable.Cols.WINS));
        String losses = getString(getColumnIndex(TeamTable.Cols.LOSSES));
        String ties = getString(getColumnIndex(TeamTable.Cols.TIES));
        int type = getInt(getColumnIndex(TeamTable.Cols.TYPE));
        int hang = getInt(getColumnIndex(TeamTable.Cols.HANG));
        int disquals = getInt(getColumnIndex(TeamTable.Cols.DISQUALS));


        Team team = new Team(UUID.fromString(uuidString));
        team.setTitle(title);
        team.setNumber(number);
        team.setDate(new Date(date));
        team.setSolved(isSolved != 0);
        team.setSuspect(suspect);
        team.setWins(wins);
        team.setLosses(losses);
        team.setTies(ties);
        team.setDisquals(disquals);
        team.setType(type);
        team.setHang(hang);


        return team;
    }
}
