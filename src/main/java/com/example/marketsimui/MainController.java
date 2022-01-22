package com.example.marketsimui;

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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    World world;
    Asset currentAsset;

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

    // left segment fields
    @FXML Label assetType;

    @FXML
    LineChart<String, Float> priceChart;

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
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };



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

    /**
     * Update the chart and the details
     */
    public void updateAssetInfo() {

        priceChart.getData().clear();
        XYChart.Series<String, Float> series1 = currentAsset.getChartCoords();

        priceChart.getData().add(series1);

//        for (final XYChart.Data<Integer, Float> data : series1.getData()) {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    Tooltip.install(data.getNode(), new Tooltip("X : " + data.getXValue() + "\nY : " + String.valueOf(data.getYValue())));
//                }
//            });
//        }
        assetType.setText(currentAsset.getMarket().getType());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //currency setter
        currenciesBox.setItems(currenciesConvertList);
        compareViewList.setItems(compareList);
        updateAssetTable();
        priceChart.setCreateSymbols(false);


        // run refresher
        new Thread(refresher).start();

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



    // Actions

    public void assetSelectedAction(MouseEvent event) {

        currentAsset = table.getSelectionModel().getSelectedItem();
        currentAsset.printInfo();
        updateAssetInfo();
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