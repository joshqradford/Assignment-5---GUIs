module com.assignment5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.assignment5 to javafx.fxml;
    exports com.assignment5;
}


