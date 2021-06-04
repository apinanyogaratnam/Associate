package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
    String firstName;
    String lastName;
    LinkedList<User> friendsList= new LinkedList<>();
    LinkedList<Company> companiesList = new LinkedList<>();
    boolean visited;

    Print printClass = new Print();

    // initializing a new user
    User(String firstName, String lastName, LinkedList<User> totalUsers) {
        this.firstName = firstName;
        this.lastName = lastName;
        totalUsers.add(this);
    }

    public void print(LinkedList<User> listOfUsers) {
        for (User userToPrint : listOfUsers) {
            printClass.print(userToPrint.firstName + ", " + userToPrint.lastName);
        }
    }

}
