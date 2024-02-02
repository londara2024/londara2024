package com.example.smart.utils;

public class ReadAndWrite {
    private String type;
    private String localhost;
    private String port;
    private String databaseName;
    private String username;
    private String password;

    public ReadAndWrite(String type, String localhost, String port, String databaseName, String username, String password) {
        this.localhost = localhost;
        this.port = port;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public String getLocalhost() {
        return localhost;
    }

    public String getPort() {
        return port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocalhost(String localhost) {
        this.localhost = localhost;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ReadAndWrite{" +
                "type='" + type + '\'' +
                ", localhost='" + localhost + '\'' +
                ", port='" + port + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
