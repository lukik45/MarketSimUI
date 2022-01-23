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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {

    // table fields
    @FXML private TableColumn<Asset, String> asset_name;
    @FXML private TableColumn<Asset, String> asset_market;
    @FXML private TableColumn<Asset, Float> asset_price;
    @FXML private TableColumn<Asset, Float> asset_tendency;


    public ObservableList<Asset> assetList;

    @FXML Label assetType;

    @FXML ListView<Asset> compareViewList;
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

        for (Asset asset : compareList){
            // get the data
            XYChart.Series<String, Float> series = asset.getChartCoords();
            series.setName(asset.getName());
            priceChart.getData().add(series);

        }

    }


    public void loadAssetTable() {
        // asset table
        asset_name.setCellValueFactory(new PropertyValueFactory<Asset, String>("name"));   //use  the "name" property of our object
        asset_market.setCellValueFactory(new PropertyValueFactory<Asset, String>("market_name"));
        asset_price.setCellValueFactory(new PropertyValueFactory<Asset, Float>("price"));
        asset_tendency.setCellValueFactory(new PropertyValueFactory<Asset, Float>("tendency"));
        table.setItems(assetList);
    }

    /**
     * Update the chart and the details
     */
    public void loadAssetInfo() {
        priceChart.getData().clear();
        XYChart.Series<String, Float> series1 = currentAsset.getChartCoords();

        priceChart.getData().add(series1);
        assetType.setText(currentAsset.getMarket().getType());  // fixme -- to remove
    }


    public void loadData(){
        assetList = FXCollections.observableArrayList(
                World.getAllAssets().values());
        super.loadData();
        //
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        compareViewList.setItems(compareList);
    }

    // Right segment functions (specific for this controller)
    public void openStockMarket(ActionEvent event) throws IOException {
        // I initialize and load new window here
        System.out.println("dupa blada");
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StockMarkets_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Stock Markets");
        stage.setScene(scene);
        stage.show();
    }
    public void openCommodityMarket(ActionEvent event) throws IOException {
        System.out.println("dupa blada");
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Commodities_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Currency market");
        stage.setScene(scene);
        stage.show();
    }
    public void openCurrencyMarket(ActionEvent event) throws IOException {
        // I initialize and load new window here
        System.out.println("dupa blada");
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Currencies_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Currency market");
        stage.setScene(scene);
        stage.show();
    }
    public void openCountries(ActionEvent event) throws IOException {
        System.out.println("dupa blada");
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Countries_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Countries");
        stage.setScene(scene);
        stage.show();
    }
    public void openCompanies(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CompanyAdd_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add company");
        stage.setScene(scene);
        stage.show();
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

}