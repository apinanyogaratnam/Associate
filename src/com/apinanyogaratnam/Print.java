package com.apinanyogaratnam;

import java.util.LinkedList;

public class Print {
    // using method overloading to use same method name with different parameters
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

    public static void print(LinkedList<?> listOfObjects) {
        // using generic objects to determine which object was passed through parameter
        for (Object obj : listOfObjects) {
            // user object
            if (obj instanceof User) print("First name: " + ((User) obj).getFirstName() + ", Last name: "
                    + ((User) obj).getLastName() + ", Username: " + ((User) obj).getUsername());
            // company object
            else if (obj instanceof Company) print("Company name: " + ((Company) obj).getName());

            // object doesnt exist here
        }
    }
}
