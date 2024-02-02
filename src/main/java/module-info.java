module com.example.smart {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ini4j;
    requires org.postgresql.jdbc;


    opens com.example.smart to javafx.fxml;
    exports com.example.smart;
}