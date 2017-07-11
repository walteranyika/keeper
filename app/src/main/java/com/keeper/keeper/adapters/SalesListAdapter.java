package com.keeper.keeper.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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

public class SalesListAdapter extends BaseAdapter {


    Context mContext;
    ArrayList<Product> data;

    public SalesListAdapter(Context context, ArrayList<Product> data) {
        this.mContext = context;
        this.data = data;
//        this.mListener =listener;
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
            convertView = inflater.inflate(R.layout.sales_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.itemTitle);
            viewHolder.priceTextView = (TextView) convertView.findViewById(R.id.itemPrice);
            viewHolder.codeTextView = (TextView) convertView.findViewById(R.id.itemCode);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Product product = data.get(position);
        viewHolder.titleTextView.setText(product.getTitle());
        viewHolder.priceTextView.setText("" + product.getPrice());
        viewHolder.codeTextView.setText("" + product.getCode());
        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView codeTextView;
        TextView priceTextView;
    }
}
