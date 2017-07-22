package com.keeper.keeper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.keeper.keeper.R;
import com.keeper.keeper.adapters.CategoriesListAdapter;
import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.models.Category;

import java.util.ArrayList;

/**
 * Created by walter on 7/22/17.
 */

public class CategoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.categories_fragment, container, false);
        ListView listView= (ListView) view.findViewById(R.id.listCategories);
        ProductsDb productsDb=new ProductsDb(getActivity());
        ArrayList<Category> data = productsDb.getCategories();
        CategoriesListAdapter adapter=new CategoriesListAdapter(getActivity(),data);
        listView.setAdapter(adapter);
        return view;
    }
}