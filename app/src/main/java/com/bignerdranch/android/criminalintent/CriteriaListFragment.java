package com.bignerdranch.android.criminalintent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MeOne on 5/13/2017.
 */

public class CriteriaListFragment extends Fragment{

    private static final String SAVED_CRITERIA_SUBTITLE_VISIBLE = "criteriaSubtitle";

    private RecyclerView mCriteriaRecyclerView;
    private boolean mCriteriaSubtitleVisible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_criteria_list, container, false);

        mCriteriaRecyclerView = (RecyclerView) view.findViewById(R.id.criteria_recycler_view);
        mCriteriaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCriteriaRecyclerView.setAdapter(new CriteriaAdapter());

        if(savedInstanceState != null)
        {
            mCriteriaSubtitleVisible = savedInstanceState.getBoolean(SAVED_CRITERIA_SUBTITLE_VISIBLE);
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_CRITERIA_SUBTITLE_VISIBLE, mCriteriaSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_criteria_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.criteria_menu_item_show_subtitle);
        if(mCriteriaSubtitleVisible)
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
            case(R.id.criteria_menu_item_show_subtitle):
                mCriteriaSubtitleVisible = !mCriteriaSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return(true);
            default:
                return(super.onOptionsItemSelected(item));
        }
    }

    private void updateSubtitle()
    {
        String subtitle = getResources().getQuantityString(R.plurals.criteria_subtitle_plural, Team.criteriaList.size(), Team.criteriaList.size());

        if(!mCriteriaSubtitleVisible)
        {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private class CriteriaHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout mCriteriaList;
        private TextView mCriteriaName;
        private TextView mCriteriaType;

//        private String mCriteria;

        public CriteriaHolder(View itemView)
        {
            super(itemView);

            mCriteriaList = (RelativeLayout) itemView.findViewById(R.id.criteria_list_layout);
            mCriteriaName = (TextView) itemView.findViewById(R.id.list_item_criteria_name);
            mCriteriaType = (TextView) itemView.findViewById(R.id.list_item_criteria_type);
        }

        public void bindCriteria(String criteria, int position)
        {
//            mCriteria = criteria;
            mCriteriaName.setText(getString(R.string.criteria_name_text, criteria));
            mCriteriaType.setText(getString(R.string.criteria_name_text, Team.criteriaList.get(criteria)));
            if (position % 2 == 1)
                mCriteriaList.setBackgroundColor(Color.rgb(186, 236, 255));
            else
                mCriteriaList.setBackgroundColor(Color.WHITE);
        }
    }

    private class CriteriaAdapter extends RecyclerView.Adapter<CriteriaHolder>
    {
        @Override
        public CriteriaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_criteria, parent, false);
            return new CriteriaHolder(view);
        }

        @Override
        public void onBindViewHolder(CriteriaHolder holder, int position) {
            String criteria = Team.getCriteriaAt(position);
            holder.bindCriteria(criteria, position);
        }

        @Override
        public int getItemCount() {
            return Team.criteriaList.size();
        }
    }
}
