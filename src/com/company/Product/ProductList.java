package com.company.Product;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProductList implements Serializable {


    HashMap<Integer, Product> listProducts;

    public ProductList() {
        this.listProducts = new HashMap<>();
    }

    public void addQuantityToProduct(int id, int quantity) {
        for (Map.Entry<Integer, Product> productEntry : listProducts.entrySet()) {
            if (productEntry.getKey() == id) {
                productEntry.getValue().setQuantity(productEntry.getValue().getQuantity() + quantity);
            }
        }
    }

    public void addProducts(int id, String name, double price) {
        listProducts.put(id, new Product(id, name, price));

    }


    public void printClientMenuProducts() {
        for (Map.Entry<Integer, Product> product : listProducts.entrySet()) {
            System.out.println("name- " + product.getValue().getName() + " ; " + "price- " + product.getValue().getPrice() + " ; " + "quantity- " + product.getValue().getQuantity());
        }
    }

    public HashMap<Integer, Product> getListProducts() {
        return listProducts;
    }


}
