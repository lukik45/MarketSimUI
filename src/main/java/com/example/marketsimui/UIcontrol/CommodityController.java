package com.example.marketsimui.UIcontrol;

import com.example.marketsimui.HelloApplication;
import com.example.marketsimui.simulation.Asset;
import com.example.marketsimui.simulation.World;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommodityController extends BaseController implements Initializable {


    @FXML private TableColumn<Asset, String> comm_name;
    @FXML private TableColumn<Asset, Float> comm_price;



    public ObservableList<Asset> assetList;


    public void loadAssetTable() {
        comm_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        comm_price.setCellValueFactory(new PropertyValueFactory<>("relativePrice"));
        table.setItems(assetList);
    }


    public void loadAssetInfo(){
        priceChart.getData().clear();
        XYChart.Series<String, Float> series1 = currentAsset.getChartCoords();
        priceChart.getData().add(series1);
    }

    @Override
    public void loadData() {
        assetList = FXCollections.observableArrayList(
                World.getCommodities().values());
        super.loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
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
