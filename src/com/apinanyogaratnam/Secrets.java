package com.apinanyogaratnam;

public class Secrets {
    // freemyhosting db login info
    private final String urlMySQL = "jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5416936";
    private final String usernameMySQL = "sql5416936";
    private final String passwordMySQL = "6RPSZwqWzY";

    public String getUrl() {
        // postgres local db login info
        return "jdbc:postgresql://localhost/test";
    }

    public String getUsername() {
        return "apinan";
    }

    public String getPassword() {
        return  "admin";
    }
}
