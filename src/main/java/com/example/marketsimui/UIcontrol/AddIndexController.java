package com.example.marketsimui.UIcontrol;

import com.example.marketsimui.simulation.Asset;
import com.example.marketsimui.simulation.CompanyShares;
import com.example.marketsimui.simulation.Index;
import com.example.marketsimui.simulation.StockMarket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddIndexController implements Initializable {

    @FXML
    TextField nameField;
    @FXML
    ListView<Asset> assetListView;
    ObservableList<Asset> assetList;

    StockMarket myMarket;
    public AddIndexController(StockMarket myMarket) {
        this.myMarket = myMarket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assetList = FXCollections.observableArrayList(myMarket.getAssets().values());
        assetListView.setItems(assetList);
        assetListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    public void AddIndexAction(ActionEvent event) {
        if (nameField.getText().equals("")){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("Name field is empty!");
            e.show();
            return;
        }
        for (Index i : myMarket.getIndexes()) {
            if (i.getName().equals(nameField.getText())){
                Alert e = new Alert(Alert.AlertType.ERROR);
                e.setContentText("Index of given name already exists!");
                e.show();
                return;
            }
        }
        ObservableList<Asset> selectedAssets = assetListView.getItems();
        if(selectedAssets.size() == 0){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setContentText("Select at least one company from the list!");
            e.show();
            return;
        }

        Index newIndex = new Index(nameField.getText(), myMarket);
        myMarket.addIndex(newIndex);


        for (Asset a : selectedAssets) {
            newIndex.addCompanyShares((CompanyShares) a);
        }
        Alert e = new Alert(Alert.AlertType.CONFIRMATION);
        e.setContentText("Index has been added successfully");
        e.show();

        nameField.clear();
        assetListView.getSelectionModel().clearSelection();




    }
}
