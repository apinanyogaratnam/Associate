package com.apinanyogaratnam;

import java.util.LinkedList;

public class Company {
    private String name;
    private LinkedList<Company> networksList = new LinkedList<>();
    private LinkedList<User> followersList = new LinkedList<>();

    // initializing a new company
    Company(String name, LinkedList<Company> allCompanies) {
        this.name = name;
        allCompanies.add(this);
    }

    public String getName() {
        return this.name;
    }

    public LinkedList<Company> getNetworksList() {
        return this.networksList;
    }

    public LinkedList<User> getFollowersList() {
        return this.followersList;
    }

    public boolean hasNetwork(Company company) {
        return this.networksList.contains(company);
    }

    public boolean hasFollower(User user) {
        return this.followersList.contains(user);
    }

    public boolean addNetwork(Company company, LinkedList<Company> allCompanies, boolean withSQL) {
        // conditions to pass for adding to network
        if (company == null) return false;
        if (company == this) return false;
        if (!MainHelper.isValidCompany(company.name, allCompanies)) return false;
        if (hasNetwork(company)) return false;

        this.networksList.add(company);
        company.networksList.add(this);

        // db updates
        if (withSQL) UpdateSQL.addNetwork(this, company);

        return true;
    }

    public boolean addFollower(User follower, boolean withSQL) {
        // conditions to pass for adding follower
        if (follower == null) return false;
        if (hasFollower(follower)) return false;

        this.followersList.add(follower);

        // db updates
        if (withSQL) UpdateSQL.addFollowers(this, follower);

        return true;
    }

    public void loadNetworks(String listOfNetworksInStringFormat, LinkedList<Company> allCompanies) {
        String csv = listOfNetworksInStringFormat.substring(1, listOfNetworksInStringFormat.length()-1);

        // adding validated networks to company's networks list
        String [] strings = Utils.splitCommas(csv);
        for (String s : strings) {
            Company network = MainHelper.getCompany(s, allCompanies);
            if (network != null) addNetwork(network, allCompanies, false);
        }
    }

    public void loadFollowers(String listOfFollowersInStringFormat, LinkedList<User> allUsers) {
        String csv = listOfFollowersInStringFormat.substring(1, listOfFollowersInStringFormat.length()-1);

        // adding validated followers to company's followers list
        String [] strings = Utils.splitCommas(csv);
        for (String s : strings) {
            User follower = MainHelper.getUser(s, allUsers);
            if (follower != null) addFollower(follower, false);
        }
    }

    public boolean updateName(String newName, LinkedList<Company> allCompanies, boolean withSQL) {
        // conditions to pass to update company name
        if (newName == null) return false;
        if (MainHelper.isValidCompany(newName, allCompanies)) {
            Print.print("Cannot update company because company already exists.");

            return false;
        }

        // parsing string for quote issues
        newName = Utils.parseString(newName);

        // db updates
        if (withSQL) UpdateSQL.updateName(this, newName);
        this.name = newName;

        return true;
    }

    public boolean removeNetwork(Company company, LinkedList<Company> allCompanies, boolean withSQL) {
        // conditions to pass to remove network
        if (company == null) return false;
        if (!hasNetwork(company)) return false;
        if (!MainHelper.isValidCompany(company.getName(), allCompanies)) return false;

        this.networksList.remove(company);
        company.networksList.remove(this);

        // db updates
        if (withSQL) UpdateSQL.removeNetwork(this, company);

        return true;
    }

    public boolean deleteCompany(LinkedList<Company> allCompanies, boolean withSQL) {
        if (!allCompanies.contains(this)) return false;

        for (Company network: this.networksList) {
            this.removeNetwork(network, allCompanies, withSQL);
        }

        for (User follower: this.followersList) {
            follower.removeCompany(this, allCompanies, withSQL);
        }

        allCompanies.remove(this);

        // db updates
        if (withSQL) DeleteSQL.deleteObjectFromDB(this);

        return true;
    }

    private LinkedList<Company> getListOfMutualNetworks(Company company) {
        LinkedList<Company> listOfMutualNetworks = new LinkedList<>();

        // comparing and appending mutual networks
        for (Company possibleMutualNetwork : company.networksList) {
            if (this.hasNetwork(possibleMutualNetwork)) listOfMutualNetworks.add(company);
        }

        return listOfMutualNetworks;
    }

    private int getCountOfMutualNetworks(Company company, LinkedList<Company> allCompanies) {
        return getListOfMutualNetworks(company).size();
    }

    private LinkedList<Company> getListOfPossiblyNewNetwork(LinkedList<Company> allCompanies) {
        LinkedList<Company> listOfPossiblyNewNetwork = new LinkedList<>();

        // appending all possible networks this can follow
        for (Company company : allCompanies) {
            if (!hasNetwork(company) && this != company) listOfPossiblyNewNetwork.add(company);
        }

        return listOfPossiblyNewNetwork;
    }

    private void swap(LinkedList<Company> listOfObjects, int i, int j) {
        Company obj1 = listOfObjects.get(i);
        Company obj2 = listOfObjects.get(j);

        // swapping obj1 and obj2
        listOfObjects.set(i, obj2);
        listOfObjects.set(j, obj1);
    }

    public LinkedList<Company> suggestNetworks(LinkedList<Company> allCompanies) {
        LinkedList<Company> suggestedNetworksList = getListOfPossiblyNewNetwork(allCompanies);

        // using insertion sort to sort out companies from most closest degree to farthest degree
        for (int i=1; i<suggestedNetworksList.size(); i++) {
            Company currentNetwork = suggestedNetworksList.get(i);
            int j = i;

            int numberOfMutualFriends = this.getCountOfMutualNetworks(currentNetwork, allCompanies);
            while (j > 0 && numberOfMutualFriends > this.getCountOfMutualNetworks(suggestedNetworksList.get(j-1), allCompanies)) {
                swap(suggestedNetworksList, j, j-1);
                j--;
            }
        }

        return suggestedNetworksList;
    }
}
