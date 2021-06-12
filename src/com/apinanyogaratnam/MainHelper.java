package com.apinanyogaratnam;

import java.util.LinkedList;

public class MainHelper {
    public static boolean isValidUser(String possibleUsername, LinkedList<User> allUsers) {
        if (possibleUsername == null) return false;

        possibleUsername = Utils.parseString(possibleUsername);
        for (User user : allUsers) {
            if (possibleUsername.equals(user.getUsername())) return true;
        }

        return false;
    } // tested

    public static boolean isValidCompany(String name, LinkedList<Company> allCompanies) {
        if (name == null) return false;

        name = Utils.parseString(name);
        for (Company company : allCompanies) {
            if (name.equals(company.getName())) return true;
        }

        return false;
    } // tested

    public static int getCountOfAllUsers(LinkedList<User> allUsers) {
        return allUsers.size();
    } // tested

    public static User getUser(String username, LinkedList<User> allUsers) {
        if (username == null) return null;

        username = Utils.parseString(username);
        for (User user : allUsers) {
            if (username.equals(user.getUsername())) return user;
        }

        return null;
    } // tested

    public static Company getCompany(String name, LinkedList<Company> allCompanies) {
        if (name == null) return null;

        name = Utils.parseString(name);
        for (Company company : allCompanies) {
            if (name.equals(company.getName())) return company;
        }

        return null;
    } // tested

    public static boolean nameInList(String friendUsername, String listOfFriendsInStringFormat) {
        String [] strings = Utils.splitCommas(listOfFriendsInStringFormat);

        friendUsername = Utils.parseString(friendUsername);
        listOfFriendsInStringFormat = Utils.parseString(listOfFriendsInStringFormat);
        for (String string : strings) {
            if (string.equals(friendUsername)) return true;
        }

        return false;
    } // tested
}
