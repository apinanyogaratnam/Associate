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
                    17. Print everything in db
                    18. Exit
                    Enter a choice (integer):\s""";
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
                    boolean repeatUsername;
                    do {
                        Print.printSameLine("Enter username: ");
                        username = reader.nextLine();

                        // add error check for already existing username
                        User newUser = createNewUser(firstname, lastname, username, allUsers, true);

                        if (newUser == null) {
                            Print.print("Username already exists, please choose another username.");
                            repeatUsername = true;
                        }
                        else repeatUsername = false;
                    } while (repeatUsername);

                    Print.print("User " + username + " was created successfully.");

                    repeat = true;
                    break;
                case 2:
                    String name;
                    boolean repeatName;
                    do {
                        // get company's information
                        Print.printSameLine("Enter company name: ");
                        name = reader.nextLine();

                        // add error check for already existing company
                        Company newCompany = createNewCompany(name, allCompanies, true);

                        if (newCompany == null) {
                            Print.print("Company name already exists.");
                            repeatName = true;
                        } else repeatName = false;
                    } while (repeatName);

                    Print.print("Company " + name + " was created successfully.");

                    repeat = true;
                    break;
                case 3:
                    String user1, user2;
                    User u1, u2;
                    do {
                        Print.printSameLine("Enter the first user's username: ");
                        user1 = reader.nextLine();
                        Print.printSameLine("Enter the second user's username: ");
                        user2 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        u2 = MainHelper.getUser(user2, allUsers);
                        if (u1 == null || u2 == null) Print.print("first or second user's username doesn't exist.");
                    } while (u1 == null || u2 == null);

                    u1.addFriend(u2, allUsers, true);
                    Print.print(user1 + " and " + user2 + " are now friends.");

                    repeat = true;
                    break;
                case 4:
                    do {
                        Print.printSameLine("Enter the first user's username: ");
                        user1 = reader.nextLine();
                        Print.printSameLine("Enter the second user's username: ");
                        user2 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        u2 = MainHelper.getUser(user2, allUsers);
                        if (u1 == null || u2 == null) Print.print("first or second user's username doesn't exist.");
                    } while (u1 == null || u2 == null);

                    u1.removeFriend(u2, allUsers, true);
                    Print.print(user1 + " and " + user2 + " friendship removed.");

                    repeat = true;
                    break;
                case 5:
                    String company1;
                    Company c1;
                    do {
                        Print.printSameLine("Enter user's username: ");
                        user1 = reader.nextLine();
                        Print.printSameLine("Enter company's name: ");
                        company1 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        c1 = MainHelper.getCompany(company1, allCompanies);
                        if (u1 == null || c1 == null) Print.print("user or company doesn't exist.");
                    } while (u1 == null || c1 == null);

                    u1.addCompany(c1, allCompanies, true);
                    Print.print("company added successfully.");

                    repeat = true;
                    break;
                case 6:
                    do {
                        Print.printSameLine("Enter user's username: ");
                        user1 = reader.nextLine();
                        Print.printSameLine("Enter company's name: ");
                        company1 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        c1 = MainHelper.getCompany(company1, allCompanies);
                        if (u1 == null || c1 == null) Print.print("user or company doesn't exist.");
                    } while (u1 == null || c1 == null);

                    u1.removeCompany(c1, allCompanies, true);

                    Print.print("company removed successfully.");

                    repeat = true;
                    break;
                case 7:
                    do {
                        Print.printSameLine("Enter user's username: ");
                        user1 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        if (u1 == null) Print.print("user doesn't exist.");
                    } while(u1 == null);

                    Print.print(u1.getFriendsList());

                    repeat = true;
                    break;
                case 8:
                    do {
                        Print.printSameLine("Enter user's username");
                        user1 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        if (u1 == null) Print.print("user doesn't exist.");
                    } while (u1 == null);

                    Print.print(u1.getCompaniesList());

                    repeat = true;
                    break;
                case 9:
                    do {
                        Print.printSameLine("Enter user's name: ");
                        user1 = reader.nextLine();

                        Print.printSameLine("Enter user's new firstname: ");
                        user2 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        if (u1 == null) Print.print("user doesn't exist.");
                    } while (u1 == null);

                    u1.updateFirstName(user2, true);

                    Print.print("first name updated successfully.");

                    repeat = true;
                    break;
                case 10:
                    do {
                        Print.printSameLine("Enter user's name: ");
                        user1 = reader.nextLine();

                        Print.printSameLine("Enter user's new lastname: ");
                        user2 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        if (u1 == null) Print.print("user doesn't exist.");
                    } while (u1 == null);

                    u1.updateLastName(user2, true);
                    Print.print("last name updated successfully.");

                    repeat = true;
                    break;
                case 11:
                    do {
                        Print.printSameLine("Enter user's name: ");
                        user1 = reader.nextLine();

                        // if user is null repeat
                        Print.printSameLine("Enter user's new username: ");
                        user2 = reader.nextLine();

                        u1 = MainHelper.getUser(user1, allUsers);
                        if (u1 == null) Print.print("user doesn't exist.");
                        else {
                            u2 = MainHelper.getUser(user2, allUsers);
                            if (u2 == null) {
                                Print.print("username already exists.");
                                u1 = null;
                            }
                        }
                    } while (u1 == null);

                    u1.updateUsername(user2, allUsers, true);
                    Print.print("username updated successfully.");

                    repeat = true;
                    break;
                case 12:
                    Company c2;
                    do {
                        Print.printSameLine("Enter company's name: ");
                        company1 = reader.nextLine();
                        Print.printSameLine("Enter network's name: ");
                        String company2 = reader.nextLine();

                        c1 = MainHelper.getCompany(company1, allCompanies);
                        c2 = MainHelper.getCompany(company2, allCompanies);
                        if (c1 == null || c2 == null) Print.print("company or network doesn't exist.");
                    } while (c1 == null || c2 == null);

                    c1.addNetwork(c2, allCompanies, true);

                    Print.print("network added successfully.");

                    repeat = true;
                    break;
                case 13:
                    String company2;
                    do {
                        Print.printSameLine("Enter company's name: ");
                        company1 = reader.nextLine();
                        Print.printSameLine("Enter network's name: ");
                        company2 = reader.nextLine();

                        c1 = MainHelper.getCompany(company1, allCompanies);
                        c2 = MainHelper.getCompany(company2, allCompanies);
                        if (c1 == null || c2 == null) Print.print("company or network doesn't exist.");
                    } while (c1 == null || c2 == null);

                    c1.removeNetwork(c2, allCompanies, true);

                    Print.print("network removed successfully.");

                    repeat = true;
                    break;
                case 14:
                    do {
                        Print.printSameLine("Enter company's name: ");
                        company1 = reader.nextLine();

                        // if company1 is null repeat
                        c1 = MainHelper.getCompany(company1, allCompanies);
                        if (c1 == null) Print.print("company doesn't exist.");
                    } while (c1 == null);

                    Print.print(c1.getNetworksList());

                    repeat = true;
                    break;
                case 15:
                    do {
                        Print.printSameLine("Enter company's name: ");
                        company1 = reader.nextLine();

                        // if company1 is null repeat
                        c1 = MainHelper.getCompany(company1, allCompanies);
                        if (c1 == null) Print.print("company doesn't exist.");
                    } while (c1 == null);

                    Print.print(c1.getFollowersList());

                    repeat = true;
                    break;
                case 16:
                    do {
                        Print.printSameLine("Enter company's name: ");
                        company1 = reader.nextLine();

                        // if company is null repeat
                        Print.printSameLine("Enter company's new name: ");
                        company2 = reader.nextLine();

                        c1 = MainHelper.getCompany(company1, allCompanies);
                        if (c1 == null) {
                            Print.print("company doesn't exist.");
                        } else if (MainHelper.getCompany(company2, allCompanies) == null) {
                            Print.print("company already exists.");
                            c1 = null;
                        }
                    } while (c1 == null);
                    c1.updateName(company2, allCompanies, true);

                    Print.print("company's name updated successfully.");

                    repeat = true;
                    break;
                case 17:
                    Print.print(allUsers);
                    Print.print(allCompanies);

                    repeat = true;
                    break;
                case 18:
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
