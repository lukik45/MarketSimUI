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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CommodityController implements Initializable {


    @FXML TableView<Asset> table;
    @FXML private TableColumn<Asset, String> comm_name;
    @FXML private TableColumn<Asset, Float> comm_price;
    @FXML private ComboBox<String> currenciesBox;


    @FXML private LineChart<String, Float> priceChart;
    private Asset selected_commodity;

    ObservableList<String> currenciesConvertList;
    public ObservableList<Asset> commList;



    public void loadData() {
        currenciesConvertList = FXCollections.observableArrayList(
                World.getCurrencies().keySet()  // fixme -- may cause troubles
                //"EUR", "GBP", "AUD"
        );


        commList = FXCollections.observableArrayList(
                World.getCommodities().values()
        );


        currenciesBox.setItems(currenciesConvertList);
        updateCurrenciesTable();
    }




    public void updateCurrenciesTable() {
        comm_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        comm_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.setItems(commList);
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
        XYChart.Series<String, Float> series1 = selected_commodity.getChartCoords();
        priceChart.getData().add(series1);



    }

    // actions
    public void currencySelectedAction(MouseEvent event) {
        selected_commodity = table.getSelectionModel().getSelectedItem();
        System.out.println(selected_commodity.getName());
        updateInfo();
    }


    public void updateCurrentCurrency(ActionEvent event) {
        String chosenCurrencyId = currenciesBox.getValue();
        System.out.println(chosenCurrencyId);
        World.setCurrentCurrency(chosenCurrencyId);
        assert Objects.equals(World.getCurrentCurrency().getName(), chosenCurrencyId);
        if(selected_commodity != null) {
            table.refresh();
            table.getColumns().get(0).setVisible(false);
            table.getColumns().get(0).setVisible(true);
            // todo update info
            System.out.println("updated, should be changed");
        }

    }


    public void openAdditionMenu(ActionEvent event) throws IOException {
        // I initialize and load new window here
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddCommodity_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Commodity");
        stage.setScene(scene);
        stage.showAndWait();
        System.out.println("now motherfuckers");
        loadData();





    }

}