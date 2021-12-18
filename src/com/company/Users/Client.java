package com.company.Users;

import java.util.Objects;
import java.util.Scanner;

public class Client extends User {



    public Client(String name, String username, String password) {
        super(name, username, password);
    }

    public boolean logInConfirmation() {
        boolean correctLogin = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter you user username ");
        String username = sc.next();

        System.out.println("Enter your password");
        String password = sc.next();

        if (Objects.equals(username, getUsername()) && Objects.equals(password, getPassword())) {
            System.out.println("Welcome user choose your task");
            return true;

        } else {
            System.out.println("Wrong credentials, try again");
            return false;

        }
        
    }


}

