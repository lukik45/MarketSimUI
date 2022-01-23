package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class UtilitiesUI {
    Asset currentAsset;

    @FXML private TableView<Asset> table;
    @FXML private TableColumn<Asset, String> asset_name;
    @FXML private TableColumn<Asset, String> asset_market;
    @FXML private TableColumn<Asset, Float> asset_price;
    @FXML private TableColumn<Asset, Float> asset_tendency;
    public ObservableList<Asset> assetList = FXCollections.observableArrayList(
            // list of asset objects should be passed
            World.getAllAssets().values()
    );


    @FXML private LineChart<String, Float> priceChart;

    @FXML ComboBox<String> currenciesBox;
    ObservableList<String> currenciesConvertList = FXCollections.observableArrayList(
            World.getCurrencies().keySet());


    public void loadAssetTable(){
        asset_name.setCellValueFactory(new PropertyValueFactory<Asset, String>("name"));   //use  the "name" property of our object
        asset_market.setCellValueFactory(new PropertyValueFactory<Asset, String>("market_name"));
        asset_price.setCellValueFactory(new PropertyValueFactory<Asset, Float>("price"));
        asset_tendency.setCellValueFactory(new PropertyValueFactory<Asset, Float>("tendency"));
        table.setItems(assetList);
    }

    public void loadAssetInfo() {
        priceChart.getData().clear();
        XYChart.Series<String, Float> series1 = currentAsset.getChartCoords();
        priceChart.getData().add(series1);

    }

    public static void loadCurrentCurrency() {

    }

    public void atInit(){
        currenciesBox.setItems(currenciesConvertList);
        compareViewList.setItems(compareList);
        loadAssetTable();
        priceChart.setCreateSymbols(false);
    }


    // actions
    public void assetSelectedAction(MouseEvent event) {
        currentAsset = table.getSelectionModel().getSelectedItem();
        currentAsset.printInfo();
        loadAssetInfo();
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
            loadAssetInfo();
            System.out.println("updated, should be changed");
        }
    }


    // comparator
    @FXML private ListView<Asset> compareViewList;
    private ObservableList<Asset> compareList = FXCollections.observableArrayList();

    public void addAssetToCompare(ActionEvent event){
        if (currentAsset == null) {
            Alert e = new Alert(Alert.AlertType.INFORMATION);
            e.setContentText("Click on the asset which you want to add to comparator, then " +
                    "click \"Add\" button.");
            e.show();
            return;
        }
        compareList.add(currentAsset);

    }
    public void clearComparator(ActionEvent event) {
        compareList.clear();
        compareViewList.refresh();
        priceChart.getData().clear();
    }

    public void compare(ActionEvent event) {
        priceChart.getData().clear();

        for (Asset asset : compareList) {
            // get the data
            XYChart.Series<String, Float> series = asset.getChartCoords();
            series.setName(asset.getName());
            priceChart.getData().add(series);
        }
    }
}
