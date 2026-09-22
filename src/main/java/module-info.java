module com.clase {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;

    opens com.clase to javafx.fxml;
    exports com.clase;
}
