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

import com.keeper.keeper.R;
import com.keeper.keeper.databases.TemporaryDb;

/**
 * Created by walter on 7/11/17.
 */

public class ChargeFragment  extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.charge_fragment, container, false);
        Button btnCharge = (Button) view.findViewById(R.id.buttonCharge);
        TextView textViewAmount = (TextView) view.findViewById(R.id.tvChargeAmount);
        final TextView textViewStatus= (TextView) view.findViewById(R.id.tvChargeStatus);
        TemporaryDb db =new TemporaryDb(getActivity());
        textViewAmount.setText("KES "+db.getProductsTotalCost());

        btnCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Add the receipt item to purchases DB


                textViewStatus.setText("Transaction Successfull");
                textViewStatus.setScaleX(0);
                textViewStatus.setScaleY(0);
                textViewStatus.animate().setDuration(1500)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .scaleY(1)
                        .scaleX(1)
                        .start();

            }
        });
        return  view;
    }
}
