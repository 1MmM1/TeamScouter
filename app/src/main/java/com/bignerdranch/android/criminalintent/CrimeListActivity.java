package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity
    implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    public void onCrimeUpdated(Team team)
    {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    protected int getLayoutResId()
    {
        return(R.layout.activity_masterdetail);
    }

    @Override
    public void onCrimeSelected(Team team)
    {
        if(findViewById(R.id.detail_fragment_container) == null)
        {
            Intent intent = CrimePagerActivity.newIntent(this, team.getId());
            startActivity(intent);
        }
        else
        {
            Fragment newDetail = CrimeFragment.newInstance(team.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }
}
