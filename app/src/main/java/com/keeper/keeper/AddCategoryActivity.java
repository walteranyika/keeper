package com.keeper.keeper;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.keeper.keeper.databases.ProductsDb;

public class AddCategoryActivity extends AppCompatActivity implements ColorChooserDialog.ColorCallback{
    EditText mEditTextCategory;
    ProductsDb db;
    View colorView;
    TextView colorTextView;
    int colorChoosen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mEditTextCategory= (EditText) findViewById(R.id.edtCategoryAdd);
        colorView=findViewById(R.id.colorView);
        colorTextView= (TextView) findViewById(R.id.colorTextView);
        db=new ProductsDb(this);
/*        colorView.setBackgroundColor(Color.parseColor("#f44336"));
        colorTextView.setTextColor(Color.parseColor("#f44336"));*/
        GradientDrawable gd=new GradientDrawable();
        gd.setColor(Color.parseColor("#f44336"));
        gd.setCornerRadius(5);
        colorView.setBackground(gd);
        colorChoosen=-769226;

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
                db.saveCategory(category,colorChoosen);
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

    public void chooseColor(View view) {

        int[] primary = new int[] {
                Color.parseColor("#F44336")
        };
        int[][] secondary = new int[][]
                {
                new int[] { Color.parseColor("#f44336"),
                            Color.parseColor("#e91e63"),
                            Color.parseColor("#9c27b0"),
                            Color.parseColor("#880e4f"),
                            Color.parseColor("#4a148c"),
                            Color.parseColor("#3f51b5"),
                            Color.parseColor("#2196f3"),
                            Color.parseColor("#1a237e"),
                            Color.parseColor("#006064"),
                            Color.parseColor("#1b5e20"),
                            Color.parseColor("#ff6f00"),
                            Color.parseColor("#757575"),}
        };

        new ColorChooserDialog.Builder(this, R.string.app_name)
                .customColors(primary,secondary)
                .show();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        colorTextView.setTextColor(selectedColor);
        colorChoosen=selectedColor;
        GradientDrawable gd=new GradientDrawable();
        gd.setColor(selectedColor);
        gd.setCornerRadius(5);
        colorView.setBackground(gd);

    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }
}
