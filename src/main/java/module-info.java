module com.example.gamewithnetwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;


    requires com.google.gson;
    opens com.example.gamewithnetwork to com.google.gson, javafx.fxml;
    exports com.example.gamewithnetwork;
}