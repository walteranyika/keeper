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
    public ProductsDb(Context application_context) {
        super(application_context, "products.db", null, 4);
    }

    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {

        String query = "CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY, title TEXT, code INTEGER, price REAL, quantity INTEGER, desc TEXT,cat_id INTEGER)";
        database.execSQL(query);
        String query2 = "CREATE TABLE IF NOT EXISTS categories(id INTEGER PRIMARY KEY, category TEXT UNIQUE NOT NULL, UNIQUE(category) ON CONFLICT IGNORE)";
        database.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String sql;
        sql = "DROP TABLE IF EXISTS products";
        database.execSQL(sql);
        database.execSQL("CREATE TABLE IF NOT EXISTS products(id INTEGER PRIMARY KEY, title TEXT, code INTEGER, price REAL, quantity INTEGER,desc TEXT, cat_id INTEGER)");
        String sql_2 ="DROP TABLE IF EXISTS categories";
        database.execSQL(sql_2);
        database.execSQL("CREATE TABLE IF NOT EXISTS categories(id INTEGER PRIMARY KEY, category TEXT UNIQUE NOT NULL, UNIQUE(category) ON CONFLICT IGNORE)");
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
            values.put("cat_id",getCategoryId(product.getCategory()));
            values.put("desc",product.getDescription());
            Log.d("PRICE", product.getTitle() + " : " + product.getPrice());
            values.put("quantity", product.getQuantity());
            database.insert("products", null, values);
            database.close();
        }
    }

    /**
     * Inserts User into SQLite DB
     */
    public void updateProduct(Product product) {

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", product.getTitle());
            values.put("price", product.getPrice());
            values.put("cat_id",getCategoryId(product.getCategory()));
            values.put("desc",product.getDescription());
            Log.d("PRICE", product.getTitle() + " : " + product.getPrice());
            values.put("quantity", product.getQuantity());
            database.update("products",values,"code="+product.getCode(),null);
            database.close();
    }
    /**
     * Inserts Category into SQLite DB
     */
    public void saveCategory(String category) {


            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("category", category);
            database.insert("categories", null, values);
            database.close();

    }

    /**
     * Get list of Product from SQLite DB as Array List of Products
     *
     * @return ArrayList<Product>
     */
    public ArrayList<Product> getProducts() {
        ArrayList<Product> data;
        data = new ArrayList<>();
        //(id INTEGER PRIMARY KEY, title TEXT, code INTEGER, price REAL, quantity INTEGER, desc TEXT,cat_id INTEGER)";

        String selectQuery = "SELECT  products.code, products.title,products.price, products.quantity, products.desc,categories.category FROM products JOIN categories ON products.cat_id=categories.id";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //(int code, String title, double price, int quantity, String description, String category)
                Product person = new Product(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5));
                data.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return data;
    }

    /**
     * Get list of Product from SQLite DB as Array List of Products
     *
     * @return ArrayList<Product>
     */
    public Product getProduct(int code) {
       Product product=null;
        String selectQuery = "SELECT  products.code, products.title,products.price, products.quantity, products.desc,categories.category FROM products JOIN categories ON products.cat_id=categories.id WHERE products.code="+code;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                product = new Product(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return product;
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
     * @param  code
     */
    public void deleteProduct(int code) {
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
        int initial=getProductQuantity(code);
        quantity=quantity+initial;
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
        String selectQuery = "SELECT categories.category, COUNT(products.title) FROM categories LEFT JOIN products ON categories.id=products.cat_id GROUP BY categories.category";
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
        String selectQuery = "SELECT  * FROM categories";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do
            {
                data.add(cursor.getString(1));
                Log.d("CATEGORIES STRING", cursor.getString(1));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return data;
    }

    public int getCategoryId(String category){
        String selectQuery = "SELECT  id FROM categories WHERE category='"+category+"'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            Log.d("ID", "ID is "+cursor.getInt(0));
           return cursor.getInt(0);
        }
        return 0;
    }

}
