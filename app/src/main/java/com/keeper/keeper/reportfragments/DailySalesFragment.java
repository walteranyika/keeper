package com.keeper.keeper.reportfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keeper.keeper.R;
import com.keeper.keeper.ReportsActivity;

public class DailySalesFragment extends Fragment {


    public DailySalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_sales, container, false);
        ((ReportsActivity) getActivity()).getSupportActionBar().setTitle("Reports");

        return view;
    }

}
