package com.apinanyogaratnam;

import java.util.LinkedList;

public class MainHelper {
    public static boolean isValidUser(String possibleUsername, LinkedList<User> allUsers) {
        if (possibleUsername == null) return false;

        for (User user : allUsers) {
            if (possibleUsername.equals(user.getUsername())) return true;
        }

        return false;
    }

    public static boolean isValidCompany(String name, LinkedList<Company> allCompanies) {
        if (name == null) return false;

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

        for (User user : allUsers) {
            if (username.equals(user.getUsername())) return user;
        }

        return null;
    }

    public static Company getCompany(String name, LinkedList<Company> allCompanies) {
        if (name == null) return null;

        for (Company company : allCompanies) {
            if (name.equals(company.getName())) return company;
        }

        return null;
    }

    public static boolean nameInList(String friendUsername, String listOfFriendsInStringFormat) {
        String [] strings = listOfFriendsInStringFormat.split(",");

        for (int i=0; i<strings.length; i++) {
            if (strings[i].equals(friendUsername)) return true;
        }

        return false;
    }



}
