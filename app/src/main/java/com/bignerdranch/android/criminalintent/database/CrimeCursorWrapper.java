package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.Team;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by gwc on 3/16/2017.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Team getCrime()
    {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        String number = getString(getColumnIndex(CrimeTable.Cols.NUMBER));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
        String wins = getString(getColumnIndex(CrimeTable.Cols.WINS));
        String losses = getString(getColumnIndex(CrimeTable.Cols.LOSSES));
        String ties = getString(getColumnIndex(CrimeTable.Cols.TIES));
        int type = getInt(getColumnIndex(CrimeTable.Cols.TYPE));
        int hang = getInt(getColumnIndex(CrimeTable.Cols.HANG));
        int disquals = getInt(getColumnIndex(CrimeTable.Cols.DISQUALS));


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
