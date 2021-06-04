package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static MainHelper helperMethods = new MainHelper();
    static Main mainMethod = new Main();

    @Test
    public void duplicateUsernameTest() {// creates new users and companies
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies);

        // creates null
        User api = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);

        assertEquals(api, null);
    }
}