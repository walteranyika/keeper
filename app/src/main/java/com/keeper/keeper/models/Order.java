package com.keeper.keeper.models;

/**
 * Created by walter on 8/6/17.
 */

public class Order {

    private int id;
    private String client;
    private String type;
    private double total;
    private String date;

    public Order(int id, String client, String type, double total, String date) {
        this.id = id;
        this.client = client;
        this.type = type;
        this.total = total;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
