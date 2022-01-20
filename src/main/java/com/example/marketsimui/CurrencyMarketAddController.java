package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CurrencyMarketAddController implements Initializable {
    private Currency relative_currency;

    @FXML private ComboBox<String> currenciesBox;
    @FXML
    TextField nameField;
    @FXML
    TextField priceField;

    ObservableList<String> currenciesConvertList = FXCollections.observableArrayList(
            World.getCurrencies().keySet()  // fixme -- may cause troubles
            //"EUR", "GBP", "AUD"
    );


    public void specifyRelativeCurrency(ActionEvent event) {
        relative_currency = World.getCurrencies().get(currenciesBox.getValue());
    }


    public void addCurrencyAction(ActionEvent event) {

        if (relative_currency == null){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("You must specify the currency for which you specify the exchange rate!");
            e.show();
            return;
        }

        String newName = nameField.getText();
        try {
            float newPrice = Float.parseFloat(priceField.getText());

            // convert the price to the absolute value:
            newPrice = newPrice * relative_currency.getPrice();

            World.addCurrency(newName, newPrice);
            nameField.clear();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("""
                    Currency has been added successfully
                    Go to countries menu if you want to use this currency
                    in some country.""");
            a.show();
        } catch (NumberFormatException ex) {
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("Provide the number as exchange rate parameter!");
            e.show();
            priceField.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currenciesBox.setItems(currenciesConvertList);
    }
}
