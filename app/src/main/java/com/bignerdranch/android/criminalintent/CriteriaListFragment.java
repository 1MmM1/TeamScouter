package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        return view;
    }
}
