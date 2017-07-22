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

import com.keeper.keeper.models.Category;
import com.keeper.keeper.models.Product;

import java.util.ArrayList;

public class ProductsDb extends SQLiteOpenHelper {

    //create a database called demo_database.db
    public ProductsDb(Context applicationcontext) {
        super(applicationcontext, "products.db", null, 3);
    }

    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY, title TEXT, code INTEGER, price REAL, quantity INTEGER, category TEXT, desc TEXT)";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String sql;
        sql = "DROP TABLE IF EXISTS products";
        database.execSQL(sql);
        database.execSQL("CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY, title TEXT, code INTEGER, price REAL, quantity INTEGER, category TEXT, desc TEXT)");
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
            values.put("category",product.getCategory());
            values.put("desc",product.getDescription());
            Log.d("PRICE", product.getTitle() + " : " + product.getPrice());
            values.put("quantity", 1);
            database.insert("products", null, values);
            database.close();
        }
    }

    /**
     * Get list of Product from SQLite DB as Array List of Products
     *
     * @return ArrayList<Product>
     */
    public ArrayList<Product> getProducts() {
        ArrayList<Product> data;
        data = new ArrayList<>();
        String selectQuery = "SELECT  * FROM products";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Product person = new Product(cursor.getInt(2), cursor.getString(1), cursor.getDouble(3), cursor.getInt(4),cursor.getString(6),cursor.getString(5));
                data.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
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
        String sql = "SELECT  SUM(quantity) FROM products";
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
     * @param  code
     */
    public void deleteItem(int code) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("products", "code=" + code, null);
        database.close();
    }

    /**
     * Delete All records from the database
     */
    public void clearRecords() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("products", null, null);
        database.close();
    }

    /**
     * Check if an item is already in the database
     * @param code
     * @return
     */

    public boolean checkIfExists(int code) {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM products WHERE code=" + code;
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
        String updateQuery = "UPDATE  products SET quantity=" + quantity + " WHERE code=" + code;
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
        String updateQuery = "UPDATE  products SET quantity=" + quantity + " WHERE code=" + code;
        database.execSQL(updateQuery);
        database.close();
    }

    /**
     * gets the quantity of an item from the database and returns an integer
     * @param code
     * @return
     */
    public int getProductQuantity(int code) {

        String selectQuery = "SELECT  quantity FROM products WHERE code=" + code;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        database.close();
        return 0;
    }

    public  int getNextProductCode(){
        String selectQuery = "SELECT code FROM products ORDER BY id DESC LIMIT 1";
        int code=0;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            Log.d("CODE",""+cursor.getInt(0));
            code= cursor.getInt(0)+1;
            Log.d("CODE",""+code);
        }
        database.close();
        return code;
    }

    /**
     * Get list of Categories from SQLite DB as Array List of Products
     *
     * @return ArrayList<Category>
     */
    public ArrayList<Category> getCategories() {
        ArrayList<Category> data;
        data = new ArrayList<>();
        String selectQuery = "SELECT  category, sum(quantity) FROM products GROUP BY category";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do
            {
                data.add(new Category(cursor.getString(0),cursor.getInt(1)));
                Log.d("CATEGORIES", cursor.getString(0)+""+cursor.getInt(1));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return data;
    }


    /**
     * Get list of Categories from SQLite DB as Array List of Products
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getUniqueCategories() {
        ArrayList<String> data;
        data = new ArrayList<>();
        String selectQuery = "SELECT  category, sum(quantity) FROM products GROUP BY category";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do
            {
                data.add(cursor.getString(0));
                Log.d("CATEGORIES STRING", cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return data;
    }

}
