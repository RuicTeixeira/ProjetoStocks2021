package com.company;

import com.company.Shop.Shop;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Shop shop = new Shop();
        shop.loadStockSerialization();
        shop.login();
    }
}
