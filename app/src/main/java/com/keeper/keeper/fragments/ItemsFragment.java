package com.keeper.keeper.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.keeper.keeper.AddItemActivity;
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
        adapter=new InventoryListAdapter(getActivity(), data);
        listView.setAdapter(adapter);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        data.addAll(db.getProducts());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_stock, menu);

        SearchView searchView =(SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SEARCH", newText);
                adapter.filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_add_stock)
        {
            Intent x =new Intent(getActivity(), AddItemActivity.class);
            startActivity(x);
        }
        return true;
    }
}
