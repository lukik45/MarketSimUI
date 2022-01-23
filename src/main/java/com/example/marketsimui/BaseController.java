package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public abstract class BaseController {
    // each controller has current asset
    protected Asset currentAsset;

    // each controller has currencies box
    @FXML ComboBox<String> currenciesBox;
    ObservableList<String> currenciesConvertList = FXCollections.observableArrayList(
            World.getCurrencies().keySet()  // fixme -- may cause troubles
            //"EUR", "GBP", "AUD"
    );

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
    protected abstract void refreshStuff();


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

}
