package com.keeper.keeper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.keeper.keeper.R;
import com.keeper.keeper.adapters.ReceiptListAdapter;
import com.keeper.keeper.databases.TemporaryDb;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

/**
 * Created by walter on 7/9/17.
 */

public class ReceiptFragment extends Fragment {
    private static final String FRAGMENT_CHARGE = "charge_fragment";
    ArrayList<Product> data;
    TemporaryDb db;
    ReceiptListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receipt_fragment, container, false);
        db= new TemporaryDb(getActivity());
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layoutReceiptCounter);
        TextView tvTotal= (TextView) view.findViewById(R.id.tvReceiptTotals);
        TextView tvCounter= (TextView) view.findViewById(R.id.tvReceiptCounter);
        tvTotal.setText("KES "+db.getProductsTotalCost());
        tvCounter.setText(db.countItems()+" Items");

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Take The User To Final Fragment
                ChargeFragment chargeFragment=new ChargeFragment();
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeHolderSales, chargeFragment, FRAGMENT_CHARGE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        ListView list = (ListView) view.findViewById(R.id.receiptList);

        data = db.getProducts();
        adapter = new ReceiptListAdapter(getActivity(), data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReceiptDialogFragment frag = ReceiptDialogFragment.newInstance(data.get(position).getCode());
                frag.show(getFragmentManager(), "it_works");
            }
        });
        return view;
    }


    public void refresh() {
        data.clear();
        Log.d("ITEMS","B4 :"+data.size());
        ArrayList<Product> items = db.getProducts();
        data.addAll(items);
        Log.d("ITEMS","AF:"+data.size());
        adapter.notifyDataSetChanged();

    }
}
