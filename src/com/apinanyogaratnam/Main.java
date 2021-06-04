package com.apinanyogaratnam;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        LinkedList<User> totalUsers = new LinkedList<>();


        User user1 = new User("Apinan", "Yogaratnam", totalUsers);
        print(totalUsers);
    }

    public static void print(String stringToPrint) {
        System.out.println(stringToPrint);
    }

    public static void print(LinkedList<User> listOfUsers) {
        for (int i=0; i<listOfUsers.size(); i++) {
            User userToPrint = listOfUsers.get(i);
            print(userToPrint.firstName + ", " + userToPrint.lastName);
        }
    }
}
