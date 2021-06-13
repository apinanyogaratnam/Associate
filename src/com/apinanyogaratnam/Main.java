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

        boolean repeat;
        do {
            // interactive
            String beginningMenu = """
                    Menu:\s
                    1. create new user
                    2. create new company
                    3. Add friend
                    4. Remove friend
                    5. Follow company
                    6. Remove company
                    7. Print friends
                    8. Print following companies
                    9. Update first name
                    10. Update last name
                    11. Update username
                    12. Add network
                    13. Remove network
                    14. Print networks
                    15. Print followers
                    16. Update company name
                    17. Exit
                    18. Enter a choice (integer):\s""";
            Print.printSameLine(beginningMenu);
            int option = reader.nextInt();
            reader.nextLine();

            switch (option) {
                case 1:
                    // get user's information
                    Print.printSameLine("Enter firstname: ");
                    String firstname = reader.nextLine();
                    Print.printSameLine("Enter lastname: ");
                    String lastname = reader.nextLine();

                    String username;
                    boolean repeatUsername = false;
                    do {
                        Print.printSameLine("Enter username: ");
                        username = reader.nextLine();

                        // add error check for already existing username
                        User newUser = createNewUser(firstname, lastname, username, allUsers, true);
                        Print.print("Username already exists, please choose another username.");
                        if (newUser == null) repeatUsername = true;
                    } while (repeatUsername);

                    Print.print("User " + username + " was created successfully.");

                    repeat = true;
                    break;
                case 2:
                    String name;
                    boolean repeatName = false;
                    do {
                        // get company's information
                        Print.printSameLine("Enter company name: ");
                        name = reader.nextLine();

                        // add error check for already existing company
                        Company newCompany = createNewCompany(name, allCompanies, true);
                        if (newCompany == null) {
                            Print.print("Company name already exists.");
                            repeatName = true;
                        } else {
                            repeat = false;
                        }
                    } while (repeatName);

                    Print.print("Company " + name + " was created successfully.");

                    repeat = true;
                    break;
                case 3:
                    Print.printSameLine("Enter the first user's username: ");
                    String user1 = reader.nextLine();
                    Print.printSameLine("Enter the second user's username: ");
                    String user2 = reader.nextLine();

                    User u1 = MainHelper.getUser(user1, allUsers);
                    User u2 = MainHelper.getUser(user2, allUsers);

                    // if u1 or u2 is null repeat
                    u1.addFriend(u2, allUsers, true);
                    Print.print(user1 + " and " + user2 + " are now friends.");

                    repeat = true;
                    break;
//                case 4:
//                    Print.printSameLine("Enter the first user's username: ");
//                    String user3 = reader.nextLine();
//                    Print.printSameLine("Enter the second user's username: ");
//                    String user4 = reader.nextLine();
//                    repeat = false;
//                    break;
//                case 17:
//                    repeat = false;
//                    break;
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
