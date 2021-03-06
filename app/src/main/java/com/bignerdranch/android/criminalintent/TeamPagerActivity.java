package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by gwc on 3/4/2017.
 */

public class TeamPagerActivity extends AppCompatActivity
        implements TeamFragment.Callbacks
{

    private static final String EXTRA_TEAM_ID = "com.bignerdranch.android.criminalintent.team_id";

    private ViewPager mViewPager;
    private List<Team> mTeams;

    public static Intent newIntent(Context packageContext, UUID teamId)
    {
        Intent intent = new Intent(packageContext, TeamPagerActivity.class);
        intent.putExtra(EXTRA_TEAM_ID, teamId);
        return intent;
    }

    @Override
    public void onTeamUpdated(Team team)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_pager);

        UUID teamId = (UUID) getIntent().getSerializableExtra(EXTRA_TEAM_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_team_pager_view_pager);

        mTeams = TeamLab.get(this).getTeam();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Team team = mTeams.get(position);
                return TeamFragment.newInstance(team.getId());
            }

            @Override
            public int getCount() {
                return mTeams.size();
            }
        });

        for(int i = 0; i < mTeams.size(); i++)
        {
            if(mTeams.get(i).getId().equals(teamId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
