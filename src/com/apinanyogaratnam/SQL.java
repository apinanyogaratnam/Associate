package com.apinanyogaratnam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class SQL {
    private static Secrets secrets = new Secrets();
    private static Print printClass = new Print();

    public void addUserToDB(User user) {
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // data to insert into database
            String query = String.format("INSERT INTO users (first_name, last_name, username, friends) VALUES " +
                    "(\"%s\", \"%s\", \"%s\", \"%s\");", user.firstName, user.lastName, user.username, "{}");

            // insert data into database
            statement.executeUpdate(query);

            // close connection to server
            connection.close();
        } catch(SQLIntegrityConstraintViolationException e) {
            printClass.print("object already exists in db.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCompanyToDB(Company company) {
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.url, secrets.username, secrets.password);

            // create a statement
            Statement statement = connection.createStatement();

            // data to insert into database
            String query = String.format("INSERT INTO companies (name, network_list, followers_list) VALUES " +
                    "(\"%s\", \"%s\", \"%s\");", company.name, "{}", "{}");

            // insert data into database
            statement.executeUpdate(query);

            // close connection to server
            connection.close();
        } catch(SQLIntegrityConstraintViolationException e) {
            printClass.print("object already exists in db.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addObjectToDB(Object obj) {
        if (obj == Company) {

        }
    }
}
