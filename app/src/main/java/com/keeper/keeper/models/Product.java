package com.keeper.keeper.models;

/**
 * Created by walter on 7/7/17.
 */

public class Product {
    private int code;
    private String title;
    private double price;
    private int quantity;

    //TODO add Category, re order level

    public Product(int code, String title, double price, int quantity) {
        this.code = code;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
