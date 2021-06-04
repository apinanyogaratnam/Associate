package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
    Print printClass = new Print();
    String firstName;
    String lastName;
    LinkedList<User> friendsList= new LinkedList<>();
    LinkedList<Company> companiesList = new LinkedList<>();
    boolean visited;

    // initializing a new user
    User(String firstName, String lastName, LinkedList<User> totalUsers) {
        this.firstName = firstName;
        this.lastName = lastName;
        totalUsers.add(this);
    }

    // print first and last name of all users
    public void print(LinkedList<User> listOfUsers) {
        for (User userToPrint : listOfUsers) {
            printClass.print(userToPrint.firstName + ", " + userToPrint.lastName);
        }
    }

}
