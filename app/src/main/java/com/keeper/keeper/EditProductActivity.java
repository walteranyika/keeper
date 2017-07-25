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

public class EditProductActivity extends AppCompatActivity {
    EditText mEditTextName, mEditTextPrice, mEditTextDescription, mEditTextQty, mEditTextCode;
    Spinner mSpinner;//TODO Fetch data from db
    ProductsDb mProductsDb;
    ArrayList<String> spinnerData;
    ArrayAdapter<String> spinnerAdapter;
    Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        mProductsDb = new ProductsDb(this);
        int code = getIntent().getIntExtra("code", 0);
        mProduct = mProductsDb.getProduct(code);

        mEditTextName = (EditText) findViewById(R.id.editItemName);
        mEditTextPrice = (EditText) findViewById(R.id.edtItemPrice);
        mEditTextDescription = (EditText) findViewById(R.id.edtItemDescription);
        mEditTextQty = (EditText) findViewById(R.id.edtItemQuantity);
        mSpinner = (Spinner) findViewById(R.id.spinnerItemCategory);
        mEditTextCode = (EditText) findViewById(R.id.edtItemCode);

        spinnerData = new ArrayList<>();
        spinnerData.addAll(mProductsDb.getUniqueCategories());
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);

        if (mProduct != null) {
            mEditTextName.setText(mProduct.getTitle());
            mEditTextCode.setText(String.valueOf(mProduct.getCode()));
            mEditTextPrice.setText(String.valueOf(mProduct.getPrice()));
            mEditTextQty.setText(String.valueOf(mProduct.getQuantity()));
            mEditTextDescription.setText(mProduct.getDescription());
            mSpinner.setSelection(getSelectedPosition(mProduct.getCategory()));
        }


    }
    private int getSelectedPosition(String category){
        ArrayList<String> items =mProductsDb.getUniqueCategories();
        int position=0;
        for(int i=0; i<items.size(); i++){
            if(category.toLowerCase().contains(items.get(i).toLowerCase()))
            {
                position=i;
                break;
            }
        }

        return  position;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyBoard();
        if(item.getItemId()==R.id.menu_item_add){
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
                mProductsDb.updateProduct(product);
                Snackbar.make(mEditTextCode,"Item Was Updated Successfully",Snackbar.LENGTH_SHORT).show();
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

    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
