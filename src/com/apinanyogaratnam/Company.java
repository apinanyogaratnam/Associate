package com.apinanyogaratnam;

import java.util.LinkedList;

public class Company {
    Print printClass = new Print();
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

}
