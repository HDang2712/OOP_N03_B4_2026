package com.example.servingwebcontent.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class myDBConnection {

    public myDBConnection() {}

    String myDatabaseURL = "jdbc:mysql://YOUR_HOST:YOUR_PORT/defaultdb?ssl-mode=REQUIRED";
    String myDatabaseUser = "avnadmin";
    String myDatabasePassword = "YOUR_PASSWORD";
    String myDatabaseDriver = "com.mysql.cj.jdbc.Driver";

    public Connection conn = null;

    public Statement getMyConn() {
        try {
            Class.forName(myDatabaseDriver);
            conn = DriverManager.getConnection(myDatabaseURL, myDatabaseUser, myDatabasePassword);
            Statement sta = conn.createStatement();
            return sta;
        } catch (Exception e) {
            System.out.println("myDBConnection - getMyConn error: " + e);
        }
        return null;
    }

    public Connection getOnlyConn() {
        try {
            Class.forName(myDatabaseDriver);
            conn = DriverManager.getConnection(myDatabaseURL, myDatabaseUser, myDatabasePassword);
            return conn;
        } catch (Exception e) {
            System.out.println("myDBConnection - getOnlyConn error: " + e);
        }
        return null;
    }
}