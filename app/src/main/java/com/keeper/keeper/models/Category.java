package com.keeper.keeper.models;

/**
 * Created by walter on 7/22/17.
 */

public class Category {
    private String title;
    private int count;
    int color;

    public Category(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public Category(String title, int count, int color) {
        this.title = title;
        this.count = count;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }
}
