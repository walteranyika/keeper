package com.keeper.keeper.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keeper.keeper.R;
import com.keeper.keeper.models.Contact;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

public class ContactsListAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<Contact> temporaryArray;
    private ArrayList<Contact> permanentArray;

    public ContactsListAdapter(Context context, ArrayList<Contact> data) {
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
            convertView = inflater.inflate(R.layout.address_book_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvNames = (TextView) convertView.findViewById(R.id.addressNames);
            viewHolder.tvEmail = (TextView) convertView.findViewById(R.id.addressEmail);
            viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.addressPhone);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.addressType);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Contact product = temporaryArray.get(position);
        viewHolder.tvNames.setText(product.getName());
        viewHolder.tvEmail.setText(product.getEmail());
        viewHolder.tvPhone.setText(product.getTelephone());
        viewHolder.tvType.setText(product.getType());


       /* viewHolder.popupImageView.setOnClickListener(new View.OnClickListener() {
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
        });*/
        return convertView;
    }

    public void filter(String text) {
/*        text = text.toLowerCase();
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
        notifyDataSetChanged();*/
    }

    static class ViewHolder
    {
        TextView tvNames;
        TextView tvPhone;
        TextView tvEmail;
        TextView tvType;

    }
}

