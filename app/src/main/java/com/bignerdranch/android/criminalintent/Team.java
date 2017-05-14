package com.bignerdranch.android.criminalintent;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class Team implements Comparable <Team> {

    private UUID mId;
    private String mName;
    private Date mDate;
    private boolean mCubes;
    private String mContact;
    private int mType;
    private String mNumber;
    private String mWins;
    private String mLosses;
    private String mTies;
    private int mDisquals;
    private int mHang;

    public Team() {
        this(UUID.randomUUID());
    }

    public Team(UUID id)
    {
        mId = id;
        mDate = new Date();
        mWins = "0";
        mTies = "0";
        mLosses = "0";
        mName = "";
    }

    public void setNumber(String num) {
        mNumber = num;}

    public String getNumber() {return mNumber;}

    public void setType(int robotType) {mType = robotType;}

    public int getType() {return mType;}

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isCubes() {
        return mCubes;
    }

    public void setCubes(boolean cubes) {
        mCubes = cubes;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public String getPhotoFilename()
    {
        return ("IMG_" + getId().toString() + ".jpg");
    }

    public String getWins() {
        return mWins;
    }

    public void setWins(String wins) {
        mWins = wins;
    }

    public String getLosses() {
        return mLosses;
    }

    public void setLosses(String losses) {
        mLosses = losses;
    }

    public String getTies() {
        return mTies;
    }

    public void setTies(String ties) {
        mTies = ties;
    }

    public int getDisquals() {
        return mDisquals;
    }

    public void setDisquals(int disquals) {
        mDisquals = disquals;
    }

    public void setHang(int hang) {
        mHang = hang;
    }

    public int getHang() {
        return mHang;
    }

    public String getHangString()
    {
        return(TeamLab.hangingTypes.get(mHang));
    }

    public int compareTo(Team team) {
        int wins = Integer.parseInt(mWins);
        int teamWins = Integer.parseInt(team.mWins);
        if (wins == teamWins)
            return mName.compareTo(team.mName);
        return (teamWins - wins);
    }
}
