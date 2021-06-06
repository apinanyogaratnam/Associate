package com.apinanyogaratnam;

import java.util.LinkedList;

public class Company {
    MainHelper mainHelperMethods = new MainHelper();
    String name;
    LinkedList<Company> networksList = new LinkedList<>();
    LinkedList<User> followersList = new LinkedList<>();

    // initializing a new company
    Company(String name, LinkedList<Company> allCompanies) {
        this.name = name;
        allCompanies.add(this);
    }

    public boolean isNetworkedWith(Company company) {

    }

    public boolean addNetwork(Company company, LinkedList<Company> allCompanies) {
        if (company == null) return false;
        if (MainHelper.isValidCompany(company.name, allCompanies)) return false;
        if (isNetworkedWith(company)) return false;
    }

    public boolean addFollower(User follower, LinkedList<User> allUsers) {

    }

    public void loadNetworks(String listOfNetworksInStringFormat, LinkedList<Company> allCompanies) {
        String csv = listOfNetworksInStringFormat.substring(1, listOfNetworksInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            Company network = mainHelperMethods.getCompany(strings[i], allCompanies);
            if (network != null) this.networksList.add(network);
        }
    }

    public void loadFollowers(String listOfFollowersInStringFormat, LinkedList<User> allUsers) {
        String csv = listOfFollowersInStringFormat.substring(1, listOfFollowersInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            User follower = mainHelperMethods.getUser(strings[i], allUsers);
            if (follower != null) this.followersList.add(follower);
        }
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
