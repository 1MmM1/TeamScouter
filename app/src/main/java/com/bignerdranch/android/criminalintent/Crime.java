package com.bignerdranch.android.criminalintent;

import android.widget.Spinner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;
    private String results;
    private int mType;
    private String number;
    private String mWins;
    private String mLosses;
    private String mTies;
    private int mDisquals;
    private int mHang;

    public static final List<String> hangingTypes = Arrays.asList("None", "Low", "High");

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id)
    {
        mId = id;
        mDate = new Date();
        mWins = "0";
        mTies = "0";
        mLosses = "0";
    }

    public void setNumber(String num) {number = num;}

    public String getNumber() {return number;}

    public void setType(int robotType) {mType = robotType;}

    public int getType() {return mType;}

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

    public void setHang(int hang) {
        mHang = hang;
    }

    public int getHang() {
        return mHang;
    }

    public String getHangString()
    {
        return(hangingTypes.get(mHang));
    }
}
