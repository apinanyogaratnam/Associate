package com.apinanyogaratnam;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        // load data from db to current data structures
        CreateSQL.loadDB(allUsers, allCompanies);

        // interactive
        String beginningMenu = "Menu: \n" +
                "1. create new user\n" +
                "2. create new company\n" +
                "Enter a number: ";
        Print.print(beginningMenu);
        int option = reader.nextInt();

        switch(option) {
            case 1:
                String userMenu = "User Menu: \n" +
                        "1. Add friend\n" +
                        "2. Remove friend\n" +
                        "3. Follow company\n" +
                        "4. Remove company\n" +
                        "5. Print friends\n" +
                        "6. Print following companies\n" +
                        "7. Update first name\n" +
                        "8. Update last name\n" +
                        "9. Update username\n" +
                        "Enter a number: ";
                Print.print(userMenu);
            case 2:
                String companiesMenu = "Companies Menu: \n" +
                        "1. Add company\n" +
                        "2. Remove friend\n" +
                        "3. Follow company\n" +
                        "4. Remove company\n" +
                        "5. Print friends\n" +
                        "6. Print following companies\n" +
                        "7. Update first name\n" +
                        "8. Update last name\n" +
                        "9. Update username\n" +
                        "Enter a number: ";
                Print.print(companiesMenu);
            default:
                Print.print("Invalid option.");
        }
    }

    public static User createNewUser(String firstName, String lastName, String username, LinkedList<User> allUsers, boolean withSQL) {
        // conditions to pass to create new user
        if (username == null) return null;
        if (firstName == null || lastName == null) return null;
        if (MainHelper.isValidUser(username, allUsers)) return null;

        firstName = Utils.parseString(firstName);
        lastName = Utils.parseString(lastName);
        username = Utils.parseString(username);
        User newUser = new User(firstName, lastName, username, allUsers);

        // if user already exists, nothing happens
        // db updates
        if (withSQL) UpdateSQL.addObjectToDB(newUser);

        return newUser;
    }

    public static Company createNewCompany(String name, LinkedList<Company> allCompanies, boolean withSQL) {
        // conditions to pass to create new company
        if (name == null) return null;
        if (MainHelper.isValidCompany(name, allCompanies)) return null;

        name = Utils.parseString(name);
        Company newCompany = new Company(name, allCompanies);

        // if company already exists, nothing happens
        // db updates
        if (withSQL) UpdateSQL.addObjectToDB(newCompany);

        return newCompany;
    }
}
