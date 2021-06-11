package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MainHelperTest {
    @Test
    public void isValidUserTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new user
        Main.createNewUser("apinan", "yogaratnam", "apinanyogaratnam", allUsers, false);

        // checking if new user is a valid user
        boolean isUser = MainHelper.isValidUser("apinanyogaratnam", allUsers);
        assertTrue(isUser);

        // checking if api is a valid user
        isUser = MainHelper.isValidUser("api", allUsers);
        assertFalse(isUser);

        // checking if null is a valid user
        String username = null;
        isUser = MainHelper.isValidUser(username, allUsers);
        assertFalse(isUser);
    }

    @Test
    public void isValidCompanyTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        // creating a new company
        Main.createNewCompany("McDonald's", allCompanies, false);

        // checking if new company is a new company
        boolean isCompany = MainHelper.isValidCompany("McDonald's", allCompanies);
        assertTrue(isCompany);

        // checking if tim hortons is a valid company
        isCompany = MainHelper.isValidCompany("tim hortons", allCompanies);
        assertFalse(isCompany);

        // checking if null is a valid company
        String companyName = null;
        isCompany = MainHelper.isValidCompany(companyName, allCompanies);
        assertFalse(isCompany);
    }

    @Test
    void getCountOfAllUsersTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new users
        Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        Main.createNewUser("stewie", "angel", "stewietheangel", allUsers, false);
        Main.createNewUser("walter", "white", "heisenborg", allUsers, false);
        Main.createNewUser("local", "librarian", "yourlocallibrarian", allUsers, false);

        int count = MainHelper.getCountOfAllUsers(allUsers);
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
        assertNull(receivedStewie);

        // checking if apinan exists
        assertEquals(apinan, receivedApinan);

        // creating null user
        User nullUser = MainHelper.getUser(null, allUsers);

        // checking if recieved null user
        assertNull(nullUser);
    }

    @Test
    void getCompanyTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        // creating and getting companies
        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company receivedTims = MainHelper.getCompany("Tim Hortons", allCompanies);
        Company receivedMcd = MainHelper.getCompany("McDonald's", allCompanies);

        // checking if tims exists
        assertNull(receivedTims);

        // checking if mcd exists
        assertEquals(mcd, receivedMcd);

        // creating null company
        Company nullCompany = MainHelper.getCompany(null, allCompanies);

        // checking if recieved null company
        assertNull(nullCompany);
    }

    @Test
    void nameInListTest() {
        String string = "apinan,stewietheangel,walter";

        // checking if apinan in list
        boolean apinanInList = MainHelper.nameInList("apinan", string);
        assertTrue(apinanInList);

        // checking if unknown is in list
        boolean unknownInList = MainHelper.nameInList("Unknown", string);
        assertFalse(unknownInList);
    }
}