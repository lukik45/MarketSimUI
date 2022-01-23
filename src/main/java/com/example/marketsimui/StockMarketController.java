package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StockMarketController  extends BaseController implements Initializable {

    @FXML private TableColumn<Asset, String> stock_name;
    @FXML private TableColumn<Asset, String> stock_market;
    @FXML private TableColumn<Asset, Float> stock_price;
    @FXML private TableColumn<Asset, Float> stock_tendency;

    @FXML private TableView<Index> table2;
    @FXML private TableColumn<Index, String> index_name;
    @FXML private TableColumn<Index, Float> index_value;

    // todo -- index chart  (store records-- its harder)

    public ObservableList<Index> indexList;

    public ObservableList<Asset> assetList = FXCollections.observableArrayList();

    @FXML ComboBox<Market> marketsBox;
    ObservableList<Market> stockMarkets = FXCollections.observableArrayList(
            World.getMarkets().values()
    );

    @Override
    protected void refreshStuff() {
        table.refresh();
        table2.refresh();
    }

    /**
     * Update the chart and the details
     */
    public void loadAssetInfo() {

        priceChart.getData().clear();
        XYChart.Series<String, Float> series1 = currentAsset.getChartCoords();

        priceChart.getData().add(series1);
    }


    public void loadAssetTable() {
        // asset table
        stock_name.setCellValueFactory(new PropertyValueFactory<Asset, String>("name"));   //use  the "name" property of our object
        stock_market.setCellValueFactory(new PropertyValueFactory<Asset, String>("market_name"));
        stock_price.setCellValueFactory(new PropertyValueFactory<Asset, Float>("price"));
        stock_tendency.setCellValueFactory(new PropertyValueFactory<Asset, Float>("tendency"));
        table.setItems(assetList);
    }

    public void loadIndexTable() {
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
        loadAssetTable();
        priceChart.setCreateSymbols(false);

        // run refresher
        new Thread(refresher).start();
    }


    public void marketChosenAction(ActionEvent event) {
        assetList.clear();
        StockMarket chosenMarket = (StockMarket) marketsBox.getValue();
        assetList.addAll(chosenMarket.getAssets().values());
        indexList = chosenMarket.getIndexes();
        loadIndexTable();
    }
}
