package com.bignerdranch.android.criminalintent;

import android.app.Activity;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeamListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private LinearLayout mEmptyCrimeView;
    private Button mCreateButton;
    private Callbacks mCallbacks;

    public interface Callbacks
    {
        void onCrimeSelected(Team team);
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

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mEmptyCrimeView = (LinearLayout) view.findViewById(R.id.empty_crime_view);
        mCreateButton = (Button) view.findViewById(R.id.create_crime_button);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCrime();
            }
        });

        if(savedInstanceState != null)
        {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    private void createCrime() {
        Team team = new Team();
        TeamLab.get(getActivity()).addCrime(team);
        updateUI();
        mCallbacks.onCrimeSelected(team);
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
        inflater.inflate(R.menu.fragment_crime_list, menu);

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
            case(R.id.menu_item_new_crime):
                createCrime();;
                return(true);
            case(R.id.menu_item_show_subtitle):
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return(true);
            default:
                return(super.onOptionsItemSelected(item));
        }
    }

    private void updateSubtitle()
    {
        TeamLab teamLab = TeamLab.get(getActivity());
//        int crimeCount = teamLab.getCrimes().size();
//        String subtitle = getString(R.string.subtitle_format, crimeCount);
        int crimeSize = teamLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, crimeSize, crimeSize);

        if(!mSubtitleVisible)
        {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public void updateUI() {
        TeamLab teamLab = TeamLab.get(getActivity());
        ArrayList<Team> teams = teamLab.getCrimes();
        if(teams.size() == 0)
        {
            mEmptyCrimeView.setVisibility(View.VISIBLE);
        }
        else
        {
            mEmptyCrimeView.setVisibility(View.GONE);
        }

        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(teams);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            //The problem with mAdapter.notifyItemChanged(mCurrentPosition); is that if you swipe left/right after
            //changing something, it will not reload the changed fragment when you come back to list view.

            mAdapter.setTeams(teams);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mWinLossTie;
        private TextView mRanking;
//        private CheckBox mSolvedCheckBox;

        private Team mTeam;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mWinLossTie = (TextView) itemView.findViewById(R.id.list_item_win_loss_tie_text_view);
            mRanking = (TextView) itemView.findViewById(R.id.team_rank_text_view);
            // The checkbox was changed to a text view so we still have to write the code for that
//            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
//            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    mTeam.setSolved(isChecked);
//                    TeamLab.get(getActivity()).updateCrime(mTeam);
//                }
//            });
        }

        public void bindCrime(Team team, int position) {
            mTeam = team;
            mTitleTextView.setText(mTeam.getTitle());
            mDateTextView.setText(mTeam.getNumber());
            mWinLossTie.setText(getString(R.string.win_loss_ties, mTeam.getWins(), mTeam.getTies(), mTeam.getLosses()));
            mRanking.setText(getString(R.string.rank, position + ""));
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onCrimeSelected(mTeam);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private static final String TAG = "CrimeAdapter";

        private List<Team> mTeams;

        public CrimeAdapter(List<Team> teams) {
            mTeams = teams;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Team team = mTeams.get(position);
            holder.bindCrime(team, position + 1);
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
