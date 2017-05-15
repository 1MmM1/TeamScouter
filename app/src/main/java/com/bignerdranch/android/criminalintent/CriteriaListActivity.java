package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Map;

/**
 * Created by MeOne on 5/13/2017.
 */

public class CriteriaListActivity extends SingleFragmentActivity {

//    private static final String EXTRA_CRITERIA_MAP = "com.bignerdranch.android.criminalintent
//          .criteria_map";

//    public static Intent newInstance(Context packageContext, Map<String, String> criteriaList)
//    {
//        Intent i = new Intent(packageContext, CriteriaListActivity.class);
//        i.putExtra(EXTRA_CRITERIA_MAP, criteriaList);
//    }

    @Override
    protected Fragment createFragment() {
        return(new CriteriaListFragment());
    }
}
