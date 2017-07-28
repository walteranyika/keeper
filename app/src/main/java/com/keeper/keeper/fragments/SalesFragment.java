package com.keeper.keeper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.keeper.keeper.R;
import com.keeper.keeper.SalesActivity;
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
    TemporaryDb temporaryDb;
    SalesListAdapter adapter;
    public static final String TAG = "KEEPER";
    public interface OnShoppingBasketSelectedListener{
        void onShoppingBasketClicked();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sales_fragment, container, false);
        ((SalesActivity) getActivity()).getSupportActionBar().setTitle("All Products");
        ListView listView = (ListView) view.findViewById(R.id.sales_list);
        setHasOptionsMenu(true);
        temporaryDb = new TemporaryDb(getActivity());
        tvSalesCounter = (TextView) view.findViewById(R.id.tvSalesCounter);
        tvSalesTotal = (TextView) view.findViewById(R.id.tvSalesTotals);
        LinearLayout layoutStocks = (LinearLayout) view.findViewById(R.id.layoutCounter);

        tvSalesCounter.setText(temporaryDb.countItems()+" Items");
        tvSalesTotal.setText("KES "+temporaryDb.getProductsTotalCost());
        final ArrayList<Product> data = new ProductsDb(getActivity()).getProducts();
        adapter = new SalesListAdapter(getActivity(), data);
        listView.setAdapter(adapter);
        tvSalesCounter.setText(temporaryDb.countItems()+" Items");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked at "+data.get(position).getTitle());
                temporaryDb.saveProduct(data.get(position));
                tvSalesCounter.setText("" + temporaryDb.countItems()+" Items");
                tvSalesTotal.setText("KES "+temporaryDb.getProductsTotalCost());
                Log.d(TAG, "" + temporaryDb.getProductsTotalCost());
            }
        });

        layoutStocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        if(temporaryDb.getProductsTotalCost()>0)
        {
            OnShoppingBasketSelectedListener listener = (OnShoppingBasketSelectedListener) getActivity();
            listener.onShoppingBasketClicked();
        }
        else
        {
            Toast.makeText(getActivity(), "Cart is empty", Toast.LENGTH_SHORT).show();
        }
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sales_fragment_menu,menu);

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

        if(item.getItemId()==R.id.itemDone)
        {
            FragmentManager manager= getActivity().getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            if(manager.findFragmentByTag(SalesActivity.RECEIPT_FRAGMENT)==null){
                ReceiptFragment receiptFragment = new ReceiptFragment();
                ft.add(R.id.placeHolderReports,receiptFragment,SalesActivity.RECEIPT_FRAGMENT);
                ft.addToBackStack(SalesActivity.RECEIPT_FRAGMENT);
                ft.commit();
                Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
            }else
            {
                manager.popBackStack();
            }
        }
        return true;
    }
    /*public void replaceFragment(Fragment frag) {
                FragmentManager fm = getSupportFragmentManager();

                if (fm != null){
                FragmentTransaction t = fm.beginTransaction();
                //you could also use containerViewId in place of R.id.detail_fragment_placeholder
                Fragment currentFrag = fm.findFragmentById(R.id.detail_fragment_placeholder);
                if (currentFrag != null && currentFrag.getClass().equals(frag.getClass())) {
                    t.replace(R.id.detail_fragment_placeholder, frag).commit();
                } else {
                    t.replace(R.id.detail_fragment_placeholder, frag).addToBackStack(null).commit();
                }
            }
    }*/
}
