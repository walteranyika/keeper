package com.keeper.keeper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.keeper.keeper.adapters.ContactsListAdapter;
import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.fragments.NewContactDialogFragment;
import com.keeper.keeper.models.Contact;

import java.util.ArrayList;

public class AddressBookActivity extends AppCompatActivity implements NewContactDialogFragment.ContactAddedListener {
    ListView mListView;
    ContactsListAdapter mListAdapter;
    ArrayList<Contact> mContactArrayList;
    ProductsDb mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView= (ListView) findViewById(R.id.listAddressbook);
        mDb=new ProductsDb(this);
        mDb.saveContact(new Contact("John Mark","0723456789","john@yahoo.com","Client"));
        mDb.saveContact(new Contact("Mary Jane","0723456788","jane@yahoo.com","Supplier"));
        mDb.saveContact(new Contact("Susan Huff","0723456790","susan@yahoo.com","Client"));
        mContactArrayList=mDb.getContacts();
        mListAdapter=new ContactsListAdapter(this,mContactArrayList);
        mListView.setAdapter(mListAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //TODO add contacts
                new NewContactDialogFragment().show(getSupportFragmentManager(),"");
            }
        });
    }

    @Override
    public void onContactAdded() {
        mContactArrayList.clear();
        mContactArrayList.addAll(mDb.getContacts());
        mListAdapter.notifyDataSetChanged();
        Snackbar.make(mListView,"Contact Added",Snackbar.LENGTH_SHORT).show();
    }
}
