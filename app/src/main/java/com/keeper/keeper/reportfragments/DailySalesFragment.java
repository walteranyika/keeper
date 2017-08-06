package com.keeper.keeper.reportfragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import com.keeper.keeper.R;
import com.keeper.keeper.ReportsActivity;

public class DailySalesFragment extends Fragment {

    TextView tvFromDate, tvToDate;

    public DailySalesFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_sales, container, false);
        ((ReportsActivity) getActivity()).getSupportActionBar().setTitle("Daily Report");
        tvFromDate = (TextView) view.findViewById(R.id.tvFromDate);
        tvToDate = (TextView) view.findViewById(R.id.tvToDate);

        tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogFrom();
            }
        });
        tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToDialogFrom();
            }
        });
        return view;
    }

    private void showToDialogFrom() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("TAG",""+month);
            }
        }, 2017, 7, 1);
        datePickerDialog.setMessage("To Date");
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void showFromDialogFrom() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("TAG",""+month);
            }
        }, 2017, 7, 1);
        datePickerDialog.setMessage("From Date");
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

}
