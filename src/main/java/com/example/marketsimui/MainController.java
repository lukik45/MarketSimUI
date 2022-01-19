package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    World world;


    // middle segment fields
    @FXML private TableView<Asset> table;
    @FXML private TableColumn<Asset, String> asset_name;
    @FXML private TableColumn<Asset, String> asset_market;
    @FXML private TableColumn<Asset, Float> asset_price;
    @FXML private TableColumn<Asset, Float> asset_tendency;

    public ObservableList<Asset> assetList = FXCollections.observableArrayList(
            // list of asset objects should be passed
            World.getAllAssets().values()
    );

    // right segment fields
    @FXML
    ComboBox<String> currenciesBox;
    ObservableList<String> currenciesList = FXCollections.observableArrayList(
            "USD", "CHF", "UAD");





    public MainController(World w){
        world = w;
    }
    public void updateAssetTable() {
        // asset table
        asset_name.setCellValueFactory(new PropertyValueFactory<Asset, String>("name"));   //use  the "name" property of our object
        asset_market.setCellValueFactory(new PropertyValueFactory<Asset, String>("market_name"));
        asset_price.setCellValueFactory(new PropertyValueFactory<Asset, Float>("price"));
        asset_tendency.setCellValueFactory(new PropertyValueFactory<Asset, Float>("tendency"));
        table.setItems(assetList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //currency setter
        currenciesBox.setItems(currenciesList);

        updateAssetTable();






    }

    // Central segment functions



    // Right segment functions

    public void resumeSim(ActionEvent event) {
        if (world.isPaused())
            world.resume_();
    }

    public void pauseSim(ActionEvent event) {
        if (!world.isPaused()) {
            world.pause();
        }
    }

    public void openMarket(ActionEvent event) {
        // todo -- choose the type of market and open appropriate market window
    }

    public void openCountries(ActionEvent event) {
        // todo -- open countries window
    }
    public void openCompanies(ActionEvent event) {
        // todo -- open companies window
    }
    public void openCommodities(ActionEvent event) {
        // todo -- open commodities window
    }
    public void openCurrencies(ActionEvent event) {
        // todo -- open currencies window
    }
    public void openInvestors(ActionEvent event) {
        // todo -- open investors window
    }
    public void openFunds(ActionEvent event) {
        // todo -- open finds window
    }

    public void updateCurrentCurrency(ActionEvent event) {
        String chosenCurrencyId = currenciesBox.getValue();
        System.out.println(chosenCurrencyId);
        World.setCurrentCurrency(chosenCurrencyId);
        assert Objects.equals(World.getCurrentCurrency().getName(), chosenCurrencyId);
    }

}