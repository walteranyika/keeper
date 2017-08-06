package com.keeper.keeper.models;

/**
 * Created by walter on 8/6/17.
 */

public class Contact {
    private int id;
    private String name;
    private  String telephone;
    private  String email;
    private  String type;

    public Contact(String name, String telephone, String email, String type) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.type = type;
    }

    public Contact(int id, String name, String telephone, String email, String type) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
