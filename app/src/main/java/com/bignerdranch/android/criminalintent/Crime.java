package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;
    private String results;
    private String type;
    private String number;
    private String mWins;
    private String mLosses;
    private String mTies;
    private int mDisquals;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id)
    {
        mId = id;
        mDate = new Date();
    }

    public void setNumber(String num) {number = num;}

    public String getNumber() {return number;}

    public void setType(String robotType) {type = robotType;}

    public String getType() {return type;}

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
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
}
