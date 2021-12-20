package com.company.Product;
import java.util.HashMap;
import java.util.Map;

public class Orders {


    HashMap<Integer, Product> orderedProducts;

    public Orders() {
        this.orderedProducts = new HashMap<>();

    }

    public void addProduct(Product product, int quantity) {
        if (orderedProducts.containsKey(product.getId())) {
            for (Map.Entry<Integer, Product> productEntry : orderedProducts.entrySet()) {
                if (productEntry.getKey() == product.getId()) {
                    productEntry.getValue().setQuantity(productEntry.getValue().getQuantity() + quantity);
                    productEntry.getValue().setPrice(product.getPrice() * productEntry.getValue().getQuantity());
                }
            }


        } else {
            Product orderProduct = new Product(product.getId(), product.getName(), product.getPrice());
            orderProduct.setQuantity(quantity);
            orderProduct.setPrice(product.getPrice() * quantity);
            orderedProducts.put(product.getId(), orderProduct);
        }
        product.setQuantity(product.getQuantity() - quantity);
    }

    public void printOrderedProducts() {
        for (Map.Entry<Integer, Product> productEntry : this.orderedProducts.entrySet()) {
            System.out.println("id- " + productEntry.getValue().getId() + " ; " + "name- " + productEntry.getValue().getName() + " ; " + "price- " + productEntry.getValue().getPrice() + " ; " + "quantity- " + productEntry.getValue().getQuantity());
        }
    }

    public HashMap<Integer, Product> getOrderedProducts() {
        return orderedProducts;
    }
}