package com.keeper.keeper.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.keeper.keeper.R;
import com.keeper.keeper.adapters.OrdersListAdapter;
import com.keeper.keeper.databases.QuotesandOrdersDb;
import com.keeper.keeper.models.Order;

import java.util.ArrayList;

public class QuotesFragment extends Fragment {
    QuotesandOrdersDb db;
    OrdersListAdapter adapter;
    ArrayList<Order> data;
    ListView mListView;

    public QuotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quotes, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listViewOrders);
        db=new QuotesandOrdersDb(getContext());
        data=db.getOrdersSummary("quote");
        adapter=new OrdersListAdapter(getActivity(),data,"Quote");
        mListView.setAdapter(adapter);
        return rootView;
    }


}
