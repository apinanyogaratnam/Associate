package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
    MainHelper mainHelperMethods = new MainHelper();
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

    public boolean isFollowingUser(User possiblyFollowingFriend) {
        for (User user : this.friendsList) {
            if (possiblyFollowingFriend.username.equals(user.username)) return true;
        }

        return false;
    } // tested

    public boolean isFollowingCompany(Company possiblyFollowingCompany) {
        return mainHelperMethods.isValidCompany(possiblyFollowingCompany.name, this.companiesList);
    } // tested

    public boolean addFriend(User friend, LinkedList<User> allUsers) {
        if (friend == null) return false;
        if (!mainHelperMethods.isValidUser(friend.username, allUsers)) return false;
        if (this.isFollowingUser(friend)) return false;

        boolean added = this.friendsList.add(friend);
        added = friend.friendsList.add(this) && added;

        return added;
    } // tested

    public void addFriends(String listOfFriendsInStringFormat, LinkedList<User> allUsers) {
        String csv = listOfFriendsInStringFormat.substring(1, listOfFriendsInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            User friend = mainHelperMethods.getUser(strings[i], allUsers);
            addFriend(friend, allUsers);
        }
    }


    public boolean addCompany(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!mainHelperMethods.isValidCompany(company.name, allCompanies)) return false;
        if (this.isFollowingCompany(company)) return false;

        boolean added = this.companiesList.add(company);
        company.followersList.add(this);

        return true;
    } // tested

    public boolean removeFriend(User friend, LinkedList<User> allUsers) {
        if (friend == null) return false;
        if (!mainHelperMethods.isValidUser(friend.username, allUsers)) return false;
        if (!isFollowingUser(friend)) return false;

        this.friendsList.remove(this.friendsList.indexOf(friend));
        friend.friendsList.remove(friend.friendsList.indexOf(this));

        return true;
    }

    public boolean removeCompany(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!mainHelperMethods.isValidCompany(company.name, allCompanies)) return false;
        if (!isFollowingCompany(company)) return false;

        this.companiesList.remove(this.companiesList.indexOf(company));
        company.followersList.remove(company.followersList.indexOf(this));

        return true;
    }

    public boolean deleteUser(LinkedList<User> allUsers) {
        int indexOfUser = allUsers.indexOf(this);
        if (indexOfUser == -1) return false;

        // remove from everyone friends with 'this'
        for (User friend : this.friendsList) {
            this.removeFriend(friend, allUsers);
        }

        // remove from allUsers
        allUsers.remove(indexOfUser);

        return true;
    }

    public int getCountOfMutualFriends(User user, LinkedList<User> allUsers) {
        return getListOfMutualFriends(user, allUsers).size();
    }

    public LinkedList<User> getListOfMutualFriends(User user, LinkedList<User> allUsers) {
        LinkedList<User> mutualFriends = new LinkedList<>();

        for (User friendOfUser : user.friendsList) {
            if (this.isFollowingUser(friendOfUser)) mutualFriends.add(friendOfUser);
        }

        return mutualFriends;
    }

    public int getDegree(User a, User b, LinkedList<User> allUsers) {
        if (a.isFollowingUser(b)) return 1;
        a.visited = true;
        int degree = 0;
        int temp = 0;

        for (User friend : this.friendsList) {
            if (friend.visited) continue;
            temp = 1 + getDegree(friend, b, allUsers);

            if (degree == 0 || (temp > 0 && temp < degree)) degree = temp;
        }

        a.visited = false;
        if (degree == 0) return -1;

        return degree;
    }

    public LinkedList<User> suggestUsers(LinkedList<User> allUsers) {
        LinkedList<User> suggestedUsers = new LinkedList<>();

        return suggestedUsers;
    }

    public LinkedList<Company> suggestCompanies(LinkedList<User> allCompanies) {
        LinkedList<Company> suggestedCompanies = new LinkedList<>();

        return suggestedCompanies;
    }

}
