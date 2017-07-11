package com.keeper.keeper.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.keeper.keeper.models.Product;

/**
 * Created by walter on 7/11/17.
 */

public class SalesDb extends SQLiteOpenHelper {
    public SalesDb(Context context) {
        super(context, "sales_database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query = "CREATE TABLE purchases" +
                "(id INTEGER PRIMARY KEY, " +
                "code TEXT, " +
                "product TEXT, " +
                "price REAL, " +
                "quantity INTEGER, " +
                "purchase_date INT, " +
                "purchase_month TEXT, " +
                "raw_date TEXT, " +
                "customer_id INTEGER)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;
        sql = "DROP TABLE IF EXISTS sales";
        db.execSQL(sql);
        String  query = "CREATE TABLE purchases" +
                "(id INTEGER PRIMARY KEY, " +
                "code TEXT, " +
                "product TEXT, " +
                "price REAL, " +
                "quantity INTEGER, " +
                "purchase_date INT, " +
                "purchase_month TEXT, " +
                "raw_date TEXT, " +
                "customer_id INTEGER)";
        db.execSQL(query);
    }

    /**
     * Inserts User into SQLite DB
     */
    public void saveTransaction(Product product) {

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", product.getTitle());
            values.put("code", product.getCode());
            values.put("price", product.getPrice());
            Log.d("PRICE", product.getTitle() + " : " + product.getPrice());
            values.put("quantity", 1);
            database.insert("temporary", null, values);
            database.close();

    }
}
