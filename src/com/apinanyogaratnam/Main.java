package com.apinanyogaratnam;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        LinkedList<User> totalUsers = new LinkedList<>();

        createNewUser("Apinan", "Yogaratnam", totalUsers);
    }

    public static User createNewUser(String firstName, String lastName, LinkedList<User> totalUsers) {
        Print printClass = new Print();
        User newUser = new User(firstName, lastName, totalUsers);
        newUser.print(totalUsers);

        return newUser;
    }
}
