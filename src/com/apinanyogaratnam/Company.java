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
        return this.followersList.indexOf(user) != -1;
    }

    public boolean addNetwork(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!MainHelper.isValidCompany(company.name, allCompanies)) return false;
        if (hasNetwork(company)) return false;

        this.networksList.add(company);
        company.networksList.add(this);

        UpdateSQL.addNetwork(this, company);

        return true;
    } // tested

    public boolean addFollower(User follower) {
        if (follower == null) return false;
        if (hasFollower(follower)) return false;

        this.followersList.add(follower);

        UpdateSQL.addFollowers(this, follower);

        return true;
    } // tested

    public void loadNetworks(String listOfNetworksInStringFormat, LinkedList<Company> allCompanies) {
        String csv = listOfNetworksInStringFormat.substring(1, listOfNetworksInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            Company network = MainHelper.getCompany(strings[i], allCompanies);
            if (network != null) this.networksList.add(network);
        }
    } // tested

    public void loadFollowers(String listOfFollowersInStringFormat, LinkedList<User> allUsers) {
        String csv = listOfFollowersInStringFormat.substring(1, listOfFollowersInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            User follower = MainHelper.getUser(strings[i], allUsers);
            if (follower != null) this.followersList.add(follower);
        }
    } // tested

    public boolean updateName(String newName, LinkedList<Company> allCompanies) {
        if (newName == null) return false;
        if (MainHelper.isValidCompany(newName, allCompanies)) {
            Print.print("Cannot update company because company already exists.");
            return false;
        }

        UpdateSQL.updateName(this, newName);
        this.name = newName;

        return true;
    } // tested

    public boolean removeNetwork(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (!hasNetwork(company)) return false;
        if (!MainHelper.isValidCompany(company.getName(), allCompanies)) return false;

        this.networksList.remove(company);
        company.networksList.remove(this);

        UpdateSQL.removeNetwork(this, company);

        return true;
    }

    public boolean deleteCompany(LinkedList<Company> allCompanies) {
        if (!allCompanies.contains(this)) return false;

        for (Company network: this.networksList) {
            this.removeNetwork(network, allCompanies);
        }

        for (User follower: this.followersList) {
            follower.removeCompany(this, allCompanies);
        }

        allCompanies.remove(this);
        DeleteSQL.deleteObjectFromDB(this);

        return true;
    }
    public Company suggestNetwork(LinkedList<Company> allCompanies) {
        Company suggestedNetwork = null;

        return suggestedNetwork;
    }

    public LinkedList<Company> suggestNetworks(LinkedList<Company> allCompanies) {
        LinkedList<Company> suggestedNetworksList = new LinkedList<>();

        return suggestedNetworksList;
    }

}
