package com.apinanyogaratnam;

import java.sql.*;
import java.util.LinkedList;

public class SQL {
    private static final Secrets secrets = new Secrets();

    protected static void loadDB(LinkedList<User> allUsers, LinkedList<Company> allCompanies) {
        loadDBUserData(allUsers);
        loadDBCompanyData(allCompanies, allUsers);
    }

    protected static void loadDBUserData(LinkedList<User> allUsers) {
        try {
            // connect to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // execute SQL query
            String query = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String username = result.getString("username");
                Main.createNewUser(firstName, lastName, username, allUsers, false);
            }

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load users into allUsers");
        }

        // second time needed for loading before defining a user
        try {
            // connect to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // execute SQL query
            String query = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String username = result.getString("username");
                String friends = result.getString("friends");
                User user = MainHelper.getUser(username, allUsers);

                if (user != null) user.loadFriends(friends, allUsers);
            }

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load users into allUsers");
        }
    }

    protected static void loadDBCompanyData(LinkedList<Company> allCompanies, LinkedList<User> allUsers) {
        try {
            // connect to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // execute SQL query
            String query = "SELECT * FROM companies";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String name = result.getString("name");
                Main.createNewCompany(name, allCompanies, false);
            }

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load users into allUsers");
        }

        // needed second time to load all things since companies can be loaded before they are defined
        try {
            // connect to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // execute SQL query
            String query = "SELECT * FROM companies";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String name = result.getString("name");
                String networkList = result.getString("network_list");
                String followersList = result.getString("followers_list");
                Company company = MainHelper.getCompany(name, allCompanies);

                if (company != null) {
                    company.loadNetworks(networkList, allCompanies);
                    company.loadFollowers(followersList, allUsers);
                }
            }

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load companies into allCompanies");
        }

        try {
            // connect to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // execute SQL query
            String query = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String username = result.getString("username");
                String companies = result.getString("companies");

                User user = MainHelper.getUser(username, allUsers);
                if (user != null) {
                    user.loadCompanies(companies, allCompanies);
                }
            }

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load users into allUsers");
        }
    }

    protected void addObjectToDB(Object obj) {
        String query;

        // set desired query and error message for adding object to db
        if (obj instanceof User) {
            query = String.format("INSERT INTO users (first_name, last_name, username, friends, companies) VALUES " +
                    "(\"%s\", \"%s\", \"%s\", \"%s\", \"%s\")", ((User) obj).getFirstName(), ((User) obj).getLastName(), ((User) obj).getUsername(), "{}", "{}");
        } else if (obj instanceof Company) {
            query = String.format("INSERT INTO companies (name, network_list, followers_list) VALUES " +
                    "(\"%s\", \"%s\", \"%s\");", ((Company) obj).name, "{}", "{}");
        } else {
            Print.print("Object type not supported to add to db.");
            return;
        }

        updateDBWithQuery(query);
    }

    protected void addFriendHelper(User user, User friend) {
        String listOfFriendsInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // insert data into database
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            while (result.next()) {
                String username = result.getString("username");
                if (username.equals(user.getUsername())) {
                    listOfFriendsInStringFormat = result.getString("friends");
                }
            }


            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if user already is friends with friend
        String temp = listOfFriendsInStringFormat.substring(1, listOfFriendsInStringFormat.length()-1);
        if (MainHelper.nameInList(friend.getUsername(), temp)) return;

        String friends = listOfFriendsInStringFormat;
        boolean isEmpty = friends.equals("{}");

        // format string
        friends = friends.substring(0, friends.length() - 1);
        friends = isEmpty ? friends + friend.getUsername() + "}" : friends + "," + friend.getUsername() + "}";

        // update user data
        String query = String.format("UPDATE users SET friends=\"%s\" WHERE username=\"%s\"", friends, user.getUsername());
        updateDBWithQuery(query);
    } // tested

    protected void addFriend(User user, User friend) {
        addFriendHelper(user, friend);
        addFriendHelper(friend, user);
    } // tested

    protected void addCompany(User user, Company company) {
        String listOfCompaniesInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // insert data into database
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            while (result.next()) {
                String username = result.getString("username");
                if (username.equals(user.getUsername())) {
                    listOfCompaniesInStringFormat = result.getString("companies");
                }
            }


            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if user already is friends with friend
        String temp = listOfCompaniesInStringFormat.substring(1, listOfCompaniesInStringFormat.length()-1);
        if (MainHelper.nameInList(company.name, temp)) return;

        String companies = listOfCompaniesInStringFormat;
        boolean isEmpty = companies.equals("{}");

        // format string
        companies = companies.substring(0, companies.length() - 1);
        companies = isEmpty ? companies + company.name + "}" : companies + "," + company.name + "}";

        // update user data
        String query = String.format("UPDATE users SET companies=\"%s\" WHERE username=\"%s\"", companies, user.getUsername());
        updateDBWithQuery(query);
    }

    protected void addNetworksHelper(Company company, Company network) {
        String listOfNetworksInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // insert data into database
            ResultSet result = statement.executeQuery("SELECT * FROM companies");
            while (result.next()) {
                String username = result.getString("name");
                if (username.equals(company.name)) {
                    listOfNetworksInStringFormat = result.getString("network_list");
                }
            }


            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if user already is friends with friend
        String temp = listOfNetworksInStringFormat.substring(1, listOfNetworksInStringFormat.length()-1);
        if (MainHelper.nameInList(network.name, temp)) return;

        String networks = listOfNetworksInStringFormat;
        boolean isEmpty = networks.equals("{}");

        // format string
        networks = networks.substring(0, networks.length() - 1);
        networks = isEmpty ? networks + network.name + "}" : networks + "," + network.name + "}";

        // update user data
        String query = String.format("UPDATE companies SET network_list=\"%s\" WHERE name=\"%s\"", networks, company.name);
        updateDBWithQuery(query);
    } // tested

    protected void addNetwork(Company company, Company network) {
        addNetworksHelper(company, network);
        addNetworksHelper(network, company);
    } // tested

    protected void addFollowers(Company company, User user) {
        String listOfFollowersInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // insert data into database
            ResultSet result = statement.executeQuery("SELECT * FROM companies");
            while (result.next()) {
                String companyName = result.getString("name");
                if (companyName.equals(company.name)) {
                    listOfFollowersInStringFormat = result.getString("followers_list");
                }
            }


            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if user already is friends with friend
        String temp = listOfFollowersInStringFormat.substring(1, listOfFollowersInStringFormat.length()-1);
        if (MainHelper.nameInList(company.name, temp)) return;

        String followers = listOfFollowersInStringFormat;
        boolean isEmpty = followers.equals("{}");

        // format string
        followers = followers.substring(0, followers.length() - 1);
        followers = isEmpty ? followers + user.getUsername() + "}" : followers + "," + company.name + "}";

        // update user data
        String query = String.format("UPDATE companies SET followers_list=\"%s\" WHERE name=\"%s\"", followers, company.name);
        updateDBWithQuery(query);
    } // tested

    protected void updateDBWithQuery(String query) {
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // insert data into database
            statement.executeUpdate(query);


            // close connection to server
            connection.close();
        } catch(SQLIntegrityConstraintViolationException e) {
            Print.print("Object already exists in db.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateFirstName(User user, String newFirstName) {
        String query = String.format("UPDATE users SET first_name=\"%s\" WHERE username=\"%s\"", newFirstName, user.getUsername());
        updateDBWithQuery(query);
    } // tested

    protected void updateLastName(User user, String newLastName) {
        String query = String.format("UPDATE users SET last_name=\"%s\" WHERE username=\"%s\"", newLastName, user.getUsername());
        updateDBWithQuery(query);
    } // tested

    protected void updateUsername(User user, String newUsername) {
        // need to also update every friend's list
        // update every friend of user in db
        String friendsString = "{";
        for (User friend : user.friendsList) {
            friendsString += friend.getUsername() + ",";
        }
        friendsString = Utils.removeCurlyBraces(friendsString);
        String [] strings = Utils.splitCommas(friendsString);

        for (String string : strings) {
            try {
                // connect to database
                Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

                // create a statement
                Statement statement = connection.createStatement();

                // execute SQL query
                String query = String.format("SELECT * FROM users WHERE username=\"%s\"", string);
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String usernameFriends = result.getString("friends");
                    String[] users = Utils.splitCommas(Utils.removeCurlyBraces(usernameFriends));
                    usernameFriends = "{";

                    for (int j=0; j<users.length; j++) {
                        if (users[j].equals(user.getUsername())) {
                            users[j] = newUsername;
                        }
                        usernameFriends += users[j] + ",";
                    }
                    usernameFriends = usernameFriends.substring(0, usernameFriends.length() - 1) + "}";
                    query = String.format("UPDATE users SET friends=\"%s\" WHERE username=\"%s\"", usernameFriends, string);
                    updateDBWithQuery(query);
                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // and companies followers list
        String companiesListString = "{";
        for (Company company : user.companiesList) {
            companiesListString += company.name + ",";
        }

        companiesListString = Utils.removeCurlyBraces(companiesListString);
        String [] arrayOfCompaniesName = Utils.splitCommas(companiesListString);

        for (String companyName : arrayOfCompaniesName) {
            try {
                // connect to database
                Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

                // create a statement
                Statement statement = connection.createStatement();

                // execute SQL query
                String query = String.format("SELECT * FROM companies WHERE name=\"%s\"", companyName);
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String followersList = result.getString("followers_list");
                    String[] users = Utils.splitCommas(Utils.removeCurlyBraces(followersList));
                    followersList = "{";

                    for (int j=0; j<users.length; j++) {
                        if (users[j].equals(user.getUsername())) users[j] = newUsername;

                        followersList += users[j] + ",";
                    }
                    followersList = followersList.substring(0, followersList.length()-1) + "}";
                    query = String.format("UPDATE companies SET followers_list=\"%s\" WHERE name=\"%s\"", followersList, companyName);
                    updateDBWithQuery(query);

                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // sql update username query command
        String query = String.format("UPDATE users SET username=\"%s\" WHERE username=\"%s\"", newUsername, user.getUsername());
        updateDBWithQuery(query);
    } // tested

    protected void updateName(Company company, String newName) {
        // when updating name, need to also update networks list and all go through
        // all followers and change their companiesList as well
        // need to also check if company name already exists

        // update every user following company
        String followersString = "{";
        for (User follower : company.followersList) {
            followersString += follower.getUsername() + ",";
        }
        followersString = Utils.removeCurlyBraces(followersString);
        String [] strings = Utils.splitCommas(followersString);

        for (String follower : strings) {
            try {
                Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());
                Statement statement = connection.createStatement();
                String query = String.format("SELECT * FROM users WHERE username=\"%s\"", follower);
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String companiesList = result.getString("companies");
                    String [] companies = Utils.splitCommas(Utils.removeCurlyBraces(companiesList));
                    companiesList = "{";

                    for (int j=0; j<companies.length; j++) {
                        if (companies[j].equals(company.name)) companies[j] = newName;

                        companiesList += companies[j] + ",";
                    }
                    companiesList = companiesList.substring(0, companiesList.length()-1) + "}";
                    query = String.format("UPDATE users SET companies=\"%s\" WHERE username=\"%s\"", companiesList, follower);
                    updateDBWithQuery(query);
                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String networksList = "{";
        for (Company network : company.networksList) {
            networksList += network.name + ",";
        }

        networksList = Utils.removeCurlyBraces(networksList);
        String [] networks = Utils.splitCommas(networksList);

        for (String networkName : networks) {
            try {
                Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());
                Statement statement = connection.createStatement();
                String query = String.format("SELECT * FROM companies WHERE name=\"%s\"", networkName);
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String networksNetworksList = result.getString("network_list");
                    String [] companies = Utils.splitCommas(Utils.removeCurlyBraces(networksNetworksList));
                    networksNetworksList = "{";

                    for (int j=0; j<companies.length; j++) {
                        if (companies[j].equals(company.name)) companies[j] = newName;

                        networksNetworksList += companies[j] + ",";
                    }
                    networksNetworksList = networksNetworksList.substring(0, networksNetworksList.length()-1) + "}";
                    query = String.format("UPDATE companies SET network_list=\"%s\" WHERE name=\"%s\"", networksNetworksList, networkName);
                    updateDBWithQuery(query);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String query = String.format("UPDATE companies SET name=\"%s\" WHERE name=\"%s\"", newName, company.name);
        updateDBWithQuery(query);
    } // tested

    protected void removeFriendHelper(User user, User friend) {

    }

    protected  void removeFriend(User user, User friend) {
        removeFriendHelper(user, friend);
        removeFriendHelper(friend, user);
    }

    protected void removeCompany() {
        // remove company from companies list
        // remove user from followers_list
        // check how i did add company as i did add company with another method
    }

    protected void removeNetworkHelper(Company company, Company network) {

    }

    protected void removeNetwork(Company company, Company network) {
        removeNetworkHelper(company, network);
        removeNetworkHelper(network, company);
    }

    // start delete methods here
    protected void removeObjectFromDB(Object obj) {
        String query = "";

        if (obj instanceof User) {
            query = String.format("INSERT INTO users (first_name, last_name, username, friends) VALUES " +
                    "(\"%s\", \"%s\", \"%s\", \"%s\");", ((User) obj).getFirstName(), ((User) obj).getLastName(), ((User) obj).getUsername(), "{}");
        } else if (obj instanceof Company) {
            query = String.format("INSERT INTO companies (name, network_list, followers_list) VALUES " +
                    "(\"%s\", \"%s\", \"%s\");", ((Company) obj).name, "{}", "{}");
        } else {
            Print.print("Object type not supported to add to db.");
        }

        // complete method
    }

}
