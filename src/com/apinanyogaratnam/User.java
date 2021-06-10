package com.apinanyogaratnam;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.util.LinkedList;

public class User {
    private static final SQL sql = new SQL();
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
    //  return this.friendsList.indexOf(possiblyFollowingFriend) != -1;
        return this.friendsList.contains(possiblyFollowingFriend);
    } // tested

    public boolean isFollowingCompany(Company possiblyFollowingCompany) {
        return this.companiesList.indexOf(possiblyFollowingCompany) != -1;
    } // tested

    public boolean addFriend(User friend, LinkedList<User> allUsers) {
        if (friend == null) return false;
        if (!MainHelper.isValidUser(friend.username, allUsers)) return false;
        if (this.isFollowingUser(friend)) return false;

        this.friendsList.add(friend);
        friend.friendsList.add(this);

        UpdateSQL.addFriend(this, friend);

        return true;
    } // tested

    public boolean addCompany(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!MainHelper.isValidCompany(company.getName(), allCompanies)) return false;
        if (this.isFollowingCompany(company)) return false;

        boolean added = this.companiesList.add(company);
        company.addFollower(this);

        UpdateSQL.addCompany(this, company);

        return true;
    } // tested

    public boolean updateFirstName(String newName) {
        if (newName == null) return false;

        this.firstName = newName;
        UpdateSQL.updateFirstName(this, newName);

        return true;
    } // tested

    public boolean updateLastName(String newName) {
        if (newName == null) return false;

        this.lastName = newName;
        UpdateSQL.updateLastName(this, newName);

        return true;
    } // tested

    public boolean updateUsername(String newName, LinkedList<User> allUsers) {
        if (newName == null) return false;
        if (MainHelper.isValidUser(newName, allUsers)) {
            Print.print("Cannot update username since username already exists.");
            return false;
        }

        UpdateSQL.updateUsername(this, newName);
        this.username = newName;

        return true;
    } // tested

    public void loadFriends(String listOfFriendsInStringFormat, LinkedList<User> allUsers) {
        String csv = Utils.removeCurlyBraces(listOfFriendsInStringFormat);

        String [] strings = Utils.splitCommas(csv);
        for (int i=0; i<strings.length; i++) {
            User friend = MainHelper.getUser(strings[i], allUsers);
            addFriend(friend, allUsers);
        }
    } // tested

    public void loadCompanies(String listOfCompaniesInStringFormat, LinkedList<Company> allCompanies) {
        String csv = Utils.removeCurlyBraces(listOfCompaniesInStringFormat);

        String [] strings = Utils.splitCommas(csv);
        for (int i=0; i<strings.length; i++) {
            Company company = MainHelper.getCompany(strings[i], allCompanies);
            addCompany(company, allCompanies);
        }
    } // tested

    public boolean removeFriend(User friend, LinkedList<User> allUsers) {
        if (friend == null) return false;
        if (!MainHelper.isValidUser(friend.username, allUsers)) return false;
        if (!isFollowingUser(friend)) return false;

        this.friendsList.remove(this.friendsList.indexOf(friend));
        friend.friendsList.remove(friend.friendsList.indexOf(this));

        UpdateSQL.removeFriend(this, friend);

        return true;
    } // tested

    public boolean removeCompany(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!MainHelper.isValidCompany(company.getName(), allCompanies)) return false;
        if (!isFollowingCompany(company)) return false;

        this.companiesList.remove(this.companiesList.indexOf(company));
        company.getFollowersList().remove(company.getFollowersList().indexOf(this));

        UpdateSQL.removeCompany(this, company);

        return true;
    } // tested

    public boolean deleteUser(LinkedList<User> allUsers, LinkedList<Company> allCompanies) {
        if (!allUsers.contains(this)) return false;

        // remove all user's friends
        for (User friend : this.friendsList) {
            this.removeFriend(friend, allUsers);
        }

        // remove every company user is following
        for (Company company : this.companiesList) {
            this.removeCompany(company, allCompanies);
        }

        // remove from allUsers
        allUsers.remove(allUsers.indexOf(this));

        DeleteSQL.deleteObjectFromDB(this);

        return true;
    } // tested

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

    public User suggestUser(LinkedList<User> allUsers) { // suggest based on mutual friends?
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
