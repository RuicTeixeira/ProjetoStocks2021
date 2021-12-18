package com.company.Product;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Orders {


    HashMap<Integer, Product> orderedProducts;

    public Orders() {
        this.orderedProducts = new HashMap<>();

    }

    public void addProduct(Product product, int quantity) {
        Product orderProduct = new Product(product.getId(), product.getName(), product.getPrice());
        orderProduct.setQuantity(quantity);
        orderedProducts.put(product.getId(), orderProduct);
        product.setQuantity(product.getQuantity() - quantity);
    }

    public void printOrderedProducts() {
        for (Map.Entry<Integer, Product> productEntry : this.orderedProducts.entrySet()) {
            System.out.println("id- " + productEntry.getValue().getId() + " ; " + "name- " + productEntry.getValue().getName() + " ; " + "price- " + productEntry.getValue().getPrice());
        }
    }

}