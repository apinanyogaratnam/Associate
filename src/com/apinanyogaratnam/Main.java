package com.apinanyogaratnam;

import java.util.LinkedList;

public class Main {
    private static MainHelper helperMethods = new MainHelper();
    public static void main(String[] args) {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        createNewCompany("McDonald's", allCompanies);
    }

    public static User createNewUser(String firstName, String lastName, String username, LinkedList<User> allUsers) {
        Print printClass = new Print();
        if (helperMethods.isValidUser(username, allUsers)) return null;
        User newUser = new User(firstName, lastName, username, allUsers);
        newUser.print(allUsers);

        return newUser;
    }

    public static Company createNewCompany(String name, LinkedList<Company> allCompanies) {
        Print printClass = new Print();
        if (helperMethods.isValidCompany(name, allCompanies)) return null;
        Company newCompany = new Company(name, allCompanies);
        newCompany.print(allCompanies);

        return newCompany;
    }

}
