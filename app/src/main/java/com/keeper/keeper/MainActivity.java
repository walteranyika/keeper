package com.keeper.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.keeper.keeper.databases.ProductsDb;
import com.keeper.keeper.models.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

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
       // Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
        exportDB("sales.db");
        exportDB("products.db");
        exportDB("tempo.db");
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

    private void exportDB(String db){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.keeper.keeper" +"/databases/"+db;
        String backupDBPath = db;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
