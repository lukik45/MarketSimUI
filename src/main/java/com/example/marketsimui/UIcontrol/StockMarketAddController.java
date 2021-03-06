package com.example.marketsimui.UIcontrol;

import com.example.marketsimui.simulation.World;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StockMarketAddController implements Initializable {






    // Below there is the code for addition window
    @FXML
    TextField nameField;
    @FXML
    ComboBox countryNameBox;
    @FXML
    Button addMarketButton;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryNameBox.setItems(World.getCountryNames());  // observable list of all countryNames
    }


    public void addMarketAction() {
        String newName = nameField.getText();
        String chosenCountry = (String) countryNameBox.getValue();
        World.addStockMarket(newName, chosenCountry, "stock");
        nameField.clear();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Market has been added successfully");
        a.show();
    }
}
