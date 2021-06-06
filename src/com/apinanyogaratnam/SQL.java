package com.apinanyogaratnam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class SQL {
    private static Secrets secrets = new Secrets();
    private static Print printClass = new Print();

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
            printClass.print("Object type not supported to add to db.");
        }

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
            printClass.print(errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // complete method
    public void removeObjectFromDB(Object obj) {
        String query = "";

        if (obj instanceof User) {
            query = String.format("INSERT INTO users (first_name, last_name, username, friends) VALUES " +
                    "(\"%s\", \"%s\", \"%s\", \"%s\");", ((User) obj).firstName, ((User) obj).lastName, ((User) obj).username, "{}");
        } else if (obj instanceof Company) {
            query = String.format("INSERT INTO companies (name, network_list, followers_list) VALUES " +
                    "(\"%s\", \"%s\", \"%s\");", ((Company) obj).name, "{}", "{}");
        } else {
            printClass.print("Object type not supported to add to db.");
        }

    }

    public void updateObjectFromDB(Object obj) {
        String query = "";

        if (obj instanceof User) {
            query = "UPDATE "
        }
    }
}
