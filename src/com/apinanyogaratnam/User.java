package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
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

}
