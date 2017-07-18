package com.keeper.keeper.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.keeper.keeper.models.PurchaseSummary;
import com.keeper.keeper.models.PurchasedItem;

import java.util.ArrayList;

/**
 * Created by walter on 7/11/17.
 */

public class SalesDb extends SQLiteOpenHelper {

    public SalesDb(Context context) {
        super(context, "sales.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String  query = "CREATE TABLE IF NOT EXISTS sales" +
                "(id INTEGER PRIMARY KEY, " +
                "code TEXT, " +
                "product TEXT, " +
                "price REAL, " +
                "quantity INTEGER, " +
                "purchase_date INT, " +
                "purchase_month TEXT, " +
                "raw_date TEXT, " +
                "customer_id INTEGER)";

        String querySummary="CREATE TABLE IF NOT EXISTS salesSummary" +
                            "(id INTEGER PRIMARY KEY, " +
                            "code TEXT, " +
                            "total_price REAL, " +
                            "purchase_date INT, " +
                            "purchase_month TEXT, " +
                            "raw_date TEXT, " +
                            "customer_id INTEGER)";

        db.execSQL(query);
        db.execSQL(querySummary);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;
        sql = "DROP TABLE IF EXISTS sales";
        db.execSQL(sql);
        String  query = "CREATE TABLE sales" +
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
     * Inserts Transaction into SQLite DB
     */
    public void saveTransaction(PurchasedItem product) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("code","A001");
        values.put("product",product.getProduct());
        values.put("price", product.getPrice());
        values.put("quantity",product.getQuantity());
        values.put("purchase_date",product.getPurchase_date());
        values.put("purchase_month",product.getPurchase_month());
        values.put("raw_date",product.getRaw_date());
        values.put("customer_id", product.getCustomer_id());

        database.insert("sales", null, values);
    }

    /**
     * Inserts Transaction into SQLite DB
     */
    public void saveSummaryTransaction(PurchaseSummary product) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put("code",product.getCode());
        values.put("total_price", product.getTotal_price());
        values.put("purchase_date",product.getPurchase_date());
        values.put("purchase_month",product.getPurchase_month());
        values.put("raw_date",product.getRaw_date());
        values.put("customer_id", product.getCustomer_id());

        database.insert("salesSummary", null, values);
    }

    /**
     * Fetches all transactions based on the code
     * @param code
     * @return
     */
    public ArrayList<PurchasedItem> getPurchasedItems(String code) {
        ArrayList<PurchasedItem> data;
        data = new ArrayList<>();
        String selectQuery = "SELECT  * FROM sales WHERE code='"+code+"'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PurchasedItem item = new PurchasedItem(cursor.getString(1), cursor.getString(2),cursor.getDouble(3),cursor.getInt(4), cursor.getInt(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8));
                data.add(item);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<PurchaseSummary> getPurchasedItems() {
        ArrayList<PurchaseSummary> data;
        data = new ArrayList<>();
        String selectQuery = "SELECT  * FROM salesSummary";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //    public PurchaseSummary(String code, double total_price, int purchase_date, String purchase_month, String raw_date, int customer_id) {

                PurchaseSummary item = new PurchaseSummary(cursor.getString(1),cursor.getDouble(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));
                data.add(item);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }



}
