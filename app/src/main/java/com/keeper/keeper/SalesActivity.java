package com.keeper.keeper;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.keeper.keeper.fragments.ReceiptDialogFragment;
import com.keeper.keeper.fragments.ReceiptFragment;
import com.keeper.keeper.fragments.SalesFragment;

public class SalesActivity extends AppCompatActivity implements SalesFragment.OnShoppingBasketSelectedListener,
        ReceiptDialogFragment.ItemQuantityChangedListener{

    public static final String SALES_FRAGMENT = "sales_fragment";
    public static final String RECEIPT_FRAGMENT = "receipt_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        //show the receipt fragment
        ReceiptFragment savedFragment = (ReceiptFragment) getSupportFragmentManager().findFragmentByTag(RECEIPT_FRAGMENT);
        if (savedFragment == null) {
            ReceiptFragment listFragment = new ReceiptFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeHolderSales, listFragment, RECEIPT_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onShoppingBasketClicked() {

        ReceiptFragment receiptFragment = new ReceiptFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeHolderSales, receiptFragment, RECEIPT_FRAGMENT);
        fragmentTransaction.addToBackStack("added");
        fragmentTransaction.commit();

    }

    @Override
    public void onQuantityChanged(int code, int quantity) {
        ReceiptFragment receiptFragment = (ReceiptFragment) getSupportFragmentManager().findFragmentByTag(RECEIPT_FRAGMENT);
        if (receiptFragment!=null)
        {
            Log.d("FRAGMENT","NOT NULL");
            receiptFragment.refresh();
        }
        else
        {
            Log.d("FRAGMENT","NULL");
        }
    }


/*    FragmentManager manager = ( (AppCompatActivity) context ).getSupportFragmentManager ();
    manager.findFragmentByTag ( tag );
    FragmentTransaction ft = manager.beginTransaction ();

    if (manager.findFragmentByTag ( tag ) == null) { // No fragment in backStack with same tag..
        ft.add ( fragmentHolderLayoutId, fragment, tag );
        ft.addToBackStack ( tag );
        ft.commit ();
    }
    else {
        ft.show ( manager.findFragmentByTag ( tag ) ).commit ();
    }*/
}