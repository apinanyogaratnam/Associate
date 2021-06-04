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
    }

    public boolean isFollowingUser(User possiblyFollowingFriend) {
        for (User user : this.friendsList) {
            if (possiblyFollowingFriend.username.equals(user.username)) return true;
        }

        return false;
    }

    public boolean isFollowingCompany(Company possiblyFollowingCompany) {
        return helperMethods.isValidCompany(possiblyFollowingCompany.name, this.companiesList);
    }

    public int addFriend(User friend, LinkedList<User> allUsers) {
        if (this.isFollowingUser(friend)) return 0;

        this.friendsList.add(friend);

        return 1;
    }

    public int addCompany(Company company, LinkedList<Company> allCompanies) {
        if (this.isFollowingCompany(company)) return 0;

        this.companiesList.add(company);

        return 1;
    }


}
