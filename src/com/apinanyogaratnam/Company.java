package com.apinanyogaratnam;

import java.util.LinkedList;

public class Company {
    Print printClass = new Print();
    MainHelper mainHelperMethods = new MainHelper();
    String name;
    LinkedList<Company> networksList = new LinkedList<>();
    LinkedList<User> followersList = new LinkedList<>();

    // initializing a new company
    Company(String name, LinkedList<Company> allCompanies) {
        this.name = name;
        allCompanies.add(this);
    }

    // print company name of all companies
    public void print(LinkedList<Company> listOfCompanies) {
        for (Company companyToPrint : listOfCompanies) {
            printClass.print(companyToPrint.name);
        }
    }

    public void addNetworks(String listOfNetworksInStringFormat, LinkedList<Company> allCompanies) {
        String csv = listOfNetworksInStringFormat.substring(1, listOfNetworksInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            Company network = mainHelperMethods.getCompany(strings[i], allCompanies);
            if (network != null) this.networksList.add(network);
        }
    }

    public void addFollowers(String listOfFollowersInStringFormat, LinkedList<User> allUsers) {
        String csv = listOfFollowersInStringFormat.substring(1, listOfFollowersInStringFormat.length()-1);

        String [] strings = csv.split(",");
        for (int i=0; i<strings.length; i++) {
            User follower = mainHelperMethods.getUser(strings[i], allUsers);
            if (follower != null) this.followersList.add(follower);
        }
    }

}
