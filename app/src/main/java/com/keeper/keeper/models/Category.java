package com.keeper.keeper.models;

/**
 * Created by walter on 7/22/17.
 */

public class Category {
    private String title;
    private int count;

    public Category(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }
}
