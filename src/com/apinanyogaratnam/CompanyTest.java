package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void getName() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);

        // check if correct name is received
        assertEquals("McDonald''s", mcd.getName());
    }

    @Test
    void getNetworksList() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company tims = Main.createNewCompany("Tims Hortons", allCompanies, false);

        mcd.addNetwork(tims, allCompanies, false);

        // check if tims is in networksList
        assertEquals(1, mcd.getNetworksList().size());
        assertTrue(mcd.getNetworksList().contains(tims));
    }

    @Test
    void getFollowersList() {
        LinkedList<Company> allCompanies = new LinkedList<>();
        LinkedList<User> allUsers = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        User api = Main.createNewUser("api", "yoga", "apinanyogaratnam", allUsers, false);
        api.addCompany(mcd, allCompanies, false);

        // check if api is in followersList
        assertEquals(1, mcd.getFollowersList().size());
        assertEquals(api, mcd.getFollowersList().get(0));
    }

    @Test
    void hasFollower() {
    }

    @Test
    void addNetwork() {
    }

    @Test
    void addFollower() {
    }

    @Test
    void loadNetworks() {
    }

    @Test
    void loadFollowers() {
    }

    @Test
    void updateName() {
    }

    @Test
    void removeNetwork() {
    }

    @Test
    void deleteCompany() {
    }

    @Test
    void suggestNetworks() {
    }
}