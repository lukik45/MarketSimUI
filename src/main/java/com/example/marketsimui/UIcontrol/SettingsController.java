package com.example.marketsimui.UIcontrol;

import com.example.marketsimui.simulation.World;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    ComboBox<Integer> limitBox;
    @FXML
    Label currentLimitLabel;

    ObservableList<Integer> limitList = FXCollections.observableArrayList(
            100,500,2000,50000, 10000
    );

    int newLimit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        limitBox.setItems(limitList);
        currentLimitLabel.setText("Now it's " + World.gettPerSecLimit());
    }


    public void saveAction(ActionEvent event) {
        if (limitBox.getValue() == null){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("You haven't changed anything");
            a.show();
        }
        newLimit = limitBox.getValue();
        World.settPerSecLimit(newLimit);
        currentLimitLabel.setText("Now it's " + World.gettPerSecLimit());

    }


}
