package com.example.smart.config;

import java.io.*;

import com.example.smart.utils.AESCrypto;
import com.example.smart.utils.DataStore;
import com.example.smart.utils.ReadAndWrite;
import org.ini4j.*;
public class FileConfig {
    public static void createIniFile () {
        try {
            String currentPath = new File(".").getCanonicalPath();
            System.out.println(currentPath);

            DataStore.currentPath = currentPath;

            File myObj = new File(currentPath + "\\sitting.ini");
            if (myObj.createNewFile()) {
                DataStore.stringPathIni = myObj.getPath();
                DataStore.iniFileName = myObj.getName();
                System.out.println("File created : " + DataStore.stringPathIni);
            } else {
                System.out.println("File already exists.");
                DataStore.stringPathIni = myObj.getPath();
                DataStore.iniFileName = myObj.getName();
            }

            DataStore.ini = new Ini();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static boolean writeDataToIniFile (ReadAndWrite readAndWrite) {
        try {
            // write data into ini file
            DataStore.ini.clear();
            DataStore.ini.put("database","localhost", AESCrypto.encrypt(readAndWrite.getLocalhost(), DataStore.secretKey));
            DataStore.ini.put("database","post", readAndWrite.getPort());
            DataStore.ini.put("database","database_name", readAndWrite.getDatabaseName());
            DataStore.ini.put("database","username", AESCrypto.encrypt(readAndWrite.getUsername(), DataStore.secretKey));
            DataStore.ini.put("database","password", AESCrypto.encrypt(readAndWrite.getPassword(), DataStore.secretKey));
            DataStore.ini.store(new FileOutputStream(DataStore.stringPathIni));

            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static ReadAndWrite readDataFormIniFile () {
        ReadAndWrite readAndWrite = null;
        try {

            // read data form ini file
            DataStore.ini.load(new FileReader(DataStore.stringPathIni));
            String localhost = DataStore.ini.get("database","localhost", String.class);
            String post = DataStore.ini.get("database","post", String.class);
            String databaseName = DataStore.ini.get("database","database_name", String.class);
            String username = DataStore.ini.get("database","username", String.class);
            String password = DataStore.ini.get("database","password", String.class);

            if ( !localhost.isEmpty() && !post.isEmpty() && !databaseName.isEmpty() &&
                    !username.isEmpty() && password.length() != 0) {
                readAndWrite = new ReadAndWrite("",
                        AESCrypto.decrypt(localhost, DataStore.secretKey),
                        post,
                        databaseName,
                        AESCrypto.decrypt(username, DataStore.secretKey),
                        AESCrypto.decrypt(password, DataStore.secretKey)
                );
            }

            return readAndWrite;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void createLogFile () {
        try {
            String logFilePath = DataStore.currentPath + "\\" + DataStore.folderLogName;
            File file = new File(logFilePath);
            boolean isCreate = file.mkdir();
            if (isCreate) {
                System.out.println("Folder is created successfully");
                DataStore.filLogPath = logFilePath;
                createTxtFileLog();
            } else {
                System.out.println("Folder already exiting");
                DataStore.filLogPath = file.getPath();
                System.out.println("createLogFile : " + DataStore.filLogPath);
                createTxtFileLog();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTxtFileLog() {
        DataStore.txtFileName = DataStore.filLogPath + "\\" + createFileLogName();
        File file = new File( DataStore.txtFileName ); //initialize File object and passing path as argument
        boolean result;
        try
        {
            result = file.createNewFile();  //creates a new file
            if(result)      // test if successfully created a new file
            {
                System.out.println("file created "+file.getCanonicalPath()); //returns the path string
            }
            else
            {
                System.out.println("File already exist at location: "+file.getCanonicalPath());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();    //prints exception if any
        }
    }


    public static void writeFileLog (String strLog) {
        System.out.println(DataStore.txtFileName);
        try {
            FileWriter writer = new FileWriter(DataStore.txtFileName, true);
            BufferedWriter br = new BufferedWriter(writer);
            br.write(strLog);
            br.newLine();
            br.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String createFileLogName (){
        return DataStore.getDate() + ".txt";
    }

}
