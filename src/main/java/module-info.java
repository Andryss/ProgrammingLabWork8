module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires java.desktop;


    exports client;
    opens client to javafx.fxml;
}