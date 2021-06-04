package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static MainHelper helperMethods = new MainHelper();
    static Main mainMethod = new Main();

    @Test
    public void duplicateUsernameTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new user
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);

        // create duplicate user
        User apinanCopy = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);

        assertEquals(apinanCopy, null);
    }

    @Test
    public void duplicateCompanyTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        // creates new companies
        Company timhortons = mainMethod.createNewCompany("Tim Hortons", allCompanies);
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies);

        // create duplicate company
        Company timhortonsCopy = mainMethod.createNewCompany("Tim Hortons", allCompanies);

        assertEquals(timhortonsCopy, null);
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

        assertEquals(foundUser, true);
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

        assertEquals(foundCompany, true);
    }
}