package com.keeper.keeper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.keeper.keeper.R;
import com.keeper.keeper.adapters.InventoryListAdapter;
import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

/**
 * Created by walter on 7/22/17.
 */

public class ItemsFragment extends Fragment {
    ProductsDb db;
    InventoryListAdapter adapter;
    ArrayList<Product> data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.items_fragment, container, false);
        ListView listView= (ListView) view.findViewById(R.id.inventory_items_list);
        db=new ProductsDb(getActivity());
        data=db.getProducts();
        Log.d("ITEMS","data b4: "+data.size());
        adapter=new InventoryListAdapter(getActivity(), data);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        data.addAll(db.getProducts());
        Log.d("ITEMS","data after "+data.size());
       // adapter.notifyDataSetInvalidated();
        adapter.notifyDataSetChanged();
    }
}
