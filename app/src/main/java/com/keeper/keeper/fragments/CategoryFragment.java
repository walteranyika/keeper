package com.keeper.keeper.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.keeper.keeper.AddCategoryActivity;
import com.keeper.keeper.R;
import com.keeper.keeper.adapters.CategoriesListAdapter;
import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.models.Category;

import java.util.ArrayList;

/**
 * Created by walter on 7/22/17.
 */

public class CategoryFragment extends Fragment {
    ProductsDb productsDb;
    ArrayList<Category> data;
    CategoriesListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.categories_fragment, container, false);
        ListView listView= (ListView) view.findViewById(R.id.listCategories);
        productsDb =new ProductsDb(getActivity());
        data = productsDb.getCategories();
        adapter=new CategoriesListAdapter(getActivity(),data);
        listView.setAdapter(adapter);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_category,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_add_category)
        {
           startActivity(new Intent(getActivity(), AddCategoryActivity.class));
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        data.addAll(productsDb.getCategories());
        adapter.notifyDataSetChanged();
    }
}