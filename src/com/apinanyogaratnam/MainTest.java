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

        // creates new users and companies
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);

        // creates null
        User apinanCopy = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);

        assertEquals(apinanCopy, null);
    }

    @Test
    public void duplicateCompanyTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        // creates new users and companies
        Company timhortons = mainMethod.createNewCompany("Tim Hortons", allCompanies);
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies);

        // creates null
        Company timhortonsCopy = mainMethod.createNewCompany("Tim Hortons", allCompanies);

        assertEquals(timhortonsCopy, null);
    }
}