package com.keeper.keeper;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.keeper.keeper.adapters.ReceiptListAdapter;
import com.keeper.keeper.databases.QuotesandOrdersDb;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

public class ConfirmActivity extends AppCompatActivity {
    TextView tvSalesCounter, tvSalesTotal;
    ListView mListView;
    ArrayList<Product> mProducts;
    ReceiptListAdapter mListAdapter;
    QuotesandOrdersDb db;
    String type,nameQuote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        mListView= (ListView) findViewById(R.id.listconfirmOrders);
        tvSalesCounter = (TextView)findViewById(R.id.tvSalesCounter);
        tvSalesTotal = (TextView)findViewById(R.id.tvSalesTotals);
        type=getIntent().getStringExtra("type");
        getSupportActionBar().setTitle("Confirm "+type);
        nameQuote=getIntent().getStringExtra("names");
        db=new QuotesandOrdersDb(this);
        mProducts=db.getProducts();
        mListAdapter=new ReceiptListAdapter(this,mProducts);
        mListView.setAdapter(mListAdapter);
        updateItems();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProducts.get(position).setQuantity(mProducts.get(position).getQuantity()+1);
                db.updateQuantity(mProducts.get(position).getCode(),mProducts.get(position).getQuantity());
                mListAdapter.notifyDataSetChanged();
                updateItems();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int qty=mProducts.get(position).getQuantity()-1;
                if (qty>0) {
                    mProducts.get(position).setQuantity(qty);
                    db.updateQuantity(mProducts.get(position).getCode(), mProducts.get(position).getQuantity());
                }else
                {
                    db.deleteItem(mProducts.get(position).getCode());
                    mProducts.remove(position);
                }
                mListAdapter.notifyDataSetChanged();
                updateItems();
                return true;
            }
        });


    }

    private void updateItems() {
        tvSalesCounter.setText("" + db.countItems()+" Items");
        tvSalesTotal.setText("KES "+db.getProductsTotalCost());
    }

    public void confirm(View view) {
          db.saveNewOrder(type,nameQuote,mProducts);
          db.clearRecords();
          Snackbar.make(tvSalesCounter,type+" has been saved successfully",Snackbar.LENGTH_LONG).show();
    }
}
