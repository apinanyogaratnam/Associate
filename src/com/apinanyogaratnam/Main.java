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

        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // execute SQL query
            String query = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(query);

            // process the result set
            while (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String username = result.getString("username");
//                printClass.print(firstName + ", " + lastName + ", " + username);
            }

            printClass.print("------------------------------------");

            // insert into database
            String query = "INSERT INTO users (\n" +
                    "    first_name,\n" +
                    "    last_name,\n" +
                    "    username\n" +
                    ")\n" +
                    "VALUES ('stewie', 'angel', 'stewietheangel');";
            statement.executeUpdate(query);

            // execute SQL query
            query = "SELECT * FROM users";
            result = statement.executeQuery(query);

            // process the result set
            while (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String username = result.getString("username");
//                printClass.print(firstName + ", " + lastName + ", " + username);

                // create users and add users to allUsers
                User stews = createNewUser(firstName, lastName, username, allUsers);
                stews.print(allUsers);
            }

            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static User createNewUser(String firstName, String lastName, String username, LinkedList<User> allUsers) {
        if (helperMethods.isValidUser(username, allUsers)) return null;
        if (firstName == null || lastName == null) return null;
        User newUser = new User(firstName, lastName, username, allUsers);

        return newUser;
    }

    public static Company createNewCompany(String name, LinkedList<Company> allCompanies) {
        if (helperMethods.isValidCompany(name, allCompanies)) return null;
        Company newCompany = new Company(name, allCompanies);

        return newCompany;
    }

}
