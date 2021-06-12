package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void getNameTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);

        // check if correct name is received
        assertEquals("McDonald''s", mcd.getName());
    }

    @Test
    void getNetworksListTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company tims = Main.createNewCompany("Tims Hortons", allCompanies, false);

        mcd.addNetwork(tims, allCompanies, false);

        // check if tims is in networksList
        assertEquals(1, mcd.getNetworksList().size());
        assertTrue(mcd.getNetworksList().contains(tims));
    }

    @Test
    void getFollowersListTest() {
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
    void hasFollowerTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        User api = Main.createNewUser("api", "yoga", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("stewie", "griffin", "stewiethenangel", allUsers, false);

        api.addCompany(mcd, allCompanies, false);

        // check if mcd has api as a follower
        assertTrue(mcd.hasFollower(api));

        // check if stewie a follower of mcd
        assertFalse(mcd.hasFollower(stewie));
    }

    @Test
    void addNetworkTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company tims = Main.createNewCompany("Tim Hortons", allCompanies, false);
        Company apple = Main.createNewCompany("apple", allCompanies, false);

        mcd.addNetwork(tims, allCompanies, false);

        // check if mcd is networking with tims
        assertTrue(mcd.hasNetwork(tims));

        // check if apple is networking with mcd
        assertFalse(mcd.hasNetwork(apple));
    }

    @Test
    void addFollowerTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        User api = Main.createNewUser("api", "yoga", "apinanyogaratnam", allUsers, false);
        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);

        mcd.addFollower(api, false);

        // check if mcd has follower api
        assertTrue(mcd.hasFollower(api));
    }

    @Test
    void loadNetworksTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();
        String list = "{McDonald''s,Tim Hortons}";

        Company tims = Main.createNewCompany("Tim Hortons", allCompanies, false);
        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company apple = Main.createNewCompany("apple", allCompanies, false);
        apple.loadNetworks(list, allCompanies);

        // check if apple is networking with mcd and tims
        assertTrue(apple.hasNetwork(tims));
        assertTrue(apple.hasNetwork(mcd));
    }

    @Test
    void loadFollowersTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();
        String list = "{apinanyogaratnam,stewietheangel,not a real user}";

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        User api = Main.createNewUser("apinan", "yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("stewie", "angel", "stewietheangel", allUsers, false);

        // check if api and stewie are added as followers
        mcd.loadFollowers(list, allUsers);
        assertTrue(mcd.hasFollower(api));
        assertTrue(mcd.hasFollower(stewie));
        assertEquals(2, mcd.getFollowersList().size());
    }

    @Test
    void updateNameTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company apple = Main.createNewCompany("apple", allCompanies, false);
        mcd.updateName("McDonalds", allCompanies, false);

        // check if mcd updated its name accordingly
        assertEquals("McDonalds", mcd.getName());

        // check if can update already existing name
        mcd.updateName("apple", allCompanies, false);
        assertEquals("McDonalds", mcd.getName());
    }

    @Test
    void removeNetworkTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company apple = Main.createNewCompany("apple", allCompanies, false);
        Company random = Main.createNewCompany("random", allCompanies, false);

        mcd.addNetwork(apple, allCompanies, false);
        boolean removed = mcd.removeNetwork(apple, allCompanies, false);

        // check if apple is still in mcd networks
        assertFalse(mcd.hasNetwork(apple));
        assertTrue(removed);

        // check if mcd and remove a non-existing netowrk
        assertFalse(mcd.removeNetwork(random, allCompanies, false));
    }

    @Test
    void deleteCompanyTest() {
        LinkedList<Company> allCompanies = new LinkedList<>();

        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        Company apple = Main.createNewCompany("apple", allCompanies, false);
        mcd.addNetwork(apple, allCompanies, false);
        mcd.deleteCompany(allCompanies, false);

        // check if deleted from allCompanies
        assertFalse(allCompanies.contains(mcd));

        // check if apple is still netowrking with mcd
        assertFalse(apple.hasNetwork(mcd));
    }

    @Test
    void suggestNetworksTest() {
    }
}