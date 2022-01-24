module com.example.marketsimui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.example.marketsimui to javafx.fxml;

    exports com.example.marketsimui.UIcontrol;
    opens com.example.marketsimui.UIcontrol to javafx.fxml;
    exports com.example.marketsimui.simulation;
    opens com.example.marketsimui.simulation to javafx.fxml;
    exports com.example.marketsimui;
}