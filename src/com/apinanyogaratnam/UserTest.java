package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    Main mainMethod = new Main();
    Print printClass = new Print();

    @Test
    void isFollowingUserTest() {
        LinkedList<User> allUsers = new LinkedList<>();

        // create new users
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        User stewie = mainMethod.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers);

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
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies);

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
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        User stewie = mainMethod.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers);
        User angel = mainMethod.createNewUser("angel", "angel", "angel", unofficialAllUsers);

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
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        User stewie = mainMethod.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers);
        Company mcdonald = mainMethod.createNewCompany("McDonald's", allCompanies);
        Company toysrus = mainMethod.createNewCompany("ToysRus", unofficialAllCompanies);

        // check if apinan and stewie are in mcdonald's followers list
        apinan.addCompany(mcdonald, allCompanies);
        stewie.addCompany(mcdonald, allCompanies);

        boolean apinanFound = false;
        boolean stewieFound = false;
        boolean found = apinanFound && stewieFound;
        for (User user : mcdonald.followersList) {
            if (user.username.equals(apinan.username)) apinanFound = true;
            if (user.username.equals(stewie.username)) stewieFound = true;
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
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        User stewie = mainMethod.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers);
        User angel = mainMethod.createNewUser("angel", "angel", "angel", unofficialAllUsers);

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

    }

    @Test
    void deleteUserTest() {

    }

    @Test
    void getCountOfMutualFriendsTest() {

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