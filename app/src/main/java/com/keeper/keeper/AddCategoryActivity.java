package com.keeper.keeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.keeper.keeper.databases.ProductsDb;

public class AddCategoryActivity extends AppCompatActivity {
    EditText mEditTextCategory;
    ProductsDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mEditTextCategory= (EditText) findViewById(R.id.edtCategoryAdd);
        db=new ProductsDb(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Check if no view has focus:
        hideKeyBoard();

        if (item.getItemId()==R.id.menu_item_add){
            String category=mEditTextCategory.getText().toString().trim();
            if (!category.isEmpty()) {
                db.saveCategory(category);
                Snackbar.make(mEditTextCategory, "Category Added.", Snackbar.LENGTH_LONG).show();
                mEditTextCategory.setText("");
            }else
            {
                Snackbar.make(mEditTextCategory, "You must provide  a Category", Snackbar.LENGTH_SHORT).show();
            }
        }else if(item.getItemId()==android.R.id.home)
        {
          this.finish();
        }
        return true;
    }

    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
