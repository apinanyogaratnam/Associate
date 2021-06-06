package com.apinanyogaratnam;

import java.sql.*;
import java.util.LinkedList;

public class SQL {
    private static final Secrets secrets = new Secrets();

    public void updateDBWithQuery(String query) {
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // insert data into database
            statement.executeUpdate(query);


            // close connection to server
            connection.close();
        } catch(SQLIntegrityConstraintViolationException e) {
            Print.print("Object already exists in db.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addObjectToDB(Object obj) {
        String query = "";
        String errorMessage = "already exists in db.";

        // set desired query and error message for adding object to db
        if (obj instanceof User) {
            query = String.format("INSERT INTO users (first_name, last_name, username, friends) VALUES " +
                    "(\"%s\", \"%s\", \"%s\", \"%s\");", ((User) obj).firstName, ((User) obj).lastName, ((User) obj).username, "{}");
            errorMessage = "User " + errorMessage;
        } else if (obj instanceof Company) {
            query = String.format("INSERT INTO companies (name, network_list, followers_list) VALUES " +
                    "(\"%s\", \"%s\", \"%s\");", ((Company) obj).name, "{}", "{}");
            errorMessage = "Company " + errorMessage;
        } else {
            Print.print("Object type not supported to add to db.");
            return;
        }

        updateDBWithQuery(query);
    }

    public void removeObjectFromDB(Object obj) {
        String query = "";

        if (obj instanceof User) {
            query = String.format("INSERT INTO users (first_name, last_name, username, friends) VALUES " +
                    "(\"%s\", \"%s\", \"%s\", \"%s\");", ((User) obj).firstName, ((User) obj).lastName, ((User) obj).username, "{}");
        } else if (obj instanceof Company) {
            query = String.format("INSERT INTO companies (name, network_list, followers_list) VALUES " +
                    "(\"%s\", \"%s\", \"%s\");", ((Company) obj).name, "{}", "{}");
        } else {
            Print.print("Object type not supported to add to db.");
        }

        // complete method
    }

    public void updateUsername(User user, String newUsername) {
        // need to also update every friend's list
        // and companies follwers list
        String query = String.format("UPDATE users SET username=\"%s\" WHERE username=\"%s\"", newUsername, user.username);
        updateDBWithQuery(query);
        // complete
    }

    public void updateFirstName(User user, String newFirstName) {
        String query = String.format("UPDATE users SET first_name=\"%s\" WHERE first_name=\"%s\"", newFirstName, user.firstName);
        updateDBWithQuery(query);
    }

    public void updateLastName(User user, String newLastName) {
        String query = String.format("UPDATE users SET last_name=\"%s\" WHERE last_name=\"\"", newLastName, user.lastName);
        updateDBWithQuery(query);
    }

    public void updateFriendHelper(User user, User friend) {
        String listOfFriendsInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // insert data into database
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            while (result.next()) {
                String username = result.getString("username");
                if (username.equals(user.username)) {
                    listOfFriendsInStringFormat = result.getString("friends");
                }
            }


            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if user already is friends with friend
        String temp = listOfFriendsInStringFormat.substring(1, listOfFriendsInStringFormat.length()-1);
        if (MainHelper.nameInList(friend.username, temp)) return;

        String friends = listOfFriendsInStringFormat;
        boolean isEmpty = friends.equals("{}");

        // format string
        friends = friends.substring(0, friends.length() - 1);
        friends = isEmpty ? friends + friend.username + "}" : friends + "," + friend.username + "}";

        // update user data
        String query = String.format("UPDATE users SET friends=\"%s\" WHERE username=\"%s\"", friends, user.username);
        updateDBWithQuery(query);
    }

    public void updateFriend(User user, User friend) {
        updateFriendHelper(user, friend);
        updateFriendHelper(friend, user);
    }

    public void updateName(Company company, String newName) {
        // when updating name, need to also update netowrks list and all go through
        // all followers and change their companiesList as well
        // need to also check if company name already exists
        String query = String.format("UPDATE company SET name=\"%s\" WHERE name=\"%s\"", newName, company.name);
        updateDBWithQuery(query);
        // complete
    }

     public void updateNetworksHelper(Company company, Company network) {
         String listOfNetworksInStringFormat = "";
         try {
             // get a connection to database
             Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

             // create a statement
             Statement statement = connection.createStatement();

             // insert data into database
             ResultSet result = statement.executeQuery("SELECT * FROM companies");
             while (result.next()) {
                 String username = result.getString("name");
                 if (username.equals(company.name)) {
                     listOfNetworksInStringFormat = result.getString("network_list");
                 }
             }


             // close connection to server
             connection.close();
         } catch (Exception e) {
             e.printStackTrace();
         }

         // check if user already is friends with friend
         String temp = listOfNetworksInStringFormat.substring(1, listOfNetworksInStringFormat.length()-1);
         if (MainHelper.nameInList(network.name, temp)) return;

         String networks = listOfNetworksInStringFormat;
         boolean isEmpty = networks.equals("{}");

         // format string
         networks = networks.substring(0, networks.length() - 1);
         networks = isEmpty ? networks + network.name + "}" : networks + "," + network.name + "}";

         // update user data
         String query = String.format("UPDATE companies SET networks=\"%s\" WHERE name=\"%s\"", networks, company.name);
         updateDBWithQuery(query);
     }

     public void updateNetworks(Company company, Company network) {
        updateNetworksHelper(company, network);
        updateNetworksHelper(network, company);
    }

    public void updateFollowers() {

    }

    public void updateCompany(User user, Company company) {
        String listOfCompaniesInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // insert data into database
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            while (result.next()) {
                String username = result.getString("username");
                if (username.equals(user.username)) {
                    listOfCompaniesInStringFormat = result.getString("companies");
                }
            }


            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if user already is friends with friend
        String temp = listOfCompaniesInStringFormat.substring(1, listOfCompaniesInStringFormat.length()-1);
        if (MainHelper.nameInList(company.name, temp)) return;

        String companies = listOfCompaniesInStringFormat;
        boolean isEmpty = companies.equals("{}");

        // format string
        companies = companies.substring(0, companies.length() - 1);
        companies = isEmpty ? companies + company.name + "}" : companies + "," + company.name + "}";

        // update user data
        String query = String.format("UPDATE users SET companies=\"%s\" WHERE username=\"%s\"", companies, user.username);
        updateDBWithQuery(query);
    }


}
