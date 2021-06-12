package com.apinanyogaratnam;

import java.util.LinkedList;

public class Company {
    SQL sql = new SQL();
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
        if (company == null) return false;
        if (company == this) return false;
        if (!MainHelper.isValidCompany(company.name, allCompanies)) return false;
        if (hasNetwork(company)) return false;

        this.networksList.add(company);
        company.networksList.add(this);

        if (withSQL) UpdateSQL.addNetwork(this, company);

        return true;
    } // tested

    public boolean addFollower(User follower, boolean withSQL) {
        if (follower == null) return false;
        if (hasFollower(follower)) return false;

        this.followersList.add(follower);

        if (withSQL) UpdateSQL.addFollowers(this, follower);

        return true;
    } // tested

    public void loadNetworks(String listOfNetworksInStringFormat, LinkedList<Company> allCompanies) {
        String csv = listOfNetworksInStringFormat.substring(1, listOfNetworksInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            Company network = MainHelper.getCompany(strings[i], allCompanies);
            if (network != null) addNetwork(network, allCompanies, false);
        }
    } // tested

    public void loadFollowers(String listOfFollowersInStringFormat, LinkedList<User> allUsers) {
        String csv = listOfFollowersInStringFormat.substring(1, listOfFollowersInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            User follower = MainHelper.getUser(strings[i], allUsers);
            if (follower != null) addFollower(follower, false);
        }
    } // tested

    public boolean updateName(String newName, LinkedList<Company> allCompanies, boolean withSQL) {
        if (newName == null) return false;
        if (MainHelper.isValidCompany(newName, allCompanies)) {
            Print.print("Cannot update company because company already exists.");
            return false;
        }

        newName = Utils.parseString(newName);
        if (withSQL) UpdateSQL.updateName(this, newName);
        this.name = newName;

        return true;
    } // tested

    public boolean removeNetwork(Company company, LinkedList<Company> allCompanies, boolean withSQL) {
        if (company == null) return false;
        if (!hasNetwork(company)) return false;
        if (!MainHelper.isValidCompany(company.getName(), allCompanies)) return false;

        this.networksList.remove(company);
        company.networksList.remove(this);

        if (withSQL) UpdateSQL.removeNetwork(this, company);

        return true;
    } // tested

    public boolean deleteCompany(LinkedList<Company> allCompanies, boolean withSQL) {
        if (!allCompanies.contains(this)) return false;

        for (Company network: this.networksList) {
            this.removeNetwork(network, allCompanies, withSQL);
        }

        for (User follower: this.followersList) {
            follower.removeCompany(this, allCompanies, withSQL);
        }

        allCompanies.remove(this);
        if (withSQL) DeleteSQL.deleteObjectFromDB(this);

        return true;
    } // tested

    private LinkedList<Company> getListOfMutualNetworks(Company company, LinkedList<Company> allCompanies) {
        LinkedList<Company> listOfMutualNetworks = new LinkedList<>();

        for (Company possibleMutualNetwork : company.networksList) {
            if (this.hasNetwork(possibleMutualNetwork)) listOfMutualNetworks.add(company);
        }

        return listOfMutualNetworks;
    }

    private int getCountOfMutualNetworks(Company company, LinkedList<Company> allCompanies) {
        return getListOfMutualNetworks(company, allCompanies).size();
    }

    private LinkedList<Company> getListOfPossiblyNewNetwork(LinkedList<Company> allCompanies) {
        LinkedList<Company> listOfPossiblyNewNetwork = new LinkedList<>();

        for (Company company : allCompanies) {
            if (!hasNetwork(company) && this != company) listOfPossiblyNewNetwork.add(company);
        }

        return listOfPossiblyNewNetwork;
    }

    private void swap(LinkedList<Company> listOfObjects, int i, int j) {
        Company obj1 = listOfObjects.get(i);
        Company obj2 = listOfObjects.get(j);

        listOfObjects.set(i, obj2);
        listOfObjects.set(j, obj1);
    }

    public LinkedList<Company> suggestNetworks(LinkedList<Company> allCompanies) {
        LinkedList<Company> suggestedNetworksList = getListOfPossiblyNewNetwork(allCompanies);

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
