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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void salesBtn(View view) {
        startActivity(new Intent(this, SalesActivity.class));
    }

    public void inventoryBtn(View view) {
        // Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
        //exportDB("sales.db");
        //exportDB("tempo.db");
        exportDB("products.db");
        startActivity(new Intent(this, StocksActivity.class));
    }

    public void reportsBtn(View view) {
        startActivity(new Intent(this, ReportsActivity.class));
    }

    public void quotesBtn(View view) {
        startActivity(new Intent(this, QuotesOrdersActivity.class));
    }

    public void solidBtn(View view) {
        startActivity(new Intent(this, SolidActivity.class));
    }

    public void tutorialsBtn(View view) {
        startActivity(new Intent(this, TutorialsActivity.class));
    }

    public void addressBtn(View view) {
        startActivity(new Intent(this, AddressBookActivity.class));
    }

    public void settingsBtn(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void exportDB(String db) {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.keeper.keeper" + "/databases/" + db;
        Calendar calender = Calendar.getInstance();
        Date leo = calender.getTime();
        String date_f = new SimpleDateFormat("yyyy_m_dd").format(leo);

        String backupDBPath = db;
        backupDBPath = backupDBPath + "_" + date_f;

        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            //Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
