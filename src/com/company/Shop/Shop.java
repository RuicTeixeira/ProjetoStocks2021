package com.company.Shop;

import com.company.ConsoleUi;
import com.company.Product.Orders;
import com.company.Product.Product;
import com.company.Product.ProductList;
import com.company.Users.Client;
import com.company.Users.Employee;
import com.company.Users.User;

import java.io.*;
import java.util.*;


public class Shop {


    private final String SERIALIZED_PATH = "src/com/company/SerializedObjects/Products.ser";
    private final Scanner sc = new Scanner(System.in);
    private Orders order;
    private Product product;
    private List<User> users;
    private ProductList productList;
    private User loggedInUser;


    public Shop() {

        this.order = new Orders();
        this.productList = new ProductList();
        this.users = new ArrayList<>();
    }


    public void printProducts() {
        for (Map.Entry<Integer, Product> product : productList.getListProducts().entrySet()) {
            System.out.println("id- " + product.getValue().getId() + " ; "
                    + "name- " + product.getValue().getName() + " ; "
                    + "price- "
                    + product.getValue().getPrice() + " ; "
                    + "quantity- "
                    + product.getValue().getQuantity());

        }
        System.out.println();
    }


    public void addProductToCatalog(int id, String name, int price) {
        System.out.println("Product " + name + " add to catalogue");
        productList.addProducts(id, name, price);
    }

    public void removeProductFromCatalog(int id) {
        System.out.println("Product with id " + id + " as been removed");
        productList.getListProducts().remove(id);
    }

    public void removeProductFromCart(int id) {
        System.out.println("Product with id " + id + " as been removed");
        order.getOrderedProducts().remove(id);
    }


    public boolean checkProductId(int id) {
        return productList.getListProducts().containsKey(id);
    }


    public void employerMenu() throws IOException {

        boolean exitProgram = true;
        while (exitProgram) {
            ConsoleUi.employerMenu();
            switch (sc.nextInt()) {
                case 1:
                    printProducts();
                    break;
                case 2:
                    menuEmployerAddProductToCatalog();
                    break;

                case 3:
                    menuEmployerRemoveProductFromCatalog();
                    break;

                case 4:
                    menuEmployerAddQuantityToProduct();
                    break;

                case 5:
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
            ConsoleUi.clientMenu();
            switch (sc.nextInt()) {
                case 1:
                    menuClientAddProductToCart();
                    break;

                case 2:
                    menuClientRemoveProductFromCart();
                    break;

                case 3:
                    exportOrderInvoice();
                    stockSerialization();
                    System.out.println("Checkout");
                    exitProgram = false;
                    break;
            }
        }
    }

    public void addProductsToCart(int id, int quantity) {
        for (Map.Entry<Integer, Product> productEntry : productList.getListProducts().entrySet()) {
            if (id == productEntry.getValue().getId()) {
                if (productEntry.getValue().getQuantity() >= quantity) {
                    order.addProduct(productEntry.getValue(), quantity);
                } else {
                    System.out.println("Not enough Stock");
                }
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


    private void menuClientAddProductToCart() throws IOException {
        System.out.println("Choose product id");
        int id = sc.nextInt();
        System.out.println("Select quantity");
        int quantity = sc.nextInt();

        if (checkProductId(id)) {
            addProductsToCart(id, quantity);

        } else {
            System.out.println("This product doesn't exist, choose another");

        }
        order.printOrderedProducts();
    }

    private void menuClientRemoveProductFromCart() {
        int idRemover = sc.nextInt();
        System.out.println("Remove a product from you basket");
        removeProductFromCart(idRemover);
        order.printOrderedProducts();
    }

    private void menuEmployerAddProductToCatalog() throws IOException {
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
    }

    private void menuEmployerRemoveProductFromCatalog() throws IOException {
        System.out.println("Select id of product to remove from catalog");
        removeProductFromCatalog(sc.nextInt());
        stockSerialization();
    }

    private void menuEmployerAddQuantityToProduct() throws IOException {
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
    }

    public void exportOrderInvoice() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/company/Invoice/OrderInvoice.txt"));


        writer.write("You purchased the following products: \n\n\n");
        for (Map.Entry<Integer, Product> productEntry : order.getOrderedProducts().entrySet()) {
            String name = productEntry.getValue().getName();
            double price = productEntry.getValue().getPrice();
            int quantity = productEntry.getValue().getQuantity();
            writer.write("Name - " + name + "\nQuantity - " + quantity + "\nPrice - " + price + "\n\n\n");
        }

        writer.flush();
        writer.close();

    }

    public void usersDataDemo() {

    }

    public boolean logInConfirmation() {
        boolean correctLogin = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your username ");
        String username = sc.next();

        System.out.println("Enter your password");
        String password = sc.next();
        mockUsers();
        for (User user : users) {
            if (username.equals(user.getUsername()) & password.equals(user.getPassword())) {
                System.out.println("Welcome " + user.getName() + " choose your task");
                loggedInUser = user;
                return true;

            }
        }
        return false;
    }

    public void mockUsers() {
        this.users.add(new Client("Julio", "julio1", "marega1"));
        this.users.add(new Employee("Mario", "Mario1", "marega1"));
    }


    public void login() throws IOException {

        do {
            if (logInConfirmation()) {
                if (loggedInUser instanceof Client) {
                    clientMenu();
                } else {
                    employerMenu();
                }

            } else {
                System.out.println("Wrong credentials");
            }
            System.out.println("Type exit to end program or continue");
        } while (!sc.next().equals("exit"));

    }


}