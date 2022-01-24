package com.example.marketsimui.UIcontrol;

import com.example.marketsimui.simulation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyAddController implements Initializable{
    private Currency relative_currency;


    @FXML
    private ComboBox<String> currenciesBox;
    @FXML
    TextField nameField;
    @FXML Slider percentIssuedSlider;
    @FXML ComboBox<StockMarket> marketBox;

    @FXML TextField priceField;

    ObservableList<Index> selectedIndexes;
    StockMarket chosenMarket;

    ObservableList<String> currenciesConvertList = FXCollections.observableArrayList(
            World.getCurrencies().keySet()  // fixme -- may cause troubles
            //"EUR", "GBP", "AUD"
    );


    public void specifyRelativeCurrency(ActionEvent event) {
        relative_currency = World.getCurrencies().get(currenciesBox.getValue());
    }


    public void addCompanyAction(ActionEvent event) {

        if (relative_currency == null){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("You must specify the currency for which you specify the exchange rate!");
            e.show();
            return;
        }

        if(World.getCompanies().containsKey(nameField.getText())){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("Company of given name already exists");
            e.show();
            return;
        }
        String newName = nameField.getText();
        try {
            float newPrice = Float.parseFloat(priceField.getText());
            float newPercentIssued = (float) percentIssuedSlider.getValue() / 100;
            // convert the price to the absolute value:
            newPrice = newPrice * relative_currency.getPrice();
            chosenMarket = marketBox.getValue();
            if (chosenMarket == null) {
                Alert e = new Alert(Alert.AlertType.ERROR);
                e.setContentText("Please select market.");
                e.show();
                return;
            }
            if(selectedIndexes.size() == 0){
                Alert e = new Alert(Alert.AlertType.ERROR);
                e.setContentText("Please select at least one index from the list and pres \"Select\".");
                e.show();
                return;
            }

            // if everything is filled properly:

            Company newCompany = new Company(newName, chosenMarket, newPrice, newPercentIssued);
            // the above method creates shares and adds the company to all necessary containers

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Company has been added successfully.");
            a.show();
        } catch (NumberFormatException ex) {
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("Provide the number as exchange rate parameter!");
            e.show();
            priceField.clear();
        }
    }

    // index selection
    @FXML
    ListView<Index> indexListView;


    public void populateIndexList() {
        chosenMarket = marketBox.getValue();
        ObservableList<Index> indexList = chosenMarket.getIndexes();
        indexListView.setItems(indexList);
        indexListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void selectIndexes(ActionEvent event) {
        selectedIndexes = indexListView.getSelectionModel().getSelectedItems();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currenciesBox.setItems(currenciesConvertList);
        marketBox.setItems(World.getStockMarkets());
        percentIssuedSlider.setShowTickMarks(true);
        percentIssuedSlider.setShowTickLabels(true);

    }
}
