package com.company.Shop;

import com.company.Product.Orders;
import com.company.Product.Product;
import com.company.Product.ProductList;
import com.company.Users.Client;
import com.company.Users.Employee;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public class Shop {


    private final String SERIALIZED_PATH = "src/com/company/SerializedObjects/Products.ser";
    private Orders order;
    private Product product;
    private Client client;
    private Employee employee;
    private ProductList productList;


    public Shop() {

        this.order = new Orders();
        this.productList = new ProductList();
    }


    public void printProducts() {
        for (Map.Entry<Integer, Product> product : productList.getListProducts().entrySet()) {
            System.out.println("id- " + product.getValue().getId() + " ; " + "name- " + product.getValue().getName() + " ; " + "price- " + product.getValue().getPrice() + " ; " + "quantity- " + product.getValue().getQuantity());

        }
        System.out.println();
    }

    public void printOrderedProducts() {
        order.printOrderedProducts();
    }

    public void addProductToCatalog(int id, String name, int price) {
        System.out.println("Product " + name + " add to catalogue");
        productList.addProducts(id, name, price);
    }

    public void removeProduct(int id) {
        System.out.println("Product with id " + id + " as been removed");
        productList.getListProducts().remove(id);
    }


    public boolean checkProductId(int id) {
        return productList.getListProducts().containsKey(id);
    }


    public void employerMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean exitProgram = true;
        while (exitProgram) {
            System.out.println("Press 1 - List all Products");
            System.out.println("Press 2 - Add Products to Catalogue");
            System.out.println("Press 3 - Remove product by id");
            System.out.println("Press 4 - Add quantity to a product in Catalog");
            System.out.println("Press 5 - Finish Session\n");
            switch (sc.nextInt()) {
                case 1:
                    printProducts();

                    break;
                case (2):
                    System.out.println("Choose product id");
                    int id = sc.nextInt();
                    System.out.println("Choose the name of your product");
                    String name = sc.next();
                    System.out.println("Set the price for your product");
                    int price = sc.nextInt();

                    if (!checkProductId(id)) {
                        addProductToCatalog(id, name, price);
                    } else {
                        System.out.println("This id is already used choose another");
                    }
                    stockSerialization();
                    break;

                case (3):
                    System.out.println("Select id of product to remove");
                    removeProduct(sc.nextInt());
                    stockSerialization();
                    break;

                case (4):
                    System.out.println("Choose product id");
                    int productId = sc.nextInt();
                    if (checkProductId(productId)) {
                        System.out.println("Enter the quantity you want to had to stock");
                        int quantity = sc.nextInt();
                        productList.addQuantityToProduct(productId, quantity);
                    } else {
                        System.out.println("That product does not exist");
                    }
                    stockSerialization();
                    break;

                case (5):
                    System.out.println("Checkout");
                    exitProgram = false;
                    break;
            }
        }

    }


    public void clientMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean exitProgram = true;
        while (exitProgram) {
            System.out.println("Press 1 - Add products to your basket");
            System.out.println("Press 2 - Remove product from your basket");
            System.out.println("Press 3 - Finish Session");
            switch (sc.nextInt()) {
                case (1):
                    System.out.println("Choose product id");
                    int id = sc.nextInt();
                    System.out.println("Select quantity");
                    int quantity= sc.nextInt();

                    if (checkProductId(id)) {
                        addProductsToCart(id,quantity);

                    } else {
                        System.out.println("This product doesn't exist, choose another");

                    }
                    productList.printClientMenuProducts();
                    stockSerialization();

                    break;

                case (2):
                    int idRemover = sc.nextInt();
                    System.out.println("Remove a product from you basket");
                    removeProduct(idRemover);

                    break;

                case (3):
                    System.out.println("Checkout");
                    exitProgram = false;
                    break;


            }
        }
    }

    public void addProductsToCart(int id,int quantity) {
        for (Map.Entry<Integer, Product> productEntry : productList.getListProducts().entrySet()) {
            if (checkProductId(id)) {
                order.addProduct(productEntry.getValue(),quantity);
                productEntry.getValue().setQuantity(quantity);
            }
        }
    }

    public void stockSerialization() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(SERIALIZED_PATH);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.productList);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void loadStockSerialization() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(SERIALIZED_PATH);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        this.productList = (ProductList) objectInputStream.readObject();

        objectInputStream.close();


    }


}