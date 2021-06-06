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

    public static void print(LinkedList<Object> listOfObjects) {
        for (Object obj : listOfObjects) {
            if (obj instanceof User) print("First name: " + ((User) obj).firstName + ", Last name: " + ((User) obj).lastName);
            else if (obj instanceof Company) print("Company name: " + ((Company) obj).name);
        }
    }
}
