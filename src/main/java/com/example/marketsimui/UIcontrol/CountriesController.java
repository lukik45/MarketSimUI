package com.example.marketsimui.UIcontrol;

import com.example.marketsimui.simulation.Country;
import com.example.marketsimui.simulation.World;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CountriesController implements Initializable {

    @FXML
    TextField nameField;

    @FXML private ComboBox<String> currenciesBox;
    @FXML
    TableView<Country> table;
    @FXML
    TableColumn<Country, String> countryName;
    @FXML
    TableColumn<Country, String> currencyName;


    ObservableList<String> currenciesConvertList;
    ObservableList<Country> countriesList;






    private void loadData() {
        currenciesConvertList = FXCollections.observableArrayList(
                World.getCurrencies().keySet()  // fixme -- may cause troubles
                //"EUR", "GBP", "AUD"
        );
        countriesList = FXCollections.observableArrayList(World.getCountries().values());
        countryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        currencyName.setCellValueFactory(new PropertyValueFactory<>("currencyName"));
        table.setItems(countriesList);

        currenciesBox.setItems(currenciesConvertList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    public void addCountryAction(ActionEvent event){


        if (nameField.getText().equals("") || currenciesBox.getValue() == null){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("Provide the country name and the currency.");
            e.show();

            return;
        }
        if(World.getCountries().containsKey(nameField.getText())){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("Country of given name already exists");
            e.show();
            return;
        }

        String newName = nameField.getText();
        String newCurrencyName = currenciesBox.getValue();
        World.addCountry(newName, newCurrencyName);


        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("""
                    Currency has been added successfully
                    Go to countries menu if you want to use this currency
                    in some country.""");



        loadData();
    }
}
