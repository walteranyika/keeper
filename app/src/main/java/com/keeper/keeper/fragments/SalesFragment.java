package com.keeper.keeper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.keeper.keeper.R;
import com.keeper.keeper.adapters.SalesListAdapter;
import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.databases.TemporaryDb;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

/**
 * Created by walter on 7/10/17.
 */

public class SalesFragment extends Fragment {
    TextView tvSalesCounter, tvSalesTotal;
    public static final String TAG = "KEEPER";
    public interface OnShoppingBasketSelectedListener{
        void onShoppingBasketClicked();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sales_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.sales_list);
        final TemporaryDb temporaryDb = new TemporaryDb(getActivity());
        tvSalesCounter = (TextView) view.findViewById(R.id.tvSalesCounter);
        tvSalesTotal = (TextView) view.findViewById(R.id.tvSalesTotals);
        LinearLayout layoutStocks = (LinearLayout) view.findViewById(R.id.layoutCounter);

        tvSalesCounter.setText(temporaryDb.countItems()+" Items");
        tvSalesTotal.setText("KES "+temporaryDb.getProductsTotalCost());
        final ArrayList<Product> data = new ProductsDb(getActivity()).getProducts();
        SalesListAdapter adapter = new SalesListAdapter(getActivity(), data);
        listView.setAdapter(adapter);
        tvSalesCounter.setText(temporaryDb.countItems()+" Items");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked");
                temporaryDb.saveProduct(data.get(position));
                tvSalesCounter.setText("" + temporaryDb.countItems()+" Items");
                tvSalesTotal.setText("KES "+temporaryDb.getProductsTotalCost());
                Log.d(TAG, "" + temporaryDb.getProductsTotalCost());
            }
        });

        layoutStocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  OnShoppingBasketSelectedListener listener = (OnShoppingBasketSelectedListener) getActivity();
                  listener.onShoppingBasketClicked();
            }
        });
        return view;
    }
}
