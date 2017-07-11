package com.keeper.keeper.models;

/**
 * Created by walter on 7/11/17.
 */

public class PurchasedItem {
    private String code;
    private String product;
    private double price;
    private int quantity;
    private int purchase_date;
    private String purchase_month;
    private String raw_date;
    private int customer_id;

    public PurchasedItem(String code, String product, double price, int quantity, int purchase_date, String purchase_month, String raw_date, int customer_id) {
        this.code = code;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.purchase_date = purchase_date;
        this.purchase_month = purchase_month;
        this.raw_date = raw_date;
        this.customer_id = customer_id;
    }

    public String getCode() {
        return code;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPurchase_date() {
        return purchase_date;
    }

    public String getPurchase_month() {
        return purchase_month;
    }

    public String getRaw_date() {
        return raw_date;
    }

    public int getCustomer_id() {
        return customer_id;
    }
}
