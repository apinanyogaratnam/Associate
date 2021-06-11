package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void getFirstName() {
        LinkedList<User> allUsers = new LinkedList<>();
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);

        // checking if apinan is given
        String username = apinan.getFirstName();
        assertEquals("Apinan", username);
    }

    @Test
    void isFollowingUserTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new users
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers, false);

        // after adding a friend, check if friend is following
        apinan.addFriend(stewie, allUsers);
        boolean isFollowing = apinan.isFollowingUser(stewie);
        assertEquals(true, isFollowing);
    }

    @Test
    void isFollowingCompanyTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();

        // create new company and user
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        Company mcdonald = Main.createNewCompany("McDonald''s", allCompanies, false);

        // after adding a new company, check if user is following the company
        apinan.addCompany(mcdonald, allCompanies);
        boolean isFollowing = apinan.isFollowingCompany(mcdonald);
        assertEquals(true, isFollowing);
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
        apinan.addFriend(stewie, allUsers);
        boolean isFollowing = apinan.isFollowingUser(stewie);
        assertEquals(true, isFollowing);

        // check if stewie is friends with apinan
        isFollowing = stewie.isFollowingUser(apinan);
        assertEquals(true, isFollowing);

        // check if apinan can add stewie again
        boolean added = apinan.addFriend(stewie, allUsers);
        assertEquals(false, added);

        // check if apinan can add a null friend
        added = apinan.addFriend(null, allUsers);
        assertEquals(false, added);

        // check if apinan can add an invalid user
        added = apinan.addFriend(angel, allUsers);
        assertEquals(false, added);
    }

    @Test
    void addCompanyTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();
        LinkedList<Company> unofficialAllCompanies = new LinkedList<>();

        // creating new users and companies
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers, false);
        Company mcdonald = Main.createNewCompany("McDonald''s", allCompanies, false);
        Company toysrus = Main.createNewCompany("ToysRus", unofficialAllCompanies, false);

        // check if apinan and stewie are in mcdonald's followers list
        apinan.addCompany(mcdonald, allCompanies);
        stewie.addCompany(mcdonald, allCompanies);

        boolean apinanFound = false;
        boolean stewieFound = false;
        boolean found = apinanFound && stewieFound;
        for (User user : mcdonald.getFollowersList()) {
            if (user.getUsername().equals(apinan.getUsername())) apinanFound = true;
            if (user.getUsername().equals(stewie.getUsername())) stewieFound = true;
            found = apinanFound && stewieFound;
            if (found) break;
        }

        assertEquals(true, found);

        // check if apinan can add a null company
        boolean added = apinan.addCompany(null, allCompanies);
        assertEquals(false, added);

        // check if apinan can follow a company again
        added = apinan.addCompany(mcdonald, allCompanies);
        assertEquals(false, added);

        // check if apinan can add an invalid company
        added = apinan.addCompany(toysrus, allCompanies);
        assertEquals(false, added);
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
        apinan.addFriend(stewie, allUsers);
        boolean isFollowing = apinan.isFollowingUser(stewie);
        assertEquals(true, isFollowing);

        // check if stewie is friends with apinan
        isFollowing = stewie.isFollowingUser(apinan);
        assertEquals(true, isFollowing);

        // check if apinan can remove stewie
        boolean removed = apinan.removeFriend(stewie, allUsers);
        assertEquals(true, removed);

        // check if apinan is still friends with stewie
        isFollowing = apinan.isFollowingUser(stewie);
        assertEquals(false, isFollowing);

        // check if apinan can remove stewie again
        removed = apinan.removeFriend(stewie, allUsers);
        assertEquals(false, removed);

        // check if apinan can remove a null friend
        removed = apinan.removeFriend(null, allUsers);
        assertEquals(false, removed);

        // check if apinan can remove an invalid user
        removed = apinan.removeFriend(angel, allUsers);
        assertEquals(false, removed);
    }

    @Test
    void removeCompanyTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<Company> allCompanies = new LinkedList<>();
        LinkedList<Company> unofficialAllCompanies = new LinkedList<>();

        // creating new users and companies
        User apinan = Main.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers, false);
        User stewie = Main.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers, false);
        Company mcdonald = Main.createNewCompany("McDonald''s", allCompanies, false);
        Company toysrus = Main.createNewCompany("ToysRus", unofficialAllCompanies, false);

        // check if apinan and stewie are in mcdonald's followers list
        apinan.addCompany(mcdonald, allCompanies);
        stewie.addCompany(mcdonald, allCompanies);

        boolean apinanFound = false;
        boolean stewieFound = false;
        boolean found = apinanFound && stewieFound;
        for (User user : mcdonald.getFollowersList()) {
            if (user.getUsername().equals(apinan.getUsername())) apinanFound = true;
            if (user.getUsername().equals(stewie.getUsername())) stewieFound = true;
            found = apinanFound && stewieFound;
            if (found) break;
        }

        assertEquals(true, found);

        // check if apinan can remove a null company
        boolean removed = apinan.removeCompany(null, allCompanies);
        assertEquals(false, removed);

        // check if apinan can remove mcdonald
        removed = apinan.removeCompany(mcdonald, allCompanies);
        assertEquals(true, removed);

        // check if apinan is still following mcdonald
        boolean isFollowing = apinan.isFollowingCompany(mcdonald);
        assertEquals(false, isFollowing);

        // check if apinan can remove a company again
        removed = apinan.removeCompany(mcdonald, allCompanies);
        assertEquals(false, removed);

        // check if apinan can remove an invalid company
        removed = apinan.removeCompany(toysrus, allCompanies);
        assertEquals(false, removed);

        // check if apinan and stewie are still in mcdonald's followers list
        boolean isFoundApinan = false;
        boolean isFoundStewie = false;
        for (User user : mcdonald.getFollowersList()) {
            if (user.getUsername().equals(apinan.getUsername())) isFoundApinan = true;
            if (user.getUsername().equals(stewie.getUsername())) isFoundStewie = true;
        }
        assertEquals(false, isFoundApinan);
        assertEquals(true, isFoundStewie);
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