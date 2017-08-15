package com.keeper.keeper;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.keeper.keeper.adapters.SalesListAdapter;
import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.databases.QuotesandOrdersDb;
import com.keeper.keeper.models.Product;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.ArrayList;

public class AddQuoteOrOrderActivity extends AppCompatActivity {
    TextView tvSalesCounter, tvSalesTotal;
    QuotesandOrdersDb temporaryDb;
    SalesListAdapter adapter;
    RadioButton mTypeRadioButton;
    EditText edtClientNames;
    MultiStateToggleButton select_btn;
    String type="order";
    private static final String TAG = "QUOTES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote_or_order);
        ListView listView = (ListView)findViewById(R.id.listViewQuotesorOrders);
        temporaryDb = new QuotesandOrdersDb(this);
        tvSalesCounter = (TextView)findViewById(R.id.tvSalesCounter);
        tvSalesTotal = (TextView)findViewById(R.id.tvSalesTotals);
        select_btn= (MultiStateToggleButton) findViewById(R.id.select_btn);
        select_btn.setValue(0);
        edtClientNames= (EditText) findViewById(R.id.edtClientName);
        final ArrayList<Product> data = new ProductsDb(this).getProducts();
        adapter = new SalesListAdapter(this, data);
        listView.setAdapter(adapter);
        updateItems();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                temporaryDb.saveProduct(data.get(position));
                data.get(position).setFakeQuantity(data.get(position).getFakeQuantity()+1);
                updateItems();
                Log.d(TAG, "" + data.get(position).getFakeQuantity());
                adapter.notifyDataSetChanged();
            }
        });

        select_btn.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
               if (position==1){
                   type="quote";
               }else{
                   type="order";
               }
                Log.d(TAG, "Type is "+type);
            }
        });


    }

    private void updateItems() {
        tvSalesCounter.setText(temporaryDb.countItems()+" Items");
        tvSalesTotal.setText("KES "+temporaryDb.getProductsTotalCost());
    }

    public void confirm(View view) {
        if(edtClientNames.getText().toString().trim().isEmpty())
        {
            Snackbar.make(tvSalesCounter,"You must enter the "+type+" name",Snackbar.LENGTH_SHORT).show();
            return;
        }else if(temporaryDb.countItems()==0)
        {
            Snackbar.make(tvSalesCounter,"You must add at least one item to your "+type,Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (temporaryDb.countItems()>0 && !edtClientNames.getText().toString().trim().isEmpty()) {
            Intent intent = new Intent(this, ConfirmActivity.class);
            intent.putExtra("names", edtClientNames.getText().toString().trim());
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }
}
