module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires jlayer;
    requires javafx.web;
    requires javafx.media;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.Controller;
    opens com.example.demo.Controller to javafx.fxml;
    exports com.example.demo.BasePlus;
    opens com.example.demo.BasePlus to javafx.fxml;
}