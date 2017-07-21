package com.keeper.keeper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.keeper.keeper.R;
import com.keeper.keeper.SalesActivity;
import com.keeper.keeper.adapters.ReceiptListAdapter;
import com.keeper.keeper.databases.TemporaryDb;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by walter on 7/9/17.
 */

public class ReceiptFragment extends Fragment {
    private static final String FRAGMENT_CHARGE = "charge_fragment";
    ArrayList<Product> data;
    TemporaryDb db;
    ReceiptListAdapter adapter;
    TextView tvTotal;
    TextView tvCounter;
    FancyButton productsBtn,checkOutBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receipt_fragment, container, false);
        ((SalesActivity) getActivity()).getSupportActionBar().setTitle("Cart");
        db= new TemporaryDb(getActivity());

        tvTotal= (TextView) view.findViewById(R.id.tvReceiptTotals);
        tvCounter= (TextView) view.findViewById(R.id.tvReceiptCounter);
        tvTotal.setText("KES "+db.getProductsTotalCost());
        tvCounter.setText(db.countItems()+" Items");
        productsBtn= (FancyButton) view.findViewById(R.id.products);
        checkOutBtn= (FancyButton) view.findViewById(R.id.btnCheckOut);

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChargeFragment chargeFragment=new ChargeFragment();
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeHolderSales, chargeFragment, ChargeFragment.class.getName());
                ft.addToBackStack(ChargeFragment.class.getName());
                ft.commit();
            }
        });

        try {
            productsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SalesFragment salesFragment=new SalesFragment();
                    FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                    ft.replace(R.id.placeHolderSales, salesFragment, SalesActivity.SALES_FRAGMENT);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }
        catch(NullPointerException e){
            Log.d("KEEPER", e.getMessage());
            e.printStackTrace();
        }

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
        tvTotal.setText("KES "+db.getProductsTotalCost());
        tvCounter.setText(db.countItems()+" Items");

    }


}
