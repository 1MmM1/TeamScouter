package com.bignerdranch.android.criminalintent.database;

/**
 * Created by gwc on 3/16/2017.
 */

public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String NUMBER = "number";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
            public static final String WINS = "wins";
            public static final String LOSSES = "losses";
            public static final String TIES = "ties";
            public static final String DISQUALS = "disquals";
        }
    }
}
