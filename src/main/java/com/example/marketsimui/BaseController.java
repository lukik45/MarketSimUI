package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {
    // each controller has current asset
    protected Asset currentAsset;

    // each controller has currencies box
    ObservableList<String> currenciesConvertList;
    @FXML ComboBox<String> currenciesBox;


    // each controller has a table but with different implementations
    @FXML TableView<Asset> table;
    // columns have to be initialized in child class
    public abstract void loadAssetTable();


    // each controller has some chart
    @FXML LineChart<String, Float> priceChart;
    // and some method to populate the chart (loadAssetInfo)
    public abstract void loadAssetInfo();


    // each has the refresher, but different things are to be refreshed
    Runnable refresher = new Runnable() {
        @Override
        public void run() {
            while(!Thread.interrupted()) {
                refreshStuff();  // table etc
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * Method used by refreshing thread
     */
    protected void refreshStuff(){
        table.refresh();
    }


    public void assetSelectedAction(MouseEvent event) {

        currentAsset = table.getSelectionModel().getSelectedItem();
        currentAsset.printInfo();
        assert currentAsset != null;
        loadAssetInfo();
    }


    // method to change current currency
    public void updateCurrentCurrency(ActionEvent event) {
        String chosenCurrencyId = currenciesBox.getValue();
        System.out.println(chosenCurrencyId);
        World.setCurrentCurrency(chosenCurrencyId);
        assert Objects.equals(World.getCurrentCurrency().getName(), chosenCurrencyId);
        if(currentAsset != null) {
            refreshStuff();
            table.getColumns().get(0).setVisible(false);
            table.getColumns().get(0).setVisible(true);
            loadAssetInfo();
            System.out.println("updated, should be changed");
        }
    }

    public void resumeSim(ActionEvent event) {
        if (World.isPaused())
            World.resume_();
    }

    public void pauseSim(ActionEvent event) {
        if (!World.isPaused()) {
            World.pause();
        }
    }



    public void loadData() {
        currenciesConvertList = FXCollections.observableArrayList(
                World.getCurrencies().keySet());  // fixme -- may cause troubles
        currenciesBox.setItems(currenciesConvertList);
        loadAssetTable();

        // then some specific loads for each controller
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        loadData();
        priceChart.setCreateSymbols(false);
        new Thread(refresher).start();

    }

}
