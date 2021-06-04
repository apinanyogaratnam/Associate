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
        User apinan = mainMethod.createNewUser("apinan", "yogaratnam", "apinanyogaratnam", allUsers);

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
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies);

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
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        User stewie = mainMethod.createNewUser("stewie", "angel", "stewietheangel", allUsers);
        User walter = mainMethod.createNewUser("walter", "white", "heisenborg", allUsers);
        User local = mainMethod.createNewUser("local", "librarian", "yourlocallibrarian", allUsers);

        int count = mainHelperMethod.getCountOfAllUsers(allUsers);
        assertEquals(4, count);
    }
}