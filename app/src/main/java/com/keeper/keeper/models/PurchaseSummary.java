package com.keeper.keeper.models;

/**
 * Created by walter on 7/11/17.
 */

public class PurchaseSummary {
    private String code;
    private double total_price;
    private int purchase_date;
    private String purchase_month;
    private String raw_date;
    private int customer_id;

    public PurchaseSummary(String code, double total_price, int purchase_date, String purchase_month, String raw_date, int customer_id) {
        this.code = code;
        this.total_price = total_price;
        this.purchase_date = purchase_date;
        this.purchase_month = purchase_month;
        this.raw_date = raw_date;
        this.customer_id = customer_id;
    }

    public String getCode() {
        return code;
    }

    public double getTotal_price() {
        return total_price;
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setPurchase_date(int purchase_date) {
        this.purchase_date = purchase_date;
    }

    public void setPurchase_month(String purchase_month) {
        this.purchase_month = purchase_month;
    }

    public void setRaw_date(String raw_date) {
        this.raw_date = raw_date;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
