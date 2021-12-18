package com.company;

import com.company.Shop.Shop;
import com.company.Users.Client;
import com.company.Users.Employee;
import com.company.Users.User;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// write your code here

        Client client = new Client("Julio","julio1","julio2");
        Employee employee = new Employee("Mario","Mario1","mario2");

        Shop shop = new Shop();
        shop.loadStockSerialization();
        shop.employerMenu();
        shop.clientMenu();
        shop.printProducts();


    }
}
