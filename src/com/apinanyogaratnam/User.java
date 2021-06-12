package com.apinanyogaratnam;

import java.util.LinkedList;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private LinkedList<User> friendsList= new LinkedList<>();
    private LinkedList<Company> companiesList = new LinkedList<>();
    private boolean visited;

    // initializing a new user
    User(String firstName, String lastName, String username, LinkedList<User> allUsers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        allUsers.add(this);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public LinkedList<User> getFriendsList() {
        return this.friendsList;
    }

    public LinkedList<Company> getCompaniesList() {
        return this.companiesList;
    }

    public boolean isFollowingUser(User possiblyFollowingFriend) {
        return this.friendsList.contains(possiblyFollowingFriend);
    }

    public boolean isFollowingCompany(Company possiblyFollowingCompany) {
        return this.companiesList.contains(possiblyFollowingCompany);
    }

    public boolean addFriend(User friend, LinkedList<User> allUsers, boolean withSQL) {
        // conditions to pass to add friend
        if (friend == null) return false;
        if (friend == this) return false;
        if (!MainHelper.isValidUser(friend.username, allUsers)) return false;
        if (this.isFollowingUser(friend)) return false;

        this.friendsList.add(friend);
        friend.friendsList.add(this);

        // update db
        if (withSQL) UpdateSQL.addFriend(this, friend);

        return true;
    }

    public boolean addCompany(Company company, LinkedList<Company> allCompanies, boolean withSQL) {
        // conditions to pass to add company
        if (company == null) return false;
        if (!MainHelper.isValidCompany(company.getName(), allCompanies)) return false;
        if (this.isFollowingCompany(company)) return false;

        boolean added = this.companiesList.add(company);
        company.addFollower(this, withSQL);

        // update db
        if (withSQL) UpdateSQL.addCompany(this, company);

        return true;
    }

    public boolean updateFirstName(String newName, boolean withSQL) {
        if (newName == null) return false;

        newName = Utils.parseString(newName);
        this.firstName = newName;

        // update db
        if (withSQL) UpdateSQL.updateFirstName(this, newName);

        return true;
    }

    public boolean updateLastName(String newName, boolean withSQL) {
        if (newName == null) return false;

        newName = Utils.parseString(newName);
        this.lastName = newName;

        // update db
        if (withSQL) UpdateSQL.updateLastName(this, newName);

        return true;
    }

    public boolean updateUsername(String newName, LinkedList<User> allUsers, boolean withSQL) {
        // conditions to pass to update username
        if (newName == null) return false;
        if (MainHelper.isValidUser(newName, allUsers)) {
            Print.print("Cannot update username since username already exists.");
            return false;
        }

        newName = Utils.parseString(newName);

        // update db
        if (withSQL) UpdateSQL.updateUsername(this, newName);

        // username updated after db update to not cause issues when comparing
        // previous username
        this.username = newName;

        return true;
    }

    public void loadFriends(String listOfFriendsInStringFormat, LinkedList<User> allUsers) {
        listOfFriendsInStringFormat = Utils.parseString(listOfFriendsInStringFormat);
        String csv = Utils.removeStartEndChars(listOfFriendsInStringFormat);

        // loading friends to user object if user is valid
        String [] strings = Utils.splitCommas(csv);
        for (String s : strings) {
            User friend = MainHelper.getUser(s, allUsers);
            addFriend(friend, allUsers, false);
        }
    }

    public void loadCompanies(String listOfCompaniesInStringFormat, LinkedList<Company> allCompanies) {
        listOfCompaniesInStringFormat = Utils.parseString(listOfCompaniesInStringFormat);
        String csv = Utils.removeStartEndChars(listOfCompaniesInStringFormat);

        // loading companies to user object if company is valid
        String [] strings = Utils.splitCommas(csv);
        for (String s : strings) {
            Company company = MainHelper.getCompany(s, allCompanies);
            addCompany(company, allCompanies, false);
        }
    }

    public boolean removeFriend(User friend, LinkedList<User> allUsers, boolean withSQL) {
        // conditions to pass to remove friend
        if (friend == null) return false;
        if (!MainHelper.isValidUser(friend.username, allUsers)) return false;
        if (!isFollowingUser(friend)) return false;

        this.friendsList.remove(this.friendsList.indexOf(friend));
        friend.friendsList.remove(friend.friendsList.indexOf(this));

        // update db
        if (withSQL) UpdateSQL.removeFriend(this, friend);

        return true;
    }

    public boolean removeCompany(Company company, LinkedList<Company> allCompanies, boolean withSQL) {
        // conditions to pass to remove company
        if (company == null) return false;
        if (!MainHelper.isValidCompany(company.getName(), allCompanies)) return false;
        if (!isFollowingCompany(company)) return false;

        // removing edges between user and company
        this.companiesList.remove(this.companiesList.indexOf(company));
        company.getFollowersList().remove(company.getFollowersList().indexOf(this));

        // update db
        if (withSQL) UpdateSQL.removeCompany(this, company);

        return true;
    }

    public boolean deleteUser(LinkedList<User> allUsers, LinkedList<Company> allCompanies, boolean withSQL) {
        if (!allUsers.contains(this)) return false;

        // remove all user's friends
        for (User friend : this.friendsList) {
            this.removeFriend(friend, allUsers, false);
        }

        // remove every company user is following
        for (Company company : this.companiesList) {
            this.removeCompany(company, allCompanies, withSQL);
        }

        // remove from allUsers
        allUsers.remove(allUsers.indexOf(this));

        if (withSQL) DeleteSQL.deleteObjectFromDB(this);

        return true;
    }

    public int getCountOfMutualFriends(User user, LinkedList<User> allUsers) {
        return getListOfMutualFriends(user, allUsers).size();
    }

    public LinkedList<User> getListOfMutualFriends(User user, LinkedList<User> allUsers) {
        LinkedList<User> mutualFriends = new LinkedList<>();

        for (User friendOfUser : user.friendsList) {
            if (this.isFollowingUser(friendOfUser) && this != friendOfUser) mutualFriends.add(friendOfUser);
        }

        return mutualFriends;
    }

    public int getDegree(User a, User b, LinkedList<User> allUsers) {
        // if a is already following b, return 1
        if (a.isFollowingUser(b)) return 1;

        // marking node as visited
        a.visited = true;
        int degree = 0;
        int temp = 0;

        // looping through a's friends list
        for (User friend : this.friendsList) {
            // skip if user already visited
            if (friend.visited) continue;

            // recursive call with friend of a's friend list
            temp = 1 + getDegree(friend, b, allUsers);

            // updating degree of connection
            if (degree == 0 || (temp > 0 && temp < degree)) degree = temp;
        }

        // marking node as false for next use
        a.visited = false;

        // no relation between user a and user b
        if (degree == 0) return -1;

        return degree;
    }

    private LinkedList<User> getListOfPossiblyNewFriends(LinkedList<User> allUsers) {
        LinkedList<User> possiblyNewFriends = new LinkedList<>();

        for (User user : allUsers) {
            // checking if user is not itself or not already a friend
            if (!isFollowingUser(user) && user != this) {
                possiblyNewFriends.add(user);
            }
        }

        return possiblyNewFriends;
    }

    private void swap(LinkedList<User> listOfObjects, int i, int j) {
        User obj1 = listOfObjects.get(i);
        User obj2 = listOfObjects.get(j);

        // swapping obj 1 with obj 2's place and vice versa
        listOfObjects.set(i, obj2);
        listOfObjects.set(j, obj1);
    }

    public LinkedList<User> suggestUsers(LinkedList<User> allUsers) {
        LinkedList<User> suggestedUsers = getListOfPossiblyNewFriends(allUsers);

        // using insertion sort to sort suggested users from highest degree to least degree of connectives
        for (int i=1; i<suggestedUsers.size(); i++) {
            User currentUser = suggestedUsers.get(i);
            int j = i;

            int numberOfMutualFriends = this.getCountOfMutualFriends(currentUser, allUsers);
            while (j > 0 && numberOfMutualFriends > this.getCountOfMutualFriends(suggestedUsers.get(j-1), allUsers)) {
                swap(suggestedUsers, j, j-1);
                j--;
            }
        }

        return suggestedUsers;
    }

    private LinkedList<Company> getListOfPossiblyNewCompanies(LinkedList<Company> allCompanies) {
        LinkedList<Company> possiblyNewCompanies = new LinkedList<>();

        for (Company company : allCompanies) {
            // checking if user is not itself or not already a friend
            if (!isFollowingCompany(company)) {
                possiblyNewCompanies.add(company);
            }
        }

        return possiblyNewCompanies;
    }

    public LinkedList<Company> suggestCompanies(LinkedList<Company> allCompanies) {
        LinkedList<Company> listOfPossiblyNewCompanies = getListOfPossiblyNewCompanies(allCompanies);
        LinkedList<Company> suggestedCompanies = new LinkedList<>();

        // loop through companies list
        for (Company company : this.companiesList) {
            // loop through network of friends list and append companies not already in suggested companies
            for (Company network : company.getNetworksList()) {
                if (listOfPossiblyNewCompanies.contains(network) && !suggestedCompanies.contains(network)) {
                    suggestedCompanies.add(network);
                }
            }
        }

        // appending the rest of the companies without connections to user
        for (Company company : listOfPossiblyNewCompanies) {
            if (!suggestedCompanies.contains(company)) {
                suggestedCompanies.add(company);
            }
        }

        return suggestedCompanies;
    }
}
