package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void getFirstNameTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);

        // checking if Apinan is given
        String username = apinan.getFirstName();
        assertEquals("Apinan", username);
    }

    @Test
    void getLastNameTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);

        // checking if Yogaratnam is given
        String username = apinan.getLastName();
        assertEquals("Yogaratnam", username);
    }

    @Test
    void getUsernameTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);

        // checking if apinanyogaratnam is given
        String username = apinan.getUsername();
        assertEquals("apinanyogaratnam", username);
    }

    @Test
    void getFriendsListTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("stewie", "angel", "stewietheangel", allUsers, false);
        apinan.addFriend(stewie, allUsers, false);

        // check if apinan is following stewie only and stewie is only in apinan's friend list
        assertEquals(1, apinan.getFriendsList().size());
        assertEquals(stewie, apinan.getFriendsList().get(0));
    }

    @Test
    void getCompaniesListTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        Company apple = Main.createNewCompany("apple", allCompanies, false);
        apinan.addCompany(apple, allCompanies, false);

        // check if apinan is following apple only
        assertEquals(1, apinan.getCompaniesList().size());
        assertEquals(apple, apinan.getCompaniesList().get(0));
    }

    @Test
    void isFollowingUserTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new users
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers, false);

        // after adding a friend, check if friend is following
        apinan.addFriend(stewie, allUsers, false);
        boolean isFollowing = apinan.isFollowingUser(stewie);
        assertTrue(isFollowing);
    }

    @Test
    void isFollowingCompanyTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        // create new company and user
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        Company mcdonald = Main.createNewCompany("McDonald's", allCompanies, false);

        // after adding a new company, check if user is following the company
        apinan.addCompany(mcdonald, allCompanies, false);
        boolean isFollowing = apinan.isFollowingCompany(mcdonald);
        assertTrue(isFollowing);
    }

    @Test
    void addFriendTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<User> unofficialAllUsers = new LinkedList<>();

        // create new users
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers, false);
        User angel = Main.createNewUser("angel", "angel", "angel", unofficialAllUsers, false);

        // check if apinan is friends with stewie
        apinan.addFriend(stewie, allUsers, false);
        boolean isFollowing = apinan.isFollowingUser(stewie);
        assertTrue(isFollowing);

        // check if stewie is friends with apinan
        isFollowing = stewie.isFollowingUser(apinan);
        assertTrue(isFollowing);

        // check if apinan can add stewie again
        boolean added = apinan.addFriend(stewie, allUsers, false);
        assertFalse(added);

        // check if apinan can add a null friend
        added = apinan.addFriend(null, allUsers, false);
        assertFalse(added);

        // check if apinan can add an invalid user
        added = apinan.addFriend(angel, allUsers, false);
        assertFalse(added);
    }

    @Test
    void addCompanyTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();
        LinkedList<Company> unofficialAllCompanies = new LinkedList<>();

        // creating new users and companies
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers, false);
        Company mcdonald = Main.createNewCompany("McDonald's", allCompanies, false);
        Company toysrus = Main.createNewCompany("ToysRus", unofficialAllCompanies, false);

        // check if apinan and stewie are in mcdonald's followers list
        apinan.addCompany(mcdonald, allCompanies, false);
        stewie.addCompany(mcdonald, allCompanies, false);

        boolean apinanFound = false;
        boolean stewieFound = false;
        boolean found = apinanFound && stewieFound;
        for (User user : mcdonald.getFollowersList()) {
            if (user.getUsername().equals(apinan.getUsername())) apinanFound = true;
            if (user.getUsername().equals(stewie.getUsername())) stewieFound = true;
            found = apinanFound && stewieFound;
            if (found) break;
        }

        assertTrue(found);

        // check if apinan can add a null company
        boolean added = apinan.addCompany(null, allCompanies, false);
        assertFalse(added);

        // check if apinan can follow a company again
        added = apinan.addCompany(mcdonald, allCompanies, false);
        assertFalse(added);

        // check if apinan can add an invalid company
        added = apinan.addCompany(toysrus, allCompanies, false);
        assertFalse(added);
    }

    @Test
    void updateFirstNameTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);

        // updating apinan's first name and checking if updated correctly
        apinan.updateFirstName("apinan", false);
        assertEquals("apinan", apinan.getFirstName());
    }

    @Test
    void updateLastNameTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);

        // updating apinan's first name and checking if updated correctly
        apinan.updateLastName("yogaratnam", false);
        assertEquals("yogaratnam", apinan.getLastName());
    }

    @Test
    void updateUsernameTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);

        // updating apinan's first name and checking if updated correctly
        apinan.updateUsername("api", allUsers, false);
        assertEquals("api", apinan.getUsername());
    }

    @Test
    void loadFriendsTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("apinan", "yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("stewie", "griffin", "stewietheangel", allUsers, false);
        User lilii = Main.createNewUser("rosee", "kiwii", "lilii", allUsers, false);
        String list = "{lilii,stewietheangel}";

        // checking if apinan is friends with lilii and stewietheangel
        apinan.loadFriends(list, allUsers);
        assertEquals(lilii, apinan.getFriendsList().get(0));
        assertEquals(stewie, apinan.getFriendsList().get(1));
        assertEquals(2, apinan.getFriendsList().size());
    }

    @Test
    void loadCompaniesTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        User apinan = Main.createNewUser("apinan", "yogaratnam", "apinanyogaratnam", allUsers, false);
        Company mcd = Main.createNewCompany("McDonald's", allCompanies, false);
        String list = "{McDonald''s}";

        // checking if apinan is following mcdonalds
        apinan.loadCompanies(list, allCompanies);
        assertEquals(1, apinan.getCompaniesList().size());
        assertEquals(mcd, apinan.getCompaniesList().get(0));
    }

    @Test
    void removeFriendTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<User> unofficialAllUsers = new LinkedList<>();

        // create new users
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers, false);
        User angel = Main.createNewUser("angel", "angel", "angel", unofficialAllUsers, false);

        // check if apinan is friends with stewie
        apinan.addFriend(stewie, allUsers, false);
        boolean isFollowing = apinan.isFollowingUser(stewie);
        assertTrue(isFollowing);

        // check if stewie is friends with apinan
        isFollowing = stewie.isFollowingUser(apinan);
        assertTrue(isFollowing);

        // check if apinan can remove stewie
        boolean removed = apinan.removeFriend(stewie, allUsers, false);
        assertTrue(removed);

        // check if apinan is still friends with stewie
        isFollowing = apinan.isFollowingUser(stewie);
        assertFalse(isFollowing);

        // check if apinan can remove stewie again
        removed = apinan.removeFriend(stewie, allUsers, false);
        assertFalse(removed);

        // check if apinan can remove a null friend
        removed = apinan.removeFriend(null, allUsers, false);
        assertFalse(removed);

        // check if apinan can remove an invalid user
        removed = apinan.removeFriend(angel, allUsers, false);
        assertFalse(removed);
    }

    @Test
    void removeCompanyTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();
        LinkedList<Company> unofficialAllCompanies = new LinkedList<>();

        // creating new users and companies
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers, false);
        Company mcdonald = Main.createNewCompany("McDonald's", allCompanies, false);
        Company toysrus = Main.createNewCompany("ToysRus", unofficialAllCompanies, false);

        // check if apinan and stewie are in mcdonald's followers list
        apinan.addCompany(mcdonald, allCompanies, false);
        stewie.addCompany(mcdonald, allCompanies, false);

        boolean apinanFound = false;
        boolean stewieFound = false;
        boolean found = apinanFound && stewieFound;
        for (User user : mcdonald.getFollowersList()) {
            if (user.getUsername().equals(apinan.getUsername())) apinanFound = true;
            if (user.getUsername().equals(stewie.getUsername())) stewieFound = true;
            found = apinanFound && stewieFound;
            if (found) break;
        }

        assertTrue(found);

        // check if apinan can remove a null company
        boolean removed = apinan.removeCompany(null, allCompanies, false);
        assertFalse(removed);

        // check if apinan can remove mcdonald
        removed = apinan.removeCompany(mcdonald, allCompanies, false);
        assertTrue(removed);

        // check if apinan is still following mcdonald
        boolean isFollowing = apinan.isFollowingCompany(mcdonald);
        assertFalse(isFollowing);

        // check if apinan can remove a company again
        removed = apinan.removeCompany(mcdonald, allCompanies, false);
        assertFalse(removed);

        // check if apinan can remove an invalid company
        removed = apinan.removeCompany(toysrus, allCompanies, false);
        assertFalse(removed);

        // check if apinan and stewie are still in mcdonald's followers list
        boolean isFoundApinan = false;
        boolean isFoundStewie = false;
        for (User user : mcdonald.getFollowersList()) {
            if (user.getUsername().equals(apinan.getUsername())) isFoundApinan = true;
            if (user.getUsername().equals(stewie.getUsername())) isFoundStewie = true;
        }
        assertFalse(isFoundApinan);
        assertTrue(isFoundStewie);
    }

    @Test
    void getCountOfMutualFriendsTest() {

    }

    @Test
    void deleteUserTest() {

    }

    @Test
    void getListOfMutualFriendsTest() {

    }

    @Test
    void getDegreeTest() {

    }

    @Test
    void suggestUsersTest() {

    }

    @Test
    void suggestCompanies() {

    }
}