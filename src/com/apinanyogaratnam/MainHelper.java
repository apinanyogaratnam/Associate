package com.apinanyogaratnam;

import java.util.LinkedList;

public class MainHelper {
    public static boolean isValidUser(String possibleUsername, LinkedList<User> allUsers) {
        for (User user : allUsers) {
            if (possibleUsername.equals(user.username)) return true;
        }

        return false;
    }

    public static boolean isValidCompany(String name, LinkedList<Company> allCompanies) {
        for (Company company : allCompanies) {
            if (name.equals(company.name)) return true;
        }

        return false;
    }
}
