package com.keeper.keeper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.keeper.keeper.R;
import com.keeper.keeper.SalesActivity;
import com.keeper.keeper.databases.SalesDb;
import com.keeper.keeper.databases.TemporaryDb;
import com.keeper.keeper.models.Product;
import com.keeper.keeper.models.PurchaseSummary;
import com.keeper.keeper.models.PurchasedItem;
import com.keeper.keeper.utils.CalendarUtils;

import java.util.ArrayList;

/**
 * Created by walter on 7/11/17.
 */

public class ChargeFragment extends Fragment {
    boolean charged=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.charge_fragment, container, false);
        ((SalesActivity) getActivity()).getSupportActionBar().setTitle("Charge");
        Button btnCharge = (Button) view.findViewById(R.id.buttonCharge);
        TextView textViewAmount = (TextView) view.findViewById(R.id.tvChargeAmount);
        final TextView textViewStatus = (TextView) view.findViewById(R.id.tvChargeStatus);
        final TemporaryDb db = new TemporaryDb(getActivity());
        textViewAmount.setText("KES " + db.getProductsTotalCost());

        btnCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!charged){
                   long purchaseDate = System.currentTimeMillis();
                   int date = (int) purchaseDate;
                   String raw_date = CalendarUtils.ConvertToDateString(purchaseDate);
                   String month = CalendarUtils.ConvertToMonthString(purchaseDate);
                   ArrayList<Product> data =db.getProducts();
                   SalesDb sdb=new SalesDb(getActivity());
                   for (Product p: data)
                   {
                       PurchasedItem item=new PurchasedItem("A001",p.getTitle(),p.getPrice(),p.getQuantity(),date,month,raw_date,1);
                       sdb.saveTransaction(item);
                   }
                   PurchaseSummary summary=new PurchaseSummary("A001",db.getProductsTotalCost(),date,month,raw_date,1);
                   sdb.saveSummaryTransaction(summary);
                   db.clearRecords();
                   textViewStatus.setText("Transaction Successfull");
                   textViewStatus.setScaleX(0);
                   textViewStatus.setScaleY(0);
                   textViewStatus.animate().setDuration(1500)
                           .setInterpolator(new AccelerateDecelerateInterpolator())
                           .scaleY(1)
                           .scaleX(1)
                           .start();
                   charged=true;
               }else
               {
                   Toast.makeText(getActivity(), "Item Already Charged", Toast.LENGTH_SHORT).show();
               }

            }
        });
        return view;
    }
}
