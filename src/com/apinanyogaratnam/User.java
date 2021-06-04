package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
    MainHelper helperMethods = new MainHelper();
    Print printClass = new Print();
    String firstName;
    String lastName;
    String username;
    LinkedList<User> friendsList= new LinkedList<>();
    LinkedList<Company> companiesList = new LinkedList<>();
    boolean visited;

    // initializing a new user
    User(String firstName, String lastName, String username, LinkedList<User> allUsers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        allUsers.add(this);
    }

    // print first and last name of all users
    public void print(LinkedList<User> listOfUsers) {
        for (User userToPrint : listOfUsers) {
            printClass.print(userToPrint.firstName + ", " + userToPrint.lastName);
        }
    } // tested

    public boolean isFollowingUser(User possiblyFollowingFriend) {
        for (User user : this.friendsList) {
            if (possiblyFollowingFriend.username.equals(user.username)) return true;
        }

        return false;
    } // tested

    public boolean isFollowingCompany(Company possiblyFollowingCompany) {
        return helperMethods.isValidCompany(possiblyFollowingCompany.name, this.companiesList);
    }

    public boolean addFriend(User friend, LinkedList<User> allUsers) {
        if (friend == null) return false;
        if (!helperMethods.isValidUser(friend.username, allUsers)) return false;
        if (this.isFollowingUser(friend)) return false;

        boolean added = this.friendsList.add(friend);
        added = friend.friendsList.add(this) && added;

        return added;
    }// tested

    public boolean addCompany(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!helperMethods.isValidCompany(company.name, allCompanies)) return false;
        if (this.isFollowingCompany(company)) return false;

        boolean added = this.companiesList.add(company);
        company.followersList.add(this);

        return true;
    }


}
