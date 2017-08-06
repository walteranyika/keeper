package com.keeper.keeper.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.keeper.keeper.R;
import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.databases.TemporaryDb;
import com.keeper.keeper.models.Contact;


/**
 * Created by walter on 7/10/17.
 */

public class NewContactDialogFragment extends DialogFragment {

    ProductsDb db;
    int quantity;

    public interface ContactAddedListener {
        void onContactAdded();
    }

    public NewContactDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        db = new ProductsDb(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.add_contact_dialog_fragment, null);
        final EditText edtNames = (EditText) rootView.findViewById(R.id.addressNames);
        final EditText edtEmail = (EditText) rootView.findViewById(R.id.addressEmail);
        final EditText edtPhone = (EditText) rootView.findViewById(R.id.addressPhone);
        final RadioButton radioType = (RadioButton) rootView.findViewById(R.id.addressType);


         builder.setTitle("Add Contact")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String names=edtNames.getText().toString().trim();
                        String phone=edtPhone.getText().toString().trim();
                        String email=edtEmail.getText().toString().trim();
                        String type=radioType.isSelected()?"Client":"Supplier";
                        Contact  contact=new Contact(names,phone,email,type);
                        db.saveContact(contact);
                        ContactAddedListener listener= (ContactAddedListener) getActivity();
                        listener.onContactAdded();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      dismiss();
                    }
                });
        builder.setView(rootView);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
