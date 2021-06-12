package com.apinanyogaratnam;

import java.sql.*;
import java.util.LinkedList;

abstract class SQL {
    protected static final Secrets secrets = new Secrets();
}

class CreateSQL extends SQL {
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
                firstName = Utils.parseString(firstName);
                String lastName = result.getString("last_name");
                lastName = Utils.parseString(lastName);
                String username = result.getString("username");
                username = Utils.parseString(username);

                // create new user from sql results
                Main.createNewUser(firstName, lastName, username, allUsers, false);
            }

            // close connection
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load users into allUsers");

            return;
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
                username = Utils.parseString(username);
                String friends = result.getString("friends");
                friends = Utils.parseString(friends);
                User user = MainHelper.getUser(username, allUsers);

                // load friends if user exists
                if (user != null) user.loadFriends(friends, allUsers);
            }

            // close connection to db
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
                name = Utils.parseString(name);

                // creating new company from db results
                Main.createNewCompany(name, allCompanies, false);
            }

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load users into allUsers");

            return;
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
                name = Utils.parseString(name);
                String networkList = result.getString("network_list");
                networkList = Utils.parseString(networkList);
                String followersList = result.getString("followers_list");
                followersList = Utils.parseString(followersList);
                Company company = MainHelper.getCompany(name, allCompanies);

                // loading networks and followers is company exists
                if (company != null) {
                    company.loadNetworks(networkList, allCompanies);
                    company.loadFollowers(followersList, allUsers);
                }
            }

            // close connection to db
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load companies into allCompanies");

            return;
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
                username = Utils.parseString(username);
                String companies = result.getString("companies");
                companies = Utils.parseString(companies);

                User user = MainHelper.getUser(username, allUsers);

                // loading companies again to avoid issues with loading followers and networks when companies
                // are not defined yet
                if (user != null) {
                    user.loadCompanies(companies, allCompanies);
                }
            }

            // close connection to db
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
            Print.print("Unable to load users into allUsers");
        }
    }
}

class UpdateSQL extends SQL {
    protected static void addObjectToDB(Object obj) {
        String query;

        // set desired query and error message for adding object to db
        if (obj instanceof User) {
            query = String.format("INSERT INTO users (first_name, last_name, username, friends, companies) VALUES " +
                    "('%s', '%s', '%s', '%s', '%s')", ((User) obj).getFirstName(), ((User) obj).getLastName(), ((User) obj).getUsername(), "{}", "{}");
        } else if (obj instanceof Company) {
            query = String.format("INSERT INTO companies (name, network_list, followers_list) VALUES " +
                    "('%s', '%s', '%s');", ((Company) obj).getName(), "{}", "{}");
        } else {
            Print.print("Object type not supported to add to db.");
            return;
        }

        updateDBWithQuery(query);
    } // tested

    protected static void addFriendHelper(User user, User friend) {
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
                username = Utils.parseString(username);
                if (username.equals(user.getUsername())) {
                    listOfFriendsInStringFormat = result.getString("friends");
                    listOfFriendsInStringFormat = Utils.parseString(listOfFriendsInStringFormat);
                }
            }


            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }

        // check if user already is friends with friend
        String temp = Utils.removeStartEndChars(listOfFriendsInStringFormat);
        if (MainHelper.nameInList(friend.getUsername(), temp)) return;

        String friends = listOfFriendsInStringFormat;
        boolean isEmpty = friends.equals("{}");

        // format string
        friends = Utils.removeEndChar(friends);
        friends = isEmpty ? friends + friend.getUsername() + "}" : friends + "," + friend.getUsername() + "}";

        // update user data
        String query = String.format("UPDATE users SET friends='%s' WHERE username='%s'", friends, user.getUsername());
        updateDBWithQuery(query);
    } // tested

    protected static void addFriend(User user, User friend) {
        addFriendHelper(user, friend);
        addFriendHelper(friend, user);
    } // tested

    protected static void addCompany(User user, Company company) {
        String listOfCompaniesInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // read data from database
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            while (result.next()) {
                String username = result.getString("username");
                username = Utils.parseString(username);
                if (username.equals(user.getUsername())) {
                    listOfCompaniesInStringFormat = result.getString("companies");
                    listOfCompaniesInStringFormat = Utils.parseString(listOfCompaniesInStringFormat);
                }
            }

            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }

        // check if user already is friends with friend
        String temp = Utils.removeStartEndChars(listOfCompaniesInStringFormat);
        if (MainHelper.nameInList(company.getName(), temp)) return;

        String companies = listOfCompaniesInStringFormat;
        boolean isEmpty = companies.equals("{}");
        // format string
        companies = Utils.removeEndChar(companies);
        companies = isEmpty ? companies + company.getName() + "}" : companies + "," + company.getName() + "}";

        // update user data
        String query = String.format("UPDATE users SET companies='%s' WHERE username='%s'", companies, user.getUsername());
        updateDBWithQuery(query);
    }

    protected static void addNetworksHelper(Company company, Company network) {
        String listOfNetworksInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // read data from database
            ResultSet result = statement.executeQuery("SELECT * FROM companies");
            while (result.next()) {
                String username = result.getString("name");
                username = Utils.parseString(username);
                if (username.equals(company.getName())) {
                    listOfNetworksInStringFormat = result.getString("network_list");
                    listOfNetworksInStringFormat = Utils.parseString(listOfNetworksInStringFormat);
                }
            }

            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }

        // check if user already is friends with friend
        String temp = Utils.removeStartEndChars(listOfNetworksInStringFormat);
        if (MainHelper.nameInList(network.getName(), temp)) return;

        String networks = listOfNetworksInStringFormat;
        boolean isEmpty = networks.equals("{}");

        // format string
        networks = Utils.removeEndChar(networks);
        networks = isEmpty ? networks + network.getName() + "}" : networks + "," + network.getName() + "}";

        // update user data
        String query = String.format("UPDATE companies SET network_list='%s' WHERE name='%s'", networks, company.getName());
        updateDBWithQuery(query);
    }

    protected static void addNetwork(Company company, Company network) {
        // using undirected graphs hence two way adding connection
        addNetworksHelper(company, network);
        addNetworksHelper(network, company);
    }

    protected static void addFollowers(Company company, User user) {
        String listOfFollowersInStringFormat = "";
        try {
            // get a connection to database
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

            // create a statement
            Statement statement = connection.createStatement();

            // read data from database
            ResultSet result = statement.executeQuery("SELECT * FROM companies");
            while (result.next()) {
                String companyName = result.getString("name");
                companyName = Utils.parseString(companyName);
                if (companyName.equals(company.getName())) {
                    listOfFollowersInStringFormat = result.getString("followers_list");
                    listOfFollowersInStringFormat = Utils.parseString(listOfFollowersInStringFormat);
                }
            }

            // close connection to server
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }

        // check if user already is friends with friend
        String temp = Utils.removeStartEndChars(listOfFollowersInStringFormat);
        if (MainHelper.nameInList(company.getName(), temp)) return;

        String followers = listOfFollowersInStringFormat;
        boolean isEmpty = followers.equals("{}");

        // format string
        followers = Utils.removeEndChar(followers);
        followers = isEmpty ? followers + user.getUsername() + "}" : followers + "," + company.getName() + "}";

        // update user data
        String query = String.format("UPDATE companies SET followers_list='%s' WHERE name='%s'", followers, company.getName());
        updateDBWithQuery(query);
    }

    protected static void updateDBWithQuery(String query) {
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

    protected static void updateFirstName(User user, String newFirstName) {
        String query = String.format("UPDATE users SET first_name='%s' WHERE username='%s'", newFirstName, user.getUsername());
        updateDBWithQuery(query);
    }

    protected static void updateLastName(User user, String newLastName) {
        String query = String.format("UPDATE users SET last_name='%s' WHERE username='%s'", newLastName, user.getUsername());
        updateDBWithQuery(query);
    }

    protected static void updateUsername(User user, String newUsername) {
        // update every friend of user in db
        String friendsString = "{";
        for (User friend : user.getFriendsList()) {
            friendsString += friend.getUsername() + ",";
        }
        friendsString = Utils.removeStartEndChars(friendsString);
        String [] strings = Utils.splitCommas(friendsString);

        for (String string : strings) {
            try {
                // connect to database
                Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

                // create a statement
                Statement statement = connection.createStatement();

                // execute SQL query
                String query = String.format("SELECT * FROM users WHERE username='%s'", string);
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String usernameFriends = result.getString("friends");
                    usernameFriends = Utils.parseString(usernameFriends);
                    String[] users = Utils.splitCommas(Utils.removeStartEndChars(usernameFriends));
                    usernameFriends = "{";

                    for (int j=0; j<users.length; j++) {
                        if (users[j].equals(user.getUsername())) {
                            users[j] = newUsername;
                        }
                        usernameFriends += users[j] + ",";
                    }
                    usernameFriends = Utils.removeEndChar(usernameFriends) + "}";
                    query = String.format("UPDATE users SET friends='%s' WHERE username='%s'", usernameFriends, string);
                    updateDBWithQuery(query);
                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();

                return;
            }
        }

        // and companies followers list
        String companiesListString = "{";
        for (Company company : user.getCompaniesList()) {
            companiesListString += company.getName() + ",";
        }

        companiesListString = Utils.removeStartEndChars(companiesListString);
        String [] arrayOfCompaniesName = Utils.splitCommas(companiesListString);

        for (String companyName : arrayOfCompaniesName) {
            try {
                // connect to database
                Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());

                // create a statement
                Statement statement = connection.createStatement();

                // execute SQL query
                String query = String.format("SELECT * FROM companies WHERE name='%s'", companyName);
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String followersList = result.getString("followers_list");
                    followersList = Utils.parseString(followersList);
                    String[] users = Utils.splitCommas(Utils.removeStartEndChars(followersList));
                    followersList = "{";

                    for (int j=0; j<users.length; j++) {
                        if (users[j].equals(user.getUsername())) users[j] = newUsername;

                        followersList += users[j] + ",";
                    }
                    followersList = Utils.removeEndChar(followersList) + "}";
                    query = String.format("UPDATE companies SET followers_list='%s' WHERE name='%s'", followersList, companyName);
                    updateDBWithQuery(query);

                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();

                return;
            }
        }

        // sql update username query command
        String query = String.format("UPDATE users SET username='%s' WHERE username='%s'", newUsername, user.getUsername());
        updateDBWithQuery(query);
    }

    protected static void updateName(Company company, String newName) {
        // update every user following company
        String followersString = "{";
        for (User follower : company.getFollowersList()) {
            followersString += follower.getUsername() + ",";
        }
        followersString = Utils.removeStartEndChars(followersString);
        String [] strings = Utils.splitCommas(followersString);

        for (String follower : strings) {
            try {
                Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());
                Statement statement = connection.createStatement();
                String query = String.format("SELECT * FROM users WHERE username='%s'", follower);
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String companiesList = result.getString("companies");
                    companiesList = Utils.parseString(companiesList);
                    String [] companies = Utils.splitCommas(Utils.removeStartEndChars(companiesList));
                    companiesList = "{";

                    for (int j=0; j<companies.length; j++) {
                        if (companies[j].equals(company.getName())) companies[j] = newName;

                        companiesList += companies[j] + ",";
                    }
                    companiesList = Utils.removeEndChar(companiesList) + "}";
                    query = String.format("UPDATE users SET companies='%s' WHERE username='%s'", companiesList, follower);
                    updateDBWithQuery(query);
                }

                // close connection to db
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();

                return;
            }
        }

        String networksList = "{";
        for (Company network : company.getNetworksList()) {
            networksList += network.getName() + ",";
        }

        networksList = Utils.removeStartEndChars(networksList);
        String [] networks = Utils.splitCommas(networksList);

        for (String networkName : networks) {
            try {
                Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());
                Statement statement = connection.createStatement();
                String query = String.format("SELECT * FROM companies WHERE name='%s'", networkName);
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    String networksNetworksList = result.getString("network_list");
                    networksNetworksList = Utils.parseString(networksNetworksList);
                    String [] companies = Utils.splitCommas(Utils.removeStartEndChars(networksNetworksList));
                    networksNetworksList = "{";

                    for (int j=0; j<companies.length; j++) {
                        if (companies[j].equals(company.getName())) companies[j] = newName;

                        networksNetworksList += companies[j] + ",";
                    }
                    networksNetworksList = Utils.removeEndChar(networksNetworksList) + "}";
                    query = String.format("UPDATE companies SET network_list='%s' WHERE name='%s'", networksNetworksList, networkName);
                    updateDBWithQuery(query);
                }

            } catch (Exception e) {
                e.printStackTrace();

                return;
            }
        }

        String query = String.format("UPDATE companies SET name='%s' WHERE name='%s'", newName, company.getName());
        updateDBWithQuery(query);
    }

    protected static void removeFriendHelper(User user, User friend) {
        try {
            // connecting to db and reading data
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM users WHERE username='%s'", user.getUsername());
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String username = result.getString("friends");
                username = Utils.parseString(username);
                String [] usernameIndexed = Utils.splitCommas(Utils.removeStartEndChars(username));

                String updatedFriendsList = "{";
                if (usernameIndexed.length > 1) {
                    for (String s : usernameIndexed) {
                        if (s.equals(friend.getUsername())) continue;

                        updatedFriendsList += s + ",";
                    }
                    updatedFriendsList = Utils.removeEndChar(updatedFriendsList) + "}";
                } else if (usernameIndexed.length == 1) {
                    if (usernameIndexed[0].equals(friend.getUsername())) updatedFriendsList += "}";
                }

                // updating data in db
                query = String.format("UPDATE users SET friends='%s' WHERE username='%s'", updatedFriendsList, user.getUsername());
                updateDBWithQuery(query);
            }

            // closing connection to db
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected  static void removeFriend(User user, User friend) {
        // undirected graphs hence two way connection
        removeFriendHelper(user, friend);
        removeFriendHelper(friend, user);
    }

    protected static void removeCompany(User user, Company company) {
        try {
            // connecting to db
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM users WHERE username='%s'", user.getUsername());
            ResultSet result = statement.executeQuery(query);

            // remove company from companies list
            while (result.next()) {
                String companies = result.getString("companies");
                companies = Utils.parseString(companies);
                String [] companiesIndexed = Utils.indexList(companies);

                String newCompaniesList = "{";
                for (String companyFromList : companiesIndexed) {
                    if (companyFromList.equals(company.getName())) continue;
                    newCompaniesList += companyFromList + ",";
                }

                if (newCompaniesList.length() == 1) newCompaniesList = "{}";
                else newCompaniesList = Utils.removeEndChar(newCompaniesList) + "}";

                query = String.format("UPDATE users SET companies='%s' WHERE username='%s'", newCompaniesList, user.getUsername());
                updateDBWithQuery(query);
            }

            // remove user from followers list
            query = String.format("SELECT * FROM companies WHERE name='%s'", company.getName());
            result = statement.executeQuery(query);

            while (result.next()) {
                String followers = result.getString("followers_list");
                followers = Utils.parseString(followers);
                String [] followersIndexed = Utils.indexList(followers);

                // parsing string for db query
                String newFollowersList = "{";
                for (String followerFromList : followersIndexed) {
                    if (followerFromList.equals(user.getUsername())) continue;
                    newFollowersList += followerFromList + ",";
                }
                if (newFollowersList.length() == 1) newFollowersList = "{}";
                else newFollowersList = Utils.removeEndChar(newFollowersList) + "}";

                query = String.format("UPDATE companies SET followers_list='%s' WHERE name='%s'", newFollowersList, company.getName());
                updateDBWithQuery(query);
            }

            // close connection to db
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void removeNetworkHelper(Company company, Company network) {
        try {
            // connect to db and read data
            Connection connection = DriverManager.getConnection(secrets.getUrl(), secrets.getUsername(), secrets.getPassword());
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM companies WHERE name='%s'", company.getName());
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String networksList = result.getString("network_list");
                networksList = Utils.parseString(networksList);
                String [] networksIndexed = Utils.indexList(networksList);

                // parse string for db query
                String newNetworksList = "{";
                for (String networkFromList : networksIndexed) {
                    if (networkFromList.equals(network.getName())) continue;

                    newNetworksList += networkFromList + ",";
                }
                if (newNetworksList.length() == 1) newNetworksList = "{}";
                query = String.format("UPDATE companies SET network_list='%s' WHERE name='%s'", newNetworksList, company.getName());
                updateDBWithQuery(query);
            }

            // close connection to db
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void removeNetwork(Company company, Company network) {
        // using undirected graphs hence two way connection
        removeNetworkHelper(company, network);
        removeNetworkHelper(network, company);
    }
}

class DeleteSQL extends SQL {
    // made method reusable by taking generic obj as parameter
    protected static void deleteObjectFromDB(Object obj) {
        String query;

        if (obj instanceof User) {
            query = String.format("DELETE FROM users WHERE username='%s'", ((User) obj).getUsername());
        } else if (obj instanceof Company) {
            query = String.format("DELETE FROM companies WHERE name='%s'", ((Company) obj).getName());
        } else {
            Print.print("Object type not supported to remove from db.");
            return;
        }

        UpdateSQL.updateDBWithQuery(query);
    }
}
