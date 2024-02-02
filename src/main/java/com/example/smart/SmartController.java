package com.example.smart;


import com.example.smart.config.DatabaseConfig;
import com.example.smart.config.FileConfig;
import com.example.smart.utils.DataStore;
import com.example.smart.utils.ReadAndWrite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;




public class SmartController implements Initializable {

    @FXML
    private Button btn_View;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private ImageView btn_help;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_update;

    @FXML
    private TextField main_api;

    @FXML
    private TextField main_className;

    @FXML
    private TextArea main_inputString;

    @FXML
    private TextField main_jobName;

    @FXML
    private TextField main_moduleName;

    @FXML
    private TableView<SmartInput> main_tableView;

    @FXML
    private TableColumn<SmartInput, String> main_col_class_name;

    @FXML
    private TableColumn<SmartInput, Integer> main_col_id;

    @FXML
    private TableColumn<SmartInput, String> main_col_input_string;

    @FXML
    private TableColumn<SmartInput, String> main_col_job_name;

    @FXML
    private TableColumn<SmartInput, String> main_col_module_name;

    @FXML
    private TableColumn<SmartInput, String> main_col_aip_document;

    @FXML
    private TableColumn<SmartInput, String> main_col_local_name;

    @FXML
    private TableColumn<SmartInput, String> main_col_date;

    @FXML
    private TextField main_local_name;

    @FXML
    private TextField search_class;

    @FXML
    private TextField search_job;

    @FXML
    private TextField search_module;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button btn_db_clear_text;

    @FXML
    private Button btn_db_connect;

    @FXML
    private TextField main_db_host;

    @FXML
    private TextField main_db_name;

    @FXML
    private TextField main_db_password;

    @FXML
    private TextField main_db_username;

    @FXML
    private AnchorPane main_form_db_connect;

    @FXML
    private ImageView main_db_status;

    @FXML
    private Label main_db_close;

    @FXML
    private TextField main_db_post;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private String localhost = "localhost";
    private String username = "postgres";
    private String password = "admin";
    private String databaseName = "javafx";

    private String port = "5432";

    ReadAndWrite readAndWrite;

    private int countClick = 0;

    private int openAndCloseDBConnection = 0;

    private Alert alert;

    private boolean isConnection = false;


    //For search
    ObservableList<SmartInput> searchListData = FXCollections.observableArrayList();

    public ObservableList<SmartInput> selectDataFormDB(){
        ObservableList<SmartInput> listData = FXCollections.observableArrayList();
        try {
            connect = DatabaseConfig.connectionDB(readAndWrite);
            String sql = "SELECT * FROM smart_input WHERE status = 1";
            if (connect !=  null) {
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                SmartInput smartInput;

                while (result.next()) {
                    smartInput = new SmartInput(
                            result.getInt("id"),
                            result.getString("module_name"),
                            result.getString("class_name"),
                            result.getString("job_name"),
                            result.getString("input_string"),
                            result.getString("api_document"),
                            result.getInt("status"),
                            result.getString("local_module"),
                            result.getDate("date")
                    );
                    smartInput.setInputString(smartInput.getInputString().replace("\\\"", "\""));
                    listData.add(smartInput);
                }
            } else {
                setDBStatus ();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            FileConfig.writeFileLog("selectDataFormDB : [ " + e.getMessage() + " ]");
        }
        return listData;
    }

    public void ShowDataInTable() {
        try {


            ObservableList<SmartInput> listDataDB = null;
            if (isConnection) {
                listDataDB = selectDataFormDB();
                searchListData = listDataDB;
            }

            main_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            main_col_module_name.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
            main_col_class_name.setCellValueFactory(new PropertyValueFactory<>("className"));
            main_col_job_name.setCellValueFactory(new PropertyValueFactory<>("jobName"));
            main_col_input_string.setCellValueFactory(new PropertyValueFactory<>("inputString"));
            main_col_aip_document.setCellValueFactory(new PropertyValueFactory<>("apiDocument"));
            main_col_local_name.setCellValueFactory(new PropertyValueFactory<>("localModuleName"));
            main_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

            main_tableView.setItems(listDataDB);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            FileConfig.writeFileLog("ShowDataInTable : [ " + e.getMessage() + " ]");
        }

    }

    public void selectedItemInTable(){
        SmartInput smartInput = main_tableView.getSelectionModel().getSelectedItem();
        int numCol = main_tableView.getSelectionModel().getSelectedIndex();

        if  ((numCol - 1) < -1) return;


        main_moduleName.setText(smartInput.getModuleName());
        main_className.setText(smartInput.getClassName());
        main_jobName.setText(smartInput.getJobName());
        main_inputString.setText(smartInput.getInputString());
        main_api.setText(smartInput.getApiDocument());
        main_local_name.setText(smartInput.getLocalModuleName());

        DataStore.id = smartInput.getId();


    }

    public void EditeTextFiled() {
        countClick++;
        if (countClick % 2 != 0) {
            main_moduleName.setEditable(true);
            main_className.setEditable(true);
            main_jobName.setEditable(true);
            main_api.setEditable(true);
            main_inputString.setEditable(true);
            main_local_name.setEditable(true);
            btn_View.setText("Enable Edit");
        } else {
            main_moduleName.setEditable(false);
            main_className.setEditable(false);
            main_jobName.setEditable(false);
            main_api.setEditable(false);
            main_inputString.setEditable(false);
            main_local_name.setEditable(false);
            btn_View.setText("Unable Edite");
        }


    }

    void showMessageBoxError (String title, String contact) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contact);
        alert.showAndWait();
    }

    void showMessageBoxSuccess (String title, String contact) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contact);
        alert.showAndWait();
    }

    public void insertToDatabaseBtnAdd() {

        if (main_moduleName.getText().isEmpty()) {
            showMessageBoxError("Error Message", "Invalid Module Name enter, please check again!!!");
        } else if (main_className.getText().isEmpty() ){
            showMessageBoxError("Error Message", "Invalid Class Name enter, please check again!!!");
        } else if (main_jobName.getText().isEmpty()) {
            showMessageBoxError("Error Message", "Invalid Job Name enter, please check again!!!");
        } else if (main_inputString.getText().isEmpty()) {
            // show message box
            showMessageBoxError("Error Message", "Invalid Input String enter, please check again!!!");
        } else {

            String sqlInsert = "INSERT INTO smart_input (module_name, class_name, job_name, input_string, api_document, status, local_module, date)"
                       + "VALUES (?,?,?,?,?,?,?,?)";

            try {

                String inputString = main_inputString.getText().replace("\"", "\\\"");

                // TO GET CURRENT DATE
                Date date = new Date();
                java.sql.Date slqDate = new java.sql.Date(date.getTime());

                String aipDocument = main_api.getText();
                String localModuleName = main_local_name.getText();

                prepare = connect.prepareStatement(sqlInsert);
                prepare.setString(1, main_moduleName.getText());
                prepare.setString(2, main_className.getText());
                prepare.setString(3, main_jobName.getText());
                prepare.setString(4, inputString);
                prepare.setString(5, aipDocument);
                prepare.setInt(6, 1);
                prepare.setString(7, localModuleName);
                prepare.setDate(8, slqDate);

                prepare.executeUpdate();

                // show message box
                showMessageBoxSuccess("Information Message", "Successfully Added!");

                refreshTable();
                clearTextFiled();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                FileConfig.writeFileLog("insertToDatabaseBtnAdd : [ " + e.getMessage() + " ]");
            }

        }

    }

    public void updateDatabaseBtnUpdate() {
        if (main_moduleName.getText().isEmpty()) {
            showMessageBoxError("Error Message", "Invalid Module Name, please check again!!!");
        } else if (main_className.getText().isEmpty()) {
            showMessageBoxError("Error Message", "Invalid Class Name, please check again!!!");
        } else if (main_jobName.getText().isEmpty()) {
            showMessageBoxError("Error Message", "Invalid Job Name, please check again!!!");
        } else if ( main_inputString.getText().isEmpty() ) {
            showMessageBoxError("Error Message", "Invalid Input String, please check again!!!");
        } else if (DataStore.id == 0) {
            // show message box
            showMessageBoxError("Error Message", "Invalid ID, please check again!!!");
        } else {

            //module_name, class_name, job_name, input_string, api_document, status, local_module, date : column DB
            String updateDatabase = "UPDATE smart_input SET "
                                    + "module_name = '" + main_moduleName.getText() + "', "
                                    + "class_name = '" + main_className.getText() + "', "
                                    + "job_name = '" + main_jobName.getText() + "', "
                                    + "input_string = '" + main_inputString.getText().replace("\\\"", "\"") + "', "
                                    + "api_document = '" + main_api.getText() + "', "
                                    + "status = " + 1 + ", "
                                    + "local_module = '" + main_local_name.getText() + "', "
                                    + "date = '" + DataStore.getDate() + "'"
                                    + " WHERE id = " + DataStore.id;

            try {
                connect = DatabaseConfig.connectionDB(readAndWrite);

                if (connect != null) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to update id = " + DataStore.id);

                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {

                        prepare = connect.prepareStatement(updateDatabase);
                        prepare.executeUpdate();

                        showMessageBoxSuccess("Information", "Successfully Updated!!!");

                        refreshTable();
                        clearTextFiled();
                    } else {
                        showMessageBoxError("Information Message", "Cancelled!!!");
                    }
                } else {
                    showMessageBoxError("Error Message", "Database connection fails, please connect Database again");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                FileConfig.writeFileLog("updateDatabaseBtnUpdate : [ " + e.getMessage() + " ]");
            }

        }
    }

    public void deleteDataBtnDeleted () {

        if (DataStore.id == 0) {

        } else {
            //module_name, class_name, job_name, input_string, api_document, status, local_module, date : column DB
            String deleteDatabase = "UPDATE smart_input SET "
                    + "status = " + 2 + " "
                    + " WHERE id = " + DataStore.id;

            try {
                connect = DatabaseConfig.connectionDB(readAndWrite);

                if (connect == null) {
                    setDBStatus ();
                    showMessageBoxError("Error Message", "Database connection fails, please connect Database again");
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to delete id = " + DataStore.id);

                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {

                        prepare = connect.prepareStatement(deleteDatabase);
                        prepare.executeUpdate();

                        showMessageBoxSuccess("Information", "Successfully Deleted!!!");

                        refreshTable();
                        clearTextFiled();
                    } else {
                        showMessageBoxError("Information Message", "Cancelled!!!");
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                FileConfig.writeFileLog("deleteDataBtnDeleted : [ " + e.getMessage() + " ]");
            }
        }
    }

    public void searchDataInTableBtnSearch () {
        try {
            ObservableList<SmartInput> listDataSearch = null;
            if (isConnection)
                listDataSearch = searchData();

            if (listDataSearch != null) {
                main_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
                main_col_module_name.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
                main_col_class_name.setCellValueFactory(new PropertyValueFactory<>("className"));
                main_col_job_name.setCellValueFactory(new PropertyValueFactory<>("jobName"));
                main_col_input_string.setCellValueFactory(new PropertyValueFactory<>("inputString"));
                main_col_aip_document.setCellValueFactory(new PropertyValueFactory<>("apiDocument"));
                main_col_local_name.setCellValueFactory(new PropertyValueFactory<>("localModuleName"));
                main_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

                main_tableView.setItems(listDataSearch);
            } else {
                ShowDataInTable();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            FileConfig.writeFileLog("searchDataInTableBtnSearch : [ " + e.getMessage() + " ]");
        }
    }

    public SmartInput setSmartInput (SmartInput smartInput) {
        return new SmartInput(
                smartInput.getId(),
                smartInput.getModuleName(),
                smartInput.getClassName(),
                smartInput.getJobName(),
                smartInput.getInputString(),
                smartInput.getApiDocument(),
                smartInput.getStatus(),
                smartInput.getLocalModuleName(),
                smartInput.getDate()
        );
    }

    public ObservableList<SmartInput> searchData () {

        String moduleName = search_module.getText().trim();
        String jobName = search_job.getText().trim();
        String className = search_class.getText().trim();

        if (moduleName.equals("") && jobName.equals("") && className.equals("")) {
            showMessageBoxError("Error Message", "Invalid Enter, please check formation enter again!");
        } else {
            ObservableList<SmartInput> listDataItem = FXCollections.observableArrayList();
            SmartInput smartInput;
            for (SmartInput obj: searchListData) {
                if (!moduleName.isEmpty() && !jobName.isEmpty() && !className.isEmpty()) { // module job class is not empty
                    if (moduleName.contains(obj.getModuleName()) && jobName.contains(obj.getJobName()) && className.contains(obj.getClassName())) {
                        smartInput = setSmartInput(obj);
                        listDataItem.add(smartInput);
                    }
                } else if (!moduleName.isEmpty() && !jobName.isEmpty()) { // module and job is not empty
                    if (moduleName.contains(obj.getModuleName()) && jobName.contains(obj.getJobName())) {
                        smartInput = setSmartInput(obj);
                        listDataItem.add(smartInput);
                    }
                } else if (!moduleName.isEmpty() && !className.isEmpty()) { // module and class is not empty
                    if (moduleName.contains(obj.getModuleName()) && className.contains(obj.getClassName())) {
                        smartInput = setSmartInput(obj);
                        listDataItem.add(smartInput);
                    }
                } else if (!jobName.isEmpty() && !className.isEmpty()) { // job and class is not empty
                    if (jobName.contains(obj.getJobName()) && className.contains(obj.getClassName())) {
                        smartInput = setSmartInput(obj);
                        listDataItem.add(smartInput);
                    }
                } else if (!moduleName.isEmpty()) {
                    if (moduleName.contains(obj.getModuleName())) {
                        smartInput = setSmartInput(obj);
                        listDataItem.add(smartInput);
                    }
                } else if (!jobName.isEmpty()) {
                    if (jobName.contains(obj.getJobName())) {
                        smartInput = setSmartInput(obj);
                        listDataItem.add(smartInput);
                    }
                } else if (!className.isEmpty()) {
                    if (className.contains(obj.getClassName())) {
                        smartInput = setSmartInput(obj);
                        listDataItem.add(smartInput);
                    }
                }
            }
            clearTextFiled();
            return listDataItem;
        }
        return null;
    }

    public void connectionDB () {
        try {
            String testHostAddress, testDatabaseName, testUsername, testPwd, testPort;
            testHostAddress = main_db_host.getText().trim();
            testDatabaseName = main_db_name.getText().trim();
            testUsername = main_db_username.getText().trim();
            testPwd = main_db_password.getText();
            testPort =  main_db_post.getText();

            if (testHostAddress.isEmpty() || testUsername.isEmpty() || testPwd.isEmpty() || testDatabaseName.isEmpty() || testPort.isEmpty()) {
                showMessageBoxError("Error DB Connection", "Invalid enter, please check enter again!!!");
            } else {
                readAndWrite = new ReadAndWrite("", testHostAddress, testPort, testDatabaseName, testUsername, testPwd);
                FileConfig.writeDataToIniFile(readAndWrite);
                connect = DatabaseConfig.connectionDB(readAndWrite);
                if (connect != null) {

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Connection has been successfully.");

                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        openAndCloseDBConnection++;
                        main_form_db_connect.setVisible(false);
                        btn_add.setDisable(false);
                        btn_delete.setDisable(false);
                        btn_update.setDisable(false);
                        main_db_status.setOpacity(1);
                        isConnection = true;
                        DatabaseConfig.autoCreateTable(connect);
                        refreshTable();
                    }

                } else {
                    showMessageBoxError("Error Message", "Connection has been fails!");
                    setDBStatus ();
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            FileConfig.writeFileLog("connectionDB : [ " + e.getMessage() + " ]");
        }
    }

    public void showFromDBConnect () {
        openAndCloseDBConnection++;
        if (openAndCloseDBConnection % 2 != 0) {
            main_form_db_connect.setVisible(true);
            btn_add.setDisable(true);
            btn_delete.setDisable(true);
            btn_update.setDisable(true);
        } else {
            main_form_db_connect.setVisible(false);
            btn_add.setDisable(false);
            btn_delete.setDisable(false);
            btn_update.setDisable(false);
        }
    }

    public void clearTextFiled (){
        main_moduleName.clear();
        main_className.clear();
        main_jobName.clear();
        main_api.clear();
        main_inputString.clear();
        main_local_name.clear();
    }

    public void checkConnectionDB () {
        try {
            readAndWrite = FileConfig.readDataFormIniFile();

            if (readAndWrite != null) {


                localhost = readAndWrite.getLocalhost();
                port = readAndWrite.getPort();
                databaseName = readAndWrite.getDatabaseName();
                username = readAndWrite.getUsername();
                password = readAndWrite.getPassword();

                connect = DatabaseConfig.connectionDB(readAndWrite);
                if (connect != null) {
                    main_db_status.setOpacity(1);
                    isConnection = true;
                    DatabaseConfig.autoCreateTable(connect);
                } else {
                    isConnection = false;
                    main_db_status.setOpacity(0);
                    btn_update.setDisable(true);
                    btn_delete.setDisable(true);
                    btn_add.setDisable(true);
                }
            } else {
                showMessageBoxError("Error Message","Connection fails. Please connection Database again!!!");
            }

        } catch (Exception e) {
            System.out.println("checkConnectionDB :" + e.getMessage());
            FileConfig.writeFileLog("checkConnectionDB : [ " + e.getMessage() + " ]");
        }

    }

    public void setDBStatus () {
        if (connect != null) {
            main_db_status.setOpacity(1);
        } else {
            main_db_status.setOpacity(0);
        }
    }

    public void clearDBTextFiled () {
        main_db_host.clear();
        main_db_username.clear();
        main_db_password.clear();
        main_db_name.clear();
        main_db_post.clear();
    }

    public void refreshTable () {
        ShowDataInTable();
    }

    public void closeFormDBConnection () {
        showFromDBConnect();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main_inputString.setWrapText(true);
        main_form_db_connect.setVisible(false);
        checkConnectionDB();
        ShowDataInTable();
    }

}