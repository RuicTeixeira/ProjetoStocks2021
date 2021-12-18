package com.company.Product;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private int id;
    private double price;
    private int quantity;

    public Product(int id,String name, double price){
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
