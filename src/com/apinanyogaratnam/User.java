package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
    String firstName;
    String lastName;
    LinkedList<User> friendsList= new LinkedList<>();
    LinkedList<Company> companiesList = new LinkedList<>();
    boolean visited;

    public void createNewUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
