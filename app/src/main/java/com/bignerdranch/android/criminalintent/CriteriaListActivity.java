package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Map;

/**
 * Created by MeOne on 5/13/2017.
 */

public class CriteriaListActivity extends SingleFragmentActivity {

//    public static Intent newInstance(Context packageContext, Map<String, String>)

    @Override
    protected Fragment createFragment() {
        return(new CriteriaListFragment());
    }
}
