package com.example.smart.utils;

import org.ini4j.Ini;

import java.util.Date;

public class DataStore {
    public static String username;
    public static String date;
    public static int id;

    public static Ini ini;
    public static String stringPathIni;
    public static String iniFileName;
    public static final String secretKey = "jdhsjdshueyewiw%$@#@$#7didh()743";

    public static String currentPath;

    public static String filLogPath;

    public static String txtFileName;

    public static final String folderLogName = "logfile";

    public static java.sql.Date getDate () {
        // TO GET CURRENT DATE
        Date date = new Date();
        java.sql.Date slqDate = new java.sql.Date(date.getTime());
        return slqDate;
    }



}
