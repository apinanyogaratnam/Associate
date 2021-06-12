package com.apinanyogaratnam;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        // load data from db to current data structures
        CreateSQL.loadDB(allUsers, allCompanies);
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
