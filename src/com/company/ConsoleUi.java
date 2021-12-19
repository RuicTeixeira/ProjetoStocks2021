package com.company;

import com.company.Product.Product;

public class ConsoleUi {

    public static void employerMenu(){
        System.out.println("\n");
        System.out.println("Press 1 - List all Products");
        System.out.println("Press 2 - Add Products to Catalogue");
        System.out.println("Press 3 - Remove product by id");
        System.out.println("Press 4 - Add quantity to a product in Catalog");
        System.out.println("Press 5 - Finish Session\n");
    }

    public static void clientMenu(){
        System.out.println("Press 1 - Add products to your basket");
        System.out.println("Press 2 - Remove product from your basket");
        System.out.println("Press 3 - Finish Session");
    }

}
