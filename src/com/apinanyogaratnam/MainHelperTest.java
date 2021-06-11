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
        boolean isUser = mainHelperMethod.isValidUser(apinan.getUsername(), allUsers);
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
        boolean isCompany = mainHelperMethod.isValidCompany(mcdonald.getName(), allCompanies);
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
        LinkedList<User> allUsers = new LinkedList<>();

        // creating and getting users
        User apinan = Main.createNewUser("apinan", "yogaratnam", "apinanyogaratnam", allUsers, false);
        User receivedStewie = MainHelper.getUser("stewietheangel", allUsers);
        User receivedApinan = MainHelper.getUser("apinanyogaratnam", allUsers);

        // checking if there exists a stewie user
        assertEquals(null, receivedStewie);

        // checking if apinan exists
        assertEquals(apinan, receivedApinan);

        // creating null user
        User nullUser = MainHelper.getUser(null, allUsers);

        // checking if recieved null user
        assertEquals(null, nullUser);
    }

    @Test
    void getCompanyTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        // creating and getting companies
        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company receivedTims = MainHelper.getCompany("Tim Hortons", allCompanies);
        Company receivedMcd = MainHelper.getCompany(mcd.getName(), allCompanies);

        // checking if tims exists
        assertEquals(null, receivedTims);

        // checking if mcd exists
        assertEquals(mcd, receivedMcd);

        // creating null company
        Company nullCompany = MainHelper.getCompany(null, allCompanies);

        // checking if recieved null company
        assertEquals(null, nullCompany);
    }

    @Test
    void nameI
}