package com.bignerdranch.android.criminalintent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    private RecyclerView mCriteriaRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_criteria_list, container, false);

        mCriteriaRecyclerView = (RecyclerView) view.findViewById(R.id.criteria_recycler_view);
        mCriteriaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCriteriaRecyclerView.setAdapter(new CriteriaAdapter());

        return view;
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
                mCriteriaList.setBackgroundColor(Color.GRAY);
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
