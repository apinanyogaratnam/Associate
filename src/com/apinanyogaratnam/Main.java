package com.apinanyogaratnam;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        // load data from db to current data structures
        CreateSQL.loadDB(allUsers, allCompanies);

        boolean repeat = false;
        do {
            // interactive
            String beginningMenu = "Menu: \n" +
                    "1. create new user\n" +
                    "2. create new company\n" +
                    "3. Add friend\n" +
                    "4. Remove friend\n" +
                    "5. Follow company\n" +
                    "6. Remove company\n" +
                    "7. Print friends\n" +
                    "8. Print following companies\n" +
                    "9. Update first name\n" +
                    "10. Update last name\n" +
                    "11. Update username\n" +
                    "12. Add network\n" +
                    "13. Remove network\n" +
                    "14. Print networks\n" +
                    "15. Print followers\n" +
                    "16. Update company name\n" +
                    "17. Exit\n" +
                    "18. Enter a choice (integer): ";
            Print.printSameLine(beginningMenu);
            int option = reader.nextInt();

            switch (option) {
                case 1:
                    // get user's information
                    Print.printSameLine("Enter firstname: ");
                    String firstname = reader.nextLine();
                    Print.printSameLine("Enter lastname: ");
                    String lastname = reader.nextLine();
                    Print.printSameLine("Enter username: ");
                    String username = reader.nextLine();

                    // add error check for already existing username
                    User newUser = createNewUser(firstname, lastname, username, allUsers, true);
                    // if (newUser == null) repeat getting username for user
                    Print.print("User " + username + " was created successfully.");

                    repeat = true;
                    break;
                case 2:
                    // get company's information
                    Print.printSameLine("Enter company name: ");
                    String name = reader.nextLine();

                    // add error check for already existing company
                    Company newCompany = createNewCompany(name, allCompanies, true);
                    // if (newUser == null) repeat getting name for company
                    Print.print("Company " + name + " was created successfully.");

                    repeat = true;
                    break;
                case 3:
                    repeat = true;
                    break;
                case 17:
                    repeat = false;
                    break;
                default:
                    Print.print("Invalid option.");
                    repeat = true;
            }
        } while (repeat);
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
