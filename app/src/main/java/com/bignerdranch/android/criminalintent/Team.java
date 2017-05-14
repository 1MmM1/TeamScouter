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

    public static final List<String> hangingTypes = Arrays.asList("None", "Low", "High");
    public static final Map<String, String> criteriaList = createCriteriaMap();

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
        return(hangingTypes.get(mHang));
    }

    public int compareTo(Team team) {
        int wins = Integer.parseInt(mWins);
        int teamWins = Integer.parseInt(team.mWins);
        if (wins == teamWins)
            return mName.compareTo(team.mName);
        return (teamWins - wins);
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
