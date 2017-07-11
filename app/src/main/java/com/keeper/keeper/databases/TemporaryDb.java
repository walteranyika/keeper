package com.keeper.keeper.databases;

/**
 * Created by walter on 7/8/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.keeper.keeper.models.Product;

import java.util.ArrayList;

public class TemporaryDb extends SQLiteOpenHelper {

    //create a database called demo_database.db
    public TemporaryDb(Context applicationcontext) {
        super(applicationcontext, "tempos.db", null, 1);
    }

    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE temporary(id INTEGER PRIMARY KEY, title TEXT, code INTEGER, price REAL, quantity INTEGER)";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String sql;
        sql = "DROP TABLE IF EXISTS temporary";
        database.execSQL(sql);
        database.execSQL("CREATE TABLE temporary(id INTEGER PRIMARY KEY, title TEXT, code INTEGER, price REAL, quantity INTEGER)");
        onCreate(database);
    }

    /**
     * Inserts User into SQLite DB
     */
    public void saveProduct(Product product) {

        if (checkIfExists(product.getCode()))
        {
            updateQuantity(product.getCode());
        }else
        {
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

    /**
     * Get list of Product from SQLite DB as Array List of Products
     *
     * @return
     */
    public ArrayList<Product> getProducts() {
        ArrayList<Product> data;
        data = new ArrayList<Product>();
        String selectQuery = "SELECT  * FROM temporary";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Product person = new Product(cursor.getInt(2), cursor.getString(1), cursor.getDouble(3), cursor.getInt(4));
                data.add(person);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }


    /**
     * Get Count of  Number of  SQLite records
     *
     * @return
     */
    public int countItems() {
        int count = 0;
        String sql = "SELECT  SUM(quantity) FROM temporary";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        database.close();
        return count;
    }

    /**
     * Delete A record Based on Its ID Number
     *
     * @param code
     */
    public void deleteItem(int code) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("temporary", "code=" + code, null);
        database.close();
    }

    /**
     * Delete All records from the database
     */
    public void clearRecords() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("temporary", null, null);
        database.close();
    }

    /**
     * Check if an item is already in the database
     * @param code
     * @return
     */

    public boolean checkIfExists(int code) {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM temporary WHERE code=" + code;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    /**
     * Updates the quantity of an item in the database
     * @param code
     */
    private void updateQuantity(int code) {
        SQLiteDatabase database = this.getWritableDatabase();
        int quantity = getProductQuantity(code) + 1;
        String updateQuery = "UPDATE  temporary SET quantity=" + quantity + " WHERE code=" + code;
        database.execSQL(updateQuery);
        database.close();
    }

    /**
     * Updates the quantity of an item
     * @param code
     * @param quantity
     */
    public void updateQuantity(int code, int quantity) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE  temporary SET quantity=" + quantity + " WHERE code=" + code;
        database.execSQL(updateQuery);
        database.close();
    }

    /**
     * gets the quantity of an item from the database and returns an integer
     * @param code
     * @return
     */
    public int getProductQuantity(int code) {

        String selectQuery = "SELECT  quantity FROM temporary WHERE code=" + code;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        database.close();
        return 0;
    }

    /**
     * gets the quantity of an item from the database and returns an integer
     * @param
     * @return
     */
    public double getProductsTotalCost() {

        String selectQuery = "SELECT  SUM(price * quantity) FROM temporary";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(0);
        }
        database.close();
        return 0;
    }

}
