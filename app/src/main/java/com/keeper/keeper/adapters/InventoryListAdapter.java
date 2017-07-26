package com.keeper.keeper.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.keeper.keeper.EditProductActivity;
import com.keeper.keeper.R;
import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

public class InventoryListAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<Product> temporaryArray;
    private ArrayList<Product> permanentArray;

    public InventoryListAdapter(Context context, ArrayList<Product> data) {
        this.mContext = context;
        this.temporaryArray = data;
        this.permanentArray = new ArrayList<>();
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
            convertView = inflater.inflate(R.layout.inventory_items_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.itemTitleInventory);
            viewHolder.priceTextView = (TextView) convertView.findViewById(R.id.itemPriceInventory);
            viewHolder.codeTextView = (TextView) convertView.findViewById(R.id.itemCodeInventory);
            viewHolder.descTextView = (TextView) convertView.findViewById(R.id.itemDescInventory);
            viewHolder.categoryTextView = (TextView) convertView.findViewById(R.id.itemCategoryInventory);
            viewHolder.popupImageView = (ImageView) convertView.findViewById(R.id.popup_menu);
            viewHolder.qtyTextView = (TextView) convertView.findViewById(R.id.itemQuantityInventory);
            viewHolder.colorView =convertView.findViewById(R.id.colorView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Product product = temporaryArray.get(position);
        viewHolder.titleTextView.setText(product.getTitle());
        viewHolder.priceTextView.setText("" + product.getPrice());
        viewHolder.codeTextView.setText("Code: " + product.getCode());
        viewHolder.descTextView.setText(product.getDescription());
        viewHolder.categoryTextView.setText(product.getCategory());
        viewHolder.qtyTextView.setText(product.getQuantity() + " Items");
        @ColorInt int color=product.getColor();

        GradientDrawable gd=new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(5);
        viewHolder.colorView.setBackground(gd);



        viewHolder.popupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop up menu
                PopupMenu menu = new PopupMenu(mContext, v);
                menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().contains("Delete")) {
                            //DONE  do the deletion
                            new MaterialDialog.Builder(mContext)
                                    .title("Delete " + product.getTitle())
                                    .content("Are you sure you want to delete " + product.getTitle() + "? Your action will be irreversible.")
                                    .positiveText("Delete")
                                    .negativeText("Cancel")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            ProductsDb db = new ProductsDb(mContext);
                                            db.deleteProduct(product.getCode());
                                            temporaryArray.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    })
                                    .show();
                        } else if (item.getTitle().toString().contains("Edit")) {
                            //TODO Edit
                            Intent editIntent = new Intent(mContext, EditProductActivity.class);
                            editIntent.putExtra("code", product.getCode());
                            mContext.startActivity(editIntent);
                        } else if (item.getTitle().toString().contains("Adjust")) {
                            //TODO Adjust
                            new MaterialDialog.Builder(mContext)
                                    .title("Add Quantity")
                                    .content("Add quantity for " + product.getTitle())
                                    .inputType(InputType.TYPE_CLASS_NUMBER)
                                    .input("Quantity", "" ,new MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(MaterialDialog dialog, CharSequence input) {
                                            try {
                                                int quantity = Integer.parseInt(input.toString());
                                                product.setQuantity(quantity+product.getQuantity());
                                                notifyDataSetChanged();
                                                ProductsDb db = new ProductsDb(mContext);
                                                db.updateQuantity(product.getCode(), quantity);
                                            }catch (NumberFormatException e)
                                            {

                                            }
                                        }
                                    }).show();

                        }
                        return true;
                    }
                });
                menu.show();
            }
        });
        return convertView;
    }

    public void filter(String text) {
        text = text.toLowerCase();
        temporaryArray.clear();
        if (text.trim().length() == 0) {
            temporaryArray.addAll(permanentArray);
        } else {
            for (Product p : permanentArray) {
                //|| (p.getCode()+"").contains(text) || (p.getPrice()+"").contains(text)
                if (p.getTitle().toLowerCase().contains(text) || (p.getCode() + "").contains(text) || (p.getPrice() + "").contains(text) || p.getCategory().toLowerCase().contains(text) || p.getDescription().toLowerCase().contains(text)) {
                    temporaryArray.add(p);
                }
            }
            Log.d("SEARCH", "COUNT " + temporaryArray.size());
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView codeTextView;
        TextView priceTextView;
        TextView descTextView;
        TextView categoryTextView;
        ImageView popupImageView;
        TextView qtyTextView;
        View colorView;
    }
}

