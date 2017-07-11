package com.keeper.keeper.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.keeper.keeper.R;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

/**
 * Created by walter on 7/9/17.
 */

public class ReceiptListAdapter extends BaseAdapter {


    Context mContext;
    ArrayList<Product> data;

    public ReceiptListAdapter(Context context, ArrayList<Product> data) {
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();// # of items in your arraylist
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);// get the actual movie
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.receipt_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.purchaseTitle);
            viewHolder.unitPriceTextView = (TextView) convertView.findViewById(R.id.purchaseUnitPrice);
            viewHolder.quantityTextView = (TextView) convertView.findViewById(R.id.purchaseQuantity);
            viewHolder.totalPriceTextView = (TextView) convertView.findViewById(R.id.purchaseTotalPrice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = data.get(position);
        viewHolder.titleTextView.setText(product.getTitle());
        viewHolder.unitPriceTextView.setText("" + product.getPrice());
        viewHolder.quantityTextView.setText("x " + product.getQuantity());
        viewHolder.totalPriceTextView.setText("" + (product.getQuantity()*product.getPrice()));
        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView quantityTextView;
        TextView unitPriceTextView;
        TextView totalPriceTextView;
    }
}

