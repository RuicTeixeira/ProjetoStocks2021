package com.company.Shop;

import com.company.Utilities.ConsoleUi;
import com.company.Product.Catalog;
import com.company.Product.Orders;
import com.company.Product.Product;
import com.company.Users.Client;
import com.company.Users.Employee;
import com.company.Users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Shop {


    private final String SERIALIZED_PATH = "src/com/company/SerializedObjects/Products.ser";
    private final Scanner sc = new Scanner(System.in);
    private Orders order;
    private List<User> users;
    private Catalog productList;
    private User loggedInUser;


    public Shop() {

        this.order = new Orders();
        this.productList = new Catalog();
        this.users = new ArrayList<>();
    }


    public void printProducts() {
        for (Map.Entry<Integer, Product> product : productList.getListProducts().entrySet()) {
            System.out.println("id- "
                    + product.getValue().getId() + " ; "
                    + "name- "
                    + product.getValue().getName() + " ; "
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
                    printListOfProductsByPrice();
                    break;
                case 6:
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
                    printProducts();
                    break;
                case 4:
                    printListOfProductsByPrice();
                    break;

                case 5:
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
        this.productList = (Catalog) objectInputStream.readObject();

        objectInputStream.close();


    }


    private void menuClientAddProductToCart() {
        System.out.println("Choose product id");
        int id = sc.nextInt();

        if (checkProductId(id)) {
            System.out.println("Select quantity");
            int quantity = sc.nextInt();
            addProductsToCart(id, quantity);
        } else {
            System.out.println("That product does not exist\n");
        }

        System.out.println("Items on your cart: ");
        order.printOrderedProducts();
        System.out.println("\n");
    }

    private void menuClientRemoveProductFromCart() {
        int idRemover = sc.nextInt();
        System.out.println("Remove a product from your cart");
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
            writer.write("Name - " + name + "\nQuantity - " + quantity + "\nPrice - " + price + "\n\n\n" + "Total cost of your purchase: " + orderTotalCost());
        }


        writer.flush();
        writer.close();

    }


    public boolean logInConfirmation() {
        boolean correctLogin = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your username ");
        String username = sc.next();

        System.out.println("Enter your password");
        String password = sc.next();
        addUsersDemo();
        for (User user : users) {
            if (username.equals(user.getUsername()) & password.equals(user.getPassword())) {
                System.out.println("\nWelcome " + user.getName() + " choose an option\n");
                loggedInUser = user;
                return true;

            }
        }
        return false;
    }

    public void addUsersDemo() {
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

    public double orderTotalCost() {
        double finalCost = 0.0;
        for (Map.Entry<Integer, Product> productEntry : order.getOrderedProducts().entrySet()) {
            finalCost += productEntry.getValue().getPrice();
        }
        return finalCost;
    }

    public List<Product> listOfProductsByPrice() {
        System.out.println("Enter the lower price");
        double lowerPrice = sc.nextDouble();
        System.out.println("Enter the higher price");
        double higherPrice = sc.nextDouble();
        return productList.getListProducts().values()
                .stream()
                .filter(product1 -> product1.getPrice() > lowerPrice)
                .filter(product1 -> product1.getPrice() < higherPrice)
                .collect(Collectors.toList());
    }

    public void printListOfProductsByPrice() {
        List<Product> productList = listOfProductsByPrice();
        for (Product product : productList) {
            System.out.println("id- "
                    + product.getId() + " ; "
                    + "name- "
                    + product.getName() + " ; "
                    + "price- "
                    + product.getPrice());
        }
    }


}