package com.keeper.keeper.models;

/**
 * Created by walter on 7/7/17.
 */

public class Product {
    private int code;
    private String title;
    private double price;
    private int quantity;
    private String description;
    private String category;
    private int color;
    private int fakeQuantity;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
//TODO add Category, re order level

    public Product(int code, String title, double price, int quantity) {
        this.code = code;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.fakeQuantity=0;
    }

    public Product(int code, String title, double price, int quantity, String description, String category) {

        this.code = code;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.fakeQuantity=0;
    }

    public Product(int code, String title, double price, int quantity, String description, String category, int color) {
        this.code = code;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.color = color;
        this.fakeQuantity=0;
    }

    public int getFakeQuantity() {
        return fakeQuantity;
    }

    public void setFakeQuantity(int fakeQuantity) {
        this.fakeQuantity = fakeQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
