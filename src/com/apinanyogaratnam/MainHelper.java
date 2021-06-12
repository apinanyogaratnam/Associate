package com.apinanyogaratnam;

import java.util.LinkedList;

public class MainHelper {
    public static boolean isValidUser(String possibleUsername, LinkedList<User> allUsers) {
        return getUser(possibleUsername, allUsers) != null;
    }

    public static boolean isValidCompany(String name, LinkedList<Company> allCompanies) {
        return getCompany(name, allCompanies) != null;
    }

    public static int getCountOfAllUsers(LinkedList<User> allUsers) {
        return allUsers.size();
    }

    public static User getUser(String username, LinkedList<User> allUsers) {
        if (username == null) return null;

        // searching for username
        username = Utils.parseString(username);
        for (User user : allUsers) {
            if (username.equals(user.getUsername())) return user;
        }

        return null;
    }

    public static Company getCompany(String name, LinkedList<Company> allCompanies) {
        if (name == null) return null;

        // searching for name
        name = Utils.parseString(name);
        for (Company company : allCompanies) {
            if (name.equals(company.getName())) return company;
        }

        return null;
    }

    public static boolean nameInList(String friendUsername, String listOfFriendsInStringFormat) {
        friendUsername = Utils.parseString(friendUsername);
        listOfFriendsInStringFormat = Utils.parseString(listOfFriendsInStringFormat);
        String [] strings = Utils.splitCommas(listOfFriendsInStringFormat);

        // searching for name in strings
        for (String string : strings) {
            if (string.equals(friendUsername)) return true;
        }

        return false;
    }
}
