package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CurrenciesController extends BaseController implements Initializable {


    @FXML private TableColumn<Currency, String> curr_name;
    @FXML private TableColumn<Currency, Float> curr_price;


    @FXML private ListView<String> validInCountriesList;

    ObservableList<String> currenciesConvertList;
    public ObservableList<Asset> assetList;



    public void loadAssetTable() {
        curr_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        curr_price.setCellValueFactory(new PropertyValueFactory<>("relativePrice"));
        table.setItems(assetList);
    }

    public void loadData() {
        assetList = FXCollections.observableArrayList(
                World.getCurrencies().values());
        super.loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }


    public void loadAssetInfo(){
        priceChart.getData().clear();
        XYChart.Series<String, Float> series1 = currentAsset.getChartCoords();
        priceChart.getData().add(series1);
        validInCountriesList.setItems(((Currency)currentAsset).getValidCountries());
    }


    public void openAdditionMenu(ActionEvent event) throws IOException {
        // I initialize and load new window here
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddCurrency_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Currency");
        stage.setScene(scene);
        stage.showAndWait();
        System.out.println("now motherfuckers");
        loadData();
    }
}
