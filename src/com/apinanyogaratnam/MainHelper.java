package com.apinanyogaratnam;

import java.util.LinkedList;

public class MainHelper {
    public static boolean isValidUser(String possibleUsername, LinkedList<User> allUsers) {
        if (possibleUsername == null) return false;

        for (User user : allUsers) {
            if (possibleUsername.equals(user.username)) return true;
        }

        return false;
    } // tested

    public static boolean isValidCompany(String name, LinkedList<Company> allCompanies) {
        if (name == null) return false;

        for (Company company : allCompanies) {
            if (name.equals(company.name)) return true;
        }

        return false;
    } // tested

    public static int getCountOfAllUsers(LinkedList<User> allUsers) {
        return allUsers.size();
    } // tested

}
