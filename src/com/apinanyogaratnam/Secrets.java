package com.apinanyogaratnam;

public class Secrets {
    // freemyhosting db login info
    private final String urlMySQL = "jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5416936";
    private final String usernameMySQL = "sql5416936";
    private final String passwordMySQL = "6RPSZwqWzY";

    // postgres local db login info
    private final String urlPostgres = "jdbc:postgresql://localhost/test";
    private final String usernamePostgres = "apinan";
    private final String passwordPostgres = "admin";

    public String getUrl() {
        return this.urlPostgres;
    }

    public String getUsername() {
        return this.usernamePostgres;
    }

    public String getPassword() {
        return this.passwordPostgres;
    }
}
