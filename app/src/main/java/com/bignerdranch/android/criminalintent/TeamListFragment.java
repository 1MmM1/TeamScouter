package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeamListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mTeamRecyclerView;
    private TeamAdapter mAdapter;
    private boolean mSubtitleVisible;
    private LinearLayout mEmptyTeamView;
    private Button mCreateButton;
    private Callbacks mCallbacks;

    public interface Callbacks
    {
        void onTeamSelected(Team team);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        mTeamRecyclerView = (RecyclerView) view.findViewById(R.id.team_recycler_view);
        mTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mEmptyTeamView = (LinearLayout) view.findViewById(R.id.empty_team_view);
        mCreateButton = (Button) view.findViewById(R.id.create_team_button);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTeam();
            }
        });

        if(savedInstanceState != null)
        {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    private void createTeam() {
        Team team = new Team();
        TeamLab.get(getActivity()).addTeam(team);
        updateUI();
        mCallbacks.onTeamSelected(team);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_team_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible)
        {
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else
        {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case(R.id.menu_item_new_team):
                createTeam();;
                return(true);
            case(R.id.menu_item_show_subtitle):
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return(true);
            case(R.id.menu_item_criteria_list):
                Intent i = new Intent(getActivity(), CriteriaListActivity.class);
                startActivity(i);
                return(true);
            default:
                return(super.onOptionsItemSelected(item));
        }
    }

    private void updateSubtitle()
    {
        TeamLab teamLab = TeamLab.get(getActivity());
        int teamSize = teamLab.getTeam().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, teamSize, teamSize);

        if(!mSubtitleVisible)
        {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public void updateUI() {
        TeamLab teamLab = TeamLab.get(getActivity());
        ArrayList<Team> teams = teamLab.getTeam();
        if(teams.size() == 0)
        {
            mEmptyTeamView.setVisibility(View.VISIBLE);
        }
        else
        {
            mEmptyTeamView.setVisibility(View.GONE);
        }

        if(mAdapter == null) {
            mAdapter = new TeamAdapter(teams);
            mTeamRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setTeams(teams);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class TeamHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mWinLossTie;
        private TextView mRanking;
        private RelativeLayout mTeamList;

        private Team mTeam;

        public TeamHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_team_name_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_team_number_text_view);
            mWinLossTie = (TextView) itemView.findViewById(R.id.list_item_win_loss_tie_text_view);
            mRanking = (TextView) itemView.findViewById(R.id.team_rank_text_view);
            mTeamList = (RelativeLayout) itemView.findViewById(R.id.team_list_layout);
        }

        public void bindTeam(Team team, int position) {
            mTeam = team;
            mTitleTextView.setText(mTeam.getName());
            mDateTextView.setText(mTeam.getNumber());
            mWinLossTie.setText(getString(R.string.win_loss_ties, mTeam.getWins(), mTeam.getTies(),
                    mTeam.getLosses()));
            mRanking.setText(getString(R.string.rank, position + ""));
            if (position % 2 != 1)
                mTeamList.setBackgroundColor(Color.rgb(186, 236, 255));
            else
                mTeamList.setBackgroundColor(Color.WHITE);
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onTeamSelected(mTeam);
        }
    }

    private class TeamAdapter extends RecyclerView.Adapter<TeamHolder> {
        private static final String TAG = "TeamAdapter";

        private List<Team> mTeams;

        public TeamAdapter(List<Team> teams) {
            mTeams = teams;
        }

        @Override
        public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_team, parent, false);
            return new TeamHolder(view);
        }

        @Override
        public void onBindViewHolder(TeamHolder holder, int position) {
            Team team = mTeams.get(position);
            holder.bindTeam(team, position + 1);
        }

        @Override
        public int getItemCount() {
            return mTeams.size();
        }

        public void setTeams(List<Team> teams)
        {
            mTeams = teams;
        }
    }
}
