package com.apinanyogaratnam;

import java.util.LinkedList;
import java.sql.*;

public class Main {
    private static MainHelper helperMethods = new MainHelper();
    private static Print printClass = new Print();
    private static Secrets secrets = new Secrets();

    public static void main(String[] args) {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        loadDBData(allUsers);
    }

    public static User createNewUser(String firstName, String lastName, String username, LinkedList<User> allUsers) {
        if (helperMethods.isValidUser(username, allUsers)) return null;
        if (firstName == null || lastName == null) return null;
        User newUser = new User(firstName, lastName, username, allUsers);

        // if user already exists, nothing happens
        addUserToDB(newUser);

        return newUser;
    }

    public static Company createNewCompany(String name, LinkedList<Company> allCompanies) {
        if (helperMethods.isValidCompany(name, allCompanies)) return null;
        Company newCompany = new Company(name, allCompanies);

        return newCompany;
    }

    public static void loadDBData(LinkedList<User> allUsers) {
        try {
            // connect to database
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // execute SQL query
            String query = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String username = result.getString("username");
                String friends = result.getString("friends");
                printClass.print(firstName + ", " + lastName + ", " + username + ", " + friends);
            }

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            printClass.print("Unable to load users into allUsers");
        }
    }

    public static void addUserToDB(User user) {
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // data to insert into database
            String query = String.format("INSERT INTO users (first_name, last_name, username, friends) VALUES " +
                    "(%s, %s, %s, %s);", user.firstName, user.lastName, user.username, "{}");

            // insert data into database
            statement.executeUpdate(query);

            // close connection to server
            connection.close();
        } catch(Exception e) {
//            e.printStackTrace();
            printClass.print("Unable to add user to db.");
        }
    }

}
