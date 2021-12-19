package com.company;

import com.company.Shop.Shop;
import com.company.Users.Client;
import com.company.Users.Employee;
import com.company.Users.User;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// write your code here
        Shop shop = new Shop();
        shop.loadStockSerialization();
        shop.login();
    }
}
