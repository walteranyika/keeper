package com.keeper.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.models.Product;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProductsDb db = new ProductsDb(this);
        //Setup db with temporary data
        if (db.countItems() == 0)
        {
            db.saveProduct(new Product(101, "Maize Floor", 90, 1));
            db.saveProduct(new Product(102, "Milk", 65, 1));
            db.saveProduct(new Product(103, "Royco", 70, 1));
            db.saveProduct(new Product(104, "Soap", 59, 1));
            db.saveProduct(new Product(105, "Drinking Water", 50, 1));
            db.saveProduct(new Product(106, "Bread", 55, 1));
            db.saveProduct(new Product(107, "Cooking Oil", 175, 1));
            db.saveProduct(new Product(108, "Unga Chapati", 100, 1));
            db.saveProduct(new Product(109, "Match Stick", 8, 1));
            db.saveProduct(new Product(110, "Doom", 200, 1));
        }

    }

    public void salesBtn(View view) {
        startActivity(new Intent(this, SalesActivity.class));
    }

    public void inventoryBtn(View view) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void reportsBtn(View view) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void quotesBtn(View view) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void solidBtn(View view) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void tutorialsBtn(View view) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void addressBtn(View view) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void settingsBtn(View view) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }
}
