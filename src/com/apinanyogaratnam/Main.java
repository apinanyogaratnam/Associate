package com.apinanyogaratnam;

import java.util.LinkedList;
import java.sql.*;

public class Main {
    private static MainHelper helperMethods = new MainHelper();
    private static Print printClass = new Print();

    public static void main(String[] args) {
        try {
            // get a connection to database
            Secrets secrets = new Secrets();
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // execute SQL query
            String query = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(query);

            // process the result set
            while (result.next()) {
                printClass.print(result.getString("first_name") + ", " +
                        result.getString("last_name") + ", " + result.getString("username"));
            }

            printClass.print("------------------------------------");

            // insert into database
            statement.executeUpdate(secrets.query);

            // execute SQL query
            query = "SELECT * FROM users";
            result = statement.executeQuery(query);

            // process the result set
            while (result.next()) {
                printClass.print(result.getString("first_name") + ", " +
                        result.getString("last_name") + ", " + result.getString("username"));
            }

            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

    }

    public static User createNewUser(String firstName, String lastName, String username, LinkedList<User> allUsers) {
        Print printClass = new Print();
        if (helperMethods.isValidUser(username, allUsers)) return null;
        if (firstName == null || lastName == null) return null;
        User newUser = new User(firstName, lastName, username, allUsers);

        return newUser;
    }

    public static Company createNewCompany(String name, LinkedList<Company> allCompanies) {
        Print printClass = new Print();
        if (helperMethods.isValidCompany(name, allCompanies)) return null;
        Company newCompany = new Company(name, allCompanies);

        return newCompany;
    }

}
