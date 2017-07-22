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
import com.keeper.keeper.models.Category;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

/**
 * Created by walter on 7/9/17.
 */

public class CategoriesListAdapter extends BaseAdapter {


    Context mContext;
    ArrayList<Category> temporaryArray;
    ArrayList<Category> permanentArray;

    public CategoriesListAdapter(Context context, ArrayList<Category> data) {
        this.mContext = context;
        this.temporaryArray = data;
        this.permanentArray=new ArrayList<>();
        this.permanentArray.addAll(data);
    }

    @Override
    public int getCount() {
        return temporaryArray.size();// # of items in your arraylist
    }

    @Override
    public Object getItem(int position) {
        return temporaryArray.get(position);// get the actual movie
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
            convertView = inflater.inflate(R.layout.categories_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.tvCategoriesTitle);
            viewHolder.countTextView = (TextView) convertView.findViewById(R.id.tvCategoriesCount);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Category product = temporaryArray.get(position);
        viewHolder.titleTextView.setText(product.getTitle());
        viewHolder.countTextView.setText(product.getCount()+" Items");

        return convertView;
    }

    public void filter(String text){
        text=text.toLowerCase();
        temporaryArray.clear();

        if(text.trim().length()==0)
        {
          temporaryArray.addAll(permanentArray);
        }
        else
        {
            for (Category p:permanentArray)
            {
                //|| (p.getCode()+"").contains(text) || (p.getPrice()+"").contains(text)
               if(p.getTitle().toLowerCase().contains(text) )
               {
                  temporaryArray.add(p);
               }
            }
            Log.d("SEARCH","COUNT "+temporaryArray.size());
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView countTextView;
    }
}

