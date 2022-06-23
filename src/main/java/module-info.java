module com.example.spoofer {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.desktop;


    opens com.example.spoofer to javafx.fxml;
    exports com.example.spoofer;
}