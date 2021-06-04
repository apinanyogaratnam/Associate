package com.apinanyogaratnam;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        LinkedList<User> totalUsers = new LinkedList<>();


        User user1 = new User("Apinan", "Yogaratnam", totalUsers);
        printListOfUsers(totalUsers);
    }

    public static void printString(String stringToPrint) {
        System.out.println(stringToPrint);
    }

    public static void printListOfUsers(LinkedList<User> listOfUsers) {
        for (int i=0; i<listOfUsers.size(); i++) {
            User userToPrint = listOfUsers.get(i);
            printString(userToPrint.firstName + ", " + userToPrint.lastName);
        }
    }
}
