package com.apinanyogaratnam;

import java.util.LinkedList;

public class Print {
    public static void print(String stringToPrint) {
        System.out.println(stringToPrint);
    }

    public static void print(int integerToPrint) {
        System.out.println(integerToPrint);
    }

    public static void print(float floatToPrint) {
        System.out.println(floatToPrint);
    }

    public static void print(double doubleToPrint) {
        System.out.println(doubleToPrint);
    }

    public static void print(boolean bool) {
        if (bool) {
            print("True");
            return;
        }

        print("False");
    }
    // print first and last name of all users
    public static void print(LinkedList<User> listOfUsers) {
        for (User user : listOfUsers) {
            print(user.firstName + ", " + user.lastName);
        }
    } // tested

    // print company name
    public static void printCompanies(LinkedList<Company> listOfCompanies) {
        for (Company company : listOfCompanies) {
            print(company.name);
        }
    } // tested
}
