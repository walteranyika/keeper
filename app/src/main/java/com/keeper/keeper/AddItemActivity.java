package com.keeper.keeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {
    EditText mEditTextName, mEditTextPrice, mEditTextDescription, mEditTextQty, mEditTextCode;
    Spinner mSpinner;
    ProductsDb mProductsDb;
    ArrayList<String> spinnerData;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mProductsDb=new ProductsDb(this);
        mEditTextName = (EditText) findViewById(R.id.editItemName);
        mEditTextPrice = (EditText) findViewById(R.id.edtItemPrice);
        mEditTextDescription = (EditText) findViewById(R.id.edtItemDescription);
        mEditTextQty = (EditText) findViewById(R.id.edtItemQuantity);
        mSpinner = (Spinner) findViewById(R.id.spinnerItemCategory);


        spinnerData=new ArrayList<>();
        spinnerData.addAll(mProductsDb.getUniqueCategories());
        spinnerAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spinnerData);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);

        mEditTextCode = (EditText) findViewById(R.id.edtItemCode);
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
            hideKeyBoard();
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
        }else if(item.getItemId()==android.R.id.home)
        {
            this.finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        spinnerData.clear();
        spinnerData.addAll(mProductsDb.getUniqueCategories());
        spinnerAdapter.notifyDataSetChanged();
    }
    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
