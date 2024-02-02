package com.example.smart.config;
import com.example.smart.utils.ReadAndWrite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConfig {

    public static Connection connectionDB (ReadAndWrite readAndWrite) {
        Connection cont = null;
        try {
            if (readAndWrite.getDatabaseName() != null && readAndWrite.getUsername() != null &&
                readAndWrite.getPort() != null && readAndWrite.getLocalhost() != null &&
                readAndWrite.getPassword() != null) {
                Class.forName("org.postgresql.Driver");
                cont = DriverManager
                        .getConnection("jdbc:postgresql://" + readAndWrite.getLocalhost() + ":" + readAndWrite.getPort() + "/" + readAndWrite.getDatabaseName(),
                                readAndWrite.getUsername(), readAndWrite.getPassword());

                System.out.println("Connection Database successfully");
                return  cont;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            FileConfig.writeFileLog("DatabaseConfig connectionDB : [ " + e.getMessage() + " ]");
        }
        return null;
    }


    public static void autoCreateTable(Connection connection) {
        try {
            String sqlCreate = "CREATE TABLE smart_input (\n" +
                    "\tid serial PRIMARY KEY,\n" +
                    "\tmodule_name VARCHAR ( 50 ) NOT NULL,\n" +
                    "\tclass_name VARCHAR ( 50 ) NOT NULL,\n" +
                    "\tjob_name VARCHAR ( 50 ) NOT NULL,\n" +
                    "\tinput_string Text NOT NULL,\n" +
                    "    api_document VARCHAR ( 50 ),\n" +
                    "\tstatus Integer,\n" +
                    "\tlocal_module VARCHAR ( 50 ),\n" +
                    "\tdate TIMESTAMP\n" +
                    ");";

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sqlCreate);
            connection.close();
            System.out.println("Create Table successfully");;
        } catch (Exception e) {
            if (e.getMessage().contains("smart_input")) {
                System.out.println("Table 'smart_input' already create. ");
                FileConfig.writeFileLog("autoCreateTable  : [  Table 'smart_input' already create. ]");
            } else {
                System.out.println(e.getMessage());
                FileConfig.writeFileLog("autoCreateTable  : [ " + e.getMessage() + " ]");
            }

        }

    }

}
