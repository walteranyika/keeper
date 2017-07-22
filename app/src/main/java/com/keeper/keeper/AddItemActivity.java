package com.keeper.keeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.models.Product;

public class AddItemActivity extends AppCompatActivity {
    EditText mEditTextName, mEditTextPrice, mEditTextDescription, mEditTextQty, mEditTextCode;
    Spinner mSpinner;//TODO Fetch data from db
    ProductsDb mProductsDb;
    //TODO Add automatic code addition
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mEditTextName = (EditText) findViewById(R.id.editItemName);
        mEditTextPrice = (EditText) findViewById(R.id.edtItemPrice);
        mEditTextDescription = (EditText) findViewById(R.id.edtItemDescription);
        mEditTextQty = (EditText) findViewById(R.id.edtItemQuantity);
        mSpinner = (Spinner) findViewById(R.id.spinnerItemCategory);
        mEditTextCode = (EditText) findViewById(R.id.edtItemCode);
        mProductsDb=new ProductsDb(this);
        mEditTextCode.setText(""+mProductsDb.getNextProductCode());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_add) {
            if (validateField(mEditTextCode) && validateField(mEditTextName) && validateField(mEditTextPrice) && validateField(mEditTextQty) && validateField(mEditTextDescription)) {
                String name = mEditTextName.getText().toString().trim();
                String price_str = mEditTextPrice.getText().toString().trim();
                String qty_str = mEditTextQty.getText().toString().trim();
                String desc = mEditTextDescription.getText().toString().trim();
                String code_str = mEditTextCode.getText().toString().trim();
                String category = mSpinner.getSelectedItem().toString();

                double price = Double.parseDouble(price_str);
                int quantity = Integer.parseInt(qty_str);
                int code = Integer.parseInt(code_str);

                Product product=new Product(code,name,price,quantity,desc,category);
                mProductsDb.saveProduct(product);
                clearFields(mEditTextName);
                clearFields(mEditTextPrice);
                clearFields(mEditTextQty);
                clearFields(mEditTextDescription);
                clearFields(mEditTextCode);
                mEditTextCode.setText(""+mProductsDb.getNextProductCode());
                Snackbar.make(mEditTextCode,"Item Was Added Successfully",Snackbar.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public boolean validateField(EditText editTextName) {
        boolean ok = false;
        if (editTextName.getText().toString().isEmpty()) {
            editTextName.setError("Fill in here");
        } else {
            ok = true;
        }
        return ok;
    }
    public  void clearFields(EditText editTextName){
        editTextName.setText("");
    }
}
