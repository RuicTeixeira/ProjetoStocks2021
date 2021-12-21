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

    private void printProducts() {
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

    private void addProductToCatalog(int id, String name, int price) {
        System.out.println("Product " + name + " add to catalogue");
        productList.addProducts(id, name, price);
    }

    private void removeProductFromCatalog(int id) {
        System.out.println("Product with id " + id + " as been removed");
        productList.getListProducts().remove(id);
    }

    private void removeProductFromCart(int id) {
        System.out.println("Product with id " + id + " as been removed\n");
        for (Map.Entry<Integer, Product> productEntryOrder : order.getOrderedProducts().entrySet()) {
            for (Map.Entry<Integer, Product> productEntryCatalog : productList.getListProducts().entrySet()) {
                if (productEntryOrder.getKey() == id) {
                    if (productEntryCatalog.getKey() == id) {
                        productEntryCatalog.getValue().setQuantity(productEntryCatalog.getValue().getQuantity()
                                + productEntryOrder.getValue().getQuantity());
                    }
                }
            }
        }
        order.getOrderedProducts().remove(id);
    }

    private boolean checkProductId(int id) {
        return productList.getListProducts().containsKey(id);
    }

    private void employerMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println(ConsoleUi.ANSI_BLUE + "\nWelcome " + loggedInUser.getName() + " choose an option\n");
        boolean exitProgram = true;
        while (exitProgram) {
            System.out.println(ConsoleUi.ANSI_BLUE + "Press enter to show employer menu" + ConsoleUi.ANSI_RESET);
            sc.nextLine();
            ConsoleUi.employerMenu();
            switch (sc.nextInt()) {
                case 1:
                    printProducts();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;
                case 2:
                    menuEmployerAddProductToCatalog();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;

                case 3:
                    menuEmployerRemoveProductFromCatalog();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;

                case 4:
                    menuEmployerAddQuantityToProduct();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;

                case 5:
                    printListOfProductsByPrice();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;
                case 6:
                    System.out.println("\nCheckout\n");
                    exitProgram = false;
                    break;
            }
        }

    }

    private void clientMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println(ConsoleUi.ANSI_RED + "\nWelcome " + loggedInUser.getName() + " choose an option\n");
        boolean exitProgram = true;
        while (exitProgram) {
            System.out.println(ConsoleUi.ANSI_RED + "Press enter to show client menu" + ConsoleUi.ANSI_RESET);
            sc.nextLine();
            ConsoleUi.clientMenu();
            switch (sc.nextInt()) {
                case 1:
                    menuClientAddProductToCart();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;

                case 2:
                    menuClientRemoveProductFromCart();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;

                case 3:
                    printProducts();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;
                case 4:
                    printListOfProductsByPrice();
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;
                case 5:
                    System.out.println("Items on your cart: ");
                    order.printOrderedProducts();
                    System.out.println("\n");
                    ConsoleUi.dividers();
                    sc.nextLine();
                    break;

                case 6:
                    exportOrderInvoice();
                    stockSerialization();
                    System.out.println("\nCheckout\n");
                    exitProgram = false;
                    order.getOrderedProducts().clear();
                    break;
            }
        }
    }

    private void addProductsToCart(int id, int quantity) {
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

    private void stockSerialization() throws IOException {

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
        System.out.println("Items on your cart: ");
        order.printOrderedProducts();
        System.out.println("\n");
        System.out.println("Remove a product from your cart by id");
        int idRemover = sc.nextInt();
        removeProductFromCart(idRemover);
        order.printOrderedProducts();
    }

    private void menuEmployerAddProductToCatalog() throws IOException {
        System.out.println("Choose product id");
        int id = sc.nextInt();
        if (!checkProductId(id)) {
            System.out.println("Choose the name of your product");
            String name = sc.next();
            System.out.println("Set the price for your product");
            int price = sc.nextInt();
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

    private void exportOrderInvoice() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/company/Invoice/OrderInvoice.txt"));

        writer.write("You purchased the following products: \n\n\n");
        for (Map.Entry<Integer, Product> productEntry : order.getOrderedProducts().entrySet()) {
            String name = productEntry.getValue().getName();
            double price = productEntry.getValue().getPrice();
            int quantity = productEntry.getValue().getQuantity();
            writer.write("Name - " + name + "\nQuantity - " + quantity + "\nPrice - " + price + "\n\n\n");
        }
        writer.write("Total cost of your purchase: " + orderTotalCost());
        writer.flush();
        writer.close();

    }

    private boolean logInConfirmation() {
        System.out.println(ConsoleUi.ANSI_PURPLE + "\nEnter your username ");
        String username = sc.next();
        System.out.println("--------------------");
        System.out.println("Enter your password" + ConsoleUi.ANSI_RESET);
        String password = sc.next();
        addUsersDemo();
        for (User user : users) {
            if (username.equals(user.getUsername()) & password.equals(user.getPassword())) {
                loggedInUser = user;
                return true;

            }
        }
        return false;
    }

    private void addUsersDemo() {
        this.users.add(new Client("Julio", "client", "client1"));
        this.users.add(new Employee("Mario", "employee", "employee1"));

    }

    public void login() throws IOException {
        ConsoleUi.banner();
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

    private double orderTotalCost() {
        double finalCost = 0.0;
        for (Map.Entry<Integer, Product> productEntry : order.getOrderedProducts().entrySet()) {
            finalCost += productEntry.getValue().getPrice();
        }
        return finalCost;
    }

    private List<Product> listOfProductsByPrice() {
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

    private void printListOfProductsByPrice() {
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