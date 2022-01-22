package com.example.marketsimui;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StockMarketController implements Initializable {

    Asset currentAsset;

    // middle segment fields
    @FXML private TableView<Asset> table;
    @FXML private TableColumn<Asset, String> stock_name;
    @FXML private TableColumn<Asset, String> stock_market;
    @FXML private TableColumn<Asset, Float> stock_price;
    @FXML private TableColumn<Asset, Float> stock_tendency;
    @FXML
    LineChart<String, Float> priceChart;

    @FXML private TableView<Index> table2;
    @FXML private TableColumn<Index, String> index_name;
    @FXML private TableColumn<Index, Float> index_value;

    // todo -- index chart  (store records-- its harder)

    public ObservableList<Index> indexList;


    public ObservableList<Asset> assetList = FXCollections.observableArrayList();


    //
    @FXML ComboBox<Market> marketsBox;
    ObservableList<Market> stockMarkets = FXCollections.observableArrayList(
            World.getMarkets().values()
    );

    // right segment fields
    @FXML
    ComboBox<String> currenciesBox;
    ObservableList<String> currenciesConvertList = FXCollections.observableArrayList(
            World.getCurrencies().keySet()  // fixme -- may cause troubles
            //"EUR", "GBP", "AUD"
    );


    Runnable refresher = new Runnable() {
        @Override
        public void run() {
            while(!Thread.interrupted()) {
                table.refresh();
                table2.refresh();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * Update the chart and the details
     */
    public void updateAssetInfo() {

        priceChart.getData().clear();
        XYChart.Series<String, Float> series1 = currentAsset.getChartCoords();

        priceChart.getData().add(series1);
    }

    public void assetSelectedAction(MouseEvent event) {

        currentAsset = table.getSelectionModel().getSelectedItem();
        currentAsset.printInfo();
        updateAssetInfo();
    }


    public void updateAssetTable() {
        // asset table
        stock_name.setCellValueFactory(new PropertyValueFactory<Asset, String>("name"));   //use  the "name" property of our object
        stock_market.setCellValueFactory(new PropertyValueFactory<Asset, String>("market_name"));
        stock_price.setCellValueFactory(new PropertyValueFactory<Asset, Float>("price"));
        stock_tendency.setCellValueFactory(new PropertyValueFactory<Asset, Float>("tendency"));
        table.setItems(assetList);
    }

    public void updateIndexTable() {
        index_name.setCellValueFactory(new PropertyValueFactory<Index, String>("name"));
        index_value.setCellValueFactory(new PropertyValueFactory<Index, Float>("totalValue"));
        table2.setItems(indexList);
    }

    public void openAdditionMenu(ActionEvent event) throws IOException {
        // I initialize and load new window here
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddStockMarket_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Market");
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //currency setter
        marketsBox.setItems(stockMarkets);
        currenciesBox.setItems(currenciesConvertList);
        updateAssetTable();
        //updateIndexTable();

        priceChart.setCreateSymbols(false);


        // run refresher
        new Thread(refresher).start();
    }


    public void marketChosenAction(ActionEvent event) {
        assetList.clear();
        StockMarket chosenMarket = (StockMarket) marketsBox.getValue();
        assetList.addAll(chosenMarket.getAssets().values());
        indexList = chosenMarket.getIndexes();
        updateIndexTable();



    }



    public void updateCurrentCurrency(ActionEvent event) {
        String chosenCurrencyId = currenciesBox.getValue();
        System.out.println(chosenCurrencyId);
        World.setCurrentCurrency(chosenCurrencyId);
        assert Objects.equals(World.getCurrentCurrency().getName(), chosenCurrencyId);
        if(currentAsset != null) {
            table.refresh();
            table.getColumns().get(0).setVisible(false);
            table.getColumns().get(0).setVisible(true);
            updateAssetInfo();
            System.out.println("updated, should be changed");
        }

    }


}
