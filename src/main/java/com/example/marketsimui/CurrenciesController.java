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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CurrenciesController implements Initializable {

    @FXML TableView<Currency> table;
    @FXML private TableColumn<Currency, String> curr_name;
    @FXML private TableColumn<Currency, Float> curr_price;

    @FXML private ComboBox<String> currenciesBox;

    @FXML private ListView<String> validInCountriesList;
    @FXML private LineChart<String, Float> priceChart;
    private Currency selected_currency;

    ObservableList<String> currenciesConvertList;
    public ObservableList<Currency> currList;

    public void loadData() {
        currenciesConvertList = FXCollections.observableArrayList(
                World.getCurrencies().keySet());

        currList = FXCollections.observableArrayList(
                World.getCurrencies().values());

        currenciesBox.setItems(currenciesConvertList);
        updateCurrenciesTable();
    }

    public void updateCurrenciesTable() {
        curr_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        curr_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.setItems(currList);
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        new Thread(refresher).start();
    }

    private void updateInfo(){
        priceChart.getData().clear();
        XYChart.Series<String, Float> series1 = selected_currency.getChartCoords();
        priceChart.getData().add(series1);
        validInCountriesList.setItems(selected_currency.getValidCountries());
    }

    // actions
    public void currencySelectedAction(MouseEvent event) {
//        selected_currency = table.getSelectionModel().getSelectedItem();
//        System.out.println(selected_currency.getName());
//        updateInfo();

    }

    public void updateCurrentCurrency(ActionEvent event) {
//        String chosenCurrencyId = currenciesBox.getValue();
//        System.out.println(chosenCurrencyId);
//        World.setCurrentCurrency(chosenCurrencyId);
//        assert Objects.equals(World.getCurrentCurrency().getName(), chosenCurrencyId);
//        if(selected_currency != null) {
//            table.refresh();
//            table.getColumns().get(0).setVisible(false);
//            table.getColumns().get(0).setVisible(true);
//            // todo update info
//            System.out.println("updated, should be changed");
        UtilitiesUI utUI = new UtilitiesUI();
        utUI.updateCurrentCurrency(event);

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
