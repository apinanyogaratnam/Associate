package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
    Print printClass = new Print();
    String firstName;
    String lastName;
    String username;
    LinkedList<User> friendsList= new LinkedList<>();
    LinkedList<Company> companiesList = new LinkedList<>();
    boolean visited;
    boolean createdSuccessfully = true;

    // initializing a new user (make error checks to see if username currently exists)
    User(String firstName, String lastName, String username, LinkedList<User> allUsers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        if (!isValidUser(this, allUsers)) {
            createdSuccessfully = false;
        } else {
            allUsers.add(this);
        }
    }

    // print first and last name of all users
    public void print(LinkedList<User> listOfUsers) {
        for (User userToPrint : listOfUsers) {
            printClass.print(userToPrint.firstName + ", " + userToPrint.lastName);
        }
    }

    public boolean isValidUser(User possibleUser, LinkedList<User> allUsers) {
        for (User user : allUsers) {
            if (possibleUser.username.equals(user.username)) {
                return true;
            }
        }

        return false;
    }

    public boolean isFriends(User possibleFriend) {
        for (User user : this.friendsList) {
            if (user.username.equals(possibleFriend.username)) {
                return true;
            }
        }

        return false;
    }

    public int addFriend(User user, User friend, LinkedList<User> allUsers) {
        if (!isValidUser(user, allUsers)) return 0;
        if (!isValidUser(friend, allUsers)) return 0;
        if (user.isFriends(friend)) return 0;

        user.friendsList.add(friend);

        return 1;
    }

    public int addCompany(Company company, LinkedList<Company> allCompanies) {
        // Finish code
    }


}
