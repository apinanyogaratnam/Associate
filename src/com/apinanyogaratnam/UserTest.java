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
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        User stewie = mainMethod.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers);

        apinan.addFriend(stewie, allUsers);
        boolean isFollowing = apinan.isFollowingUser(stewie);
        assertEquals(true, isFollowing);
    }

    @Test
    void isFollowingCompanyTest() {
    }

    @Test
    void addFriendTest() {
        LinkedList<User> allUsers = new LinkedList<>();
        LinkedList<User> unofficialAllUsers = new LinkedList<>();
        User apinan = mainMethod.createNewUser("Apinan", "Yogaratnam", "apinanyogaratnam", allUsers);
        User stewie = mainMethod.createNewUser("Stewie", "Griffin", "stewietheangel", allUsers);
        User angel = mainMethod.createNewUser("angel", "angel", "angel", unofficialAllUsers);

        apinan.addFriend(stewie, allUsers);
        boolean isFollowing = apinan.isFollowingUser(stewie);
        assertEquals(true, isFollowing);

        isFollowing = stewie.isFollowingUser(apinan);
        assertEquals(true, isFollowing);

        boolean added = apinan.addFriend(stewie, allUsers);
        assertEquals(false, added);

        added = apinan.addFriend(null, allUsers);
        assertEquals(false, added);

        added = apinan.addFriend(angel, allUsers);
        assertEquals(false, added);
    }

    @Test
    void addCompanyTest() {
    }
}