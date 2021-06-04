package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static MainHelper mainHelperMethod = new MainHelper();
    static Main mainMethod = new Main();

    @Test
    public void duplicateUsernameTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new user
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);

        // create duplicate user
        User apinanCopy = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);

        assertEquals(null, apinanCopy);
    }

    @Test
    public void duplicateCompanyTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        // creates new companies
        Company timhortons = mainMethod.createNewCompany("Tim Hortons", allCompanies);
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies);

        // create duplicate company
        Company timhortonsCopy = mainMethod.createNewCompany("Tim Hortons", allCompanies);

        assertEquals(null, timhortonsCopy);
    }

    @Test
    public void userInAllUsersTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new user
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);

        // search through allUsers for new user
        boolean foundUser = false;
        for (User user : allUsers) {
            if (user.username.equals(apinan.username)) {
                foundUser = true;
                break;
            }
        }

        assertEquals(true, foundUser);
    }

    @Test
    public void companyInAllCompaniesTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        // create new company
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies);

        // search through allCompanies for new company
        boolean foundCompany = false;
        for (Company company : allCompanies) {
            if (company.name.equals(mcdonald.name)) {
                foundCompany = true;
                break;
            }
        }

        assertEquals(true, foundCompany);
    }

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

}