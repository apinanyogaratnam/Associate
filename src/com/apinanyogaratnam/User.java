package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
    SQL sql = new SQL();
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
        return this.friendsList.indexOf(possiblyFollowingFriend) != -1;
    }

    public boolean isFollowingCompany(Company possiblyFollowingCompany) {
        return this.companiesList.indexOf(possiblyFollowingCompany) != -1;
    }

    public boolean addFriend(User friend, LinkedList<User> allUsers) {
        if (friend == null) return false;
        if (!MainHelper.isValidUser(friend.username, allUsers)) return false;
        if (this.isFollowingUser(friend)) return false;

        this.friendsList.add(friend);
        friend.friendsList.add(this);

        sql.addFriend(this, friend);

        return true;
    } // tested

    public boolean addCompany(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!MainHelper.isValidCompany(company.name, allCompanies)) return false;
        if (this.isFollowingCompany(company)) return false;

        boolean added = this.companiesList.add(company);
        company.addFollower(this);

        sql.updateCompany(this, company);

        return true;
    } // tested

    public boolean updateFirstName(String newName) {
        if (newName == null) return false;

        this.firstName = newName;
        sql.updateFirstName(this, newName);

        return true;
    }

    public boolean updateLastName(String newName) {
        if (newName == null) return false;

        this.lastName = newName;
        sql.updateLastName(this, newName);

        return true;
    }

    public void loadFriends(String listOfFriendsInStringFormat, LinkedList<User> allUsers) {
        String csv = listOfFriendsInStringFormat.substring(1, listOfFriendsInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            User friend = MainHelper.getUser(strings[i], allUsers);
            addFriend(friend, allUsers);
        }
    }

    public boolean removeFriend(User friend, LinkedList<User> allUsers) {
        if (friend == null) return false;
        if (!MainHelper.isValidUser(friend.username, allUsers)) return false;
        if (!isFollowingUser(friend)) return false;

        this.friendsList.remove(this.friendsList.indexOf(friend));
        friend.friendsList.remove(friend.friendsList.indexOf(this));

        return true;
    }

    public boolean removeCompany(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!MainHelper.isValidCompany(company.name, allCompanies)) return false;
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

    public User suggestUser(LinkedList<User> allUsers) {
        int getDegreeMin = 2;
        User userMin = null;

        for (User user : allUsers) {
            if (user.username.equals(this.username)) continue;
            if (this.isFollowingUser(user)) continue;

            // finding close users with nearby degrees
            int currentDegree = getDegree(this, user, allUsers);
            if (currentDegree != -1 && currentDegree != 1 && currentDegree <= getDegreeMin) {
                getDegreeMin = currentDegree;
                userMin = user;
            }
        }

        return userMin;
    }

    public LinkedList<User> suggestUsers(LinkedList<User> allUsers) {
        LinkedList<User> suggestedUsersList = new LinkedList<>();

        return suggestedUsersList;
    }

    public LinkedList<Company> suggestCompanies(LinkedList<User> allCompanies) {
        LinkedList<Company> suggestedCompanies = new LinkedList<>();

        return suggestedCompanies;
    }

}
