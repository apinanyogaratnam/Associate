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

    public static void print(LinkedList<User> listOfObjects) {
        for (User user : listOfObjects) {
            print("First name: " + user.firstName + ", Last name: " + user.lastName);
        }
    }

    public static void print(LinkedList<Company> listOfObjects, int placeholder) {
        for (Company company : listOfObjects) {
            print("Company name: " + company.name);
        }
    }
}
