package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MainHelperTest {
    private static MainHelper mainHelperMethod = new MainHelper();
    static Main mainMethod = new Main();

    @Test
    public void isValidUserTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new user
        User apinan = mainMethod.createNewUser("apinan", "yogaratnam", "apinanyogaratnam", allUsers, false);

        // checking if new user is a valid user
        boolean isUser = mainHelperMethod.isValidUser(apinan.username, allUsers);
        assertEquals(true, isUser);

        // checking if api is a valid user
        isUser = mainHelperMethod.isValidUser("api", allUsers);
        assertEquals(false, isUser);

        // checking if null is a valid user
        String username = null;
        isUser = mainHelperMethod.isValidUser(username, allUsers);
        assertEquals(false, isUser);
    }

    @Test
    public void isValidCompanyTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        // creating a new company
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies, false);

        // checking if new company is a new company
        boolean isCompany = mainHelperMethod.isValidCompany(mcdonald.name, allCompanies);
        assertEquals(true, isCompany);

        // checking if tim hortons is a valid company
        isCompany = mainHelperMethod.isValidCompany("tim hortons", allCompanies);
        assertEquals(false, isCompany);

        // checking if null is a valid company
        String companyName = null;
        isCompany = mainHelperMethod.isValidCompany(companyName, allCompanies);
        assertEquals(false, isCompany);
    }

    @Test
    void getCountOfAllUsersTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new users
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("stewie", "angel", "stewietheangel", allUsers, false);
        User walter = Main.createNewUser("walter", "white", "heisenborg", allUsers, false);
        User local = Main.createNewUser("local", "librarian", "yourlocallibrarian", allUsers, false);

        int count = mainHelperMethod.getCountOfAllUsers(allUsers);
        assertEquals(4, count);
    }

    @Test
    void getUserTest() {

    }
}