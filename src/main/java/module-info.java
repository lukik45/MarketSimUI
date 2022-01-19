module com.example.marketsimui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.example.marketsimui to javafx.fxml;
    exports com.example.marketsimui;
}