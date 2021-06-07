package com.apinanyogaratnam;

import java.util.LinkedList;
import java.sql.*;

public class Main {
    private static final Secrets secrets = new Secrets();
    private static final SQL sql = new SQL();

    public static void main(String[] args) {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        // load data from db to current variables
        sql.loadDBUserData(allUsers);
        sql.loadDBCompanyData(allCompanies, allUsers);
        User stewie = MainHelper.getUser("stewietheangel", allUsers);
        stewie.updateFirstName("baby");
        stewie.updateLastName("stews");
    }

    public static User createNewUser(String firstName, String lastName, String username, LinkedList<User> allUsers, boolean withSQL) {
        if (MainHelper.isValidUser(username, allUsers)) return null;
        if (firstName == null || lastName == null) return null;
        User newUser = new User(firstName, lastName, username, allUsers);

        // if user already exists, nothing happens
        if (withSQL) sql.addObjectToDB(newUser);

        return newUser;
    }

    public static Company createNewCompany(String name, LinkedList<Company> allCompanies, boolean withSQL) {
        if (MainHelper.isValidCompany(name, allCompanies)) return null;
        Company newCompany = new Company(name, allCompanies);

        // if company already exists, nothing happens
        if (withSQL) sql.addObjectToDB(newCompany);

        return newCompany;
    }

}
