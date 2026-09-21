module com.clase {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.clase to javafx.fxml;
    exports com.clase;
}
