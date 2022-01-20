package com.example.marketsimui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockMarketController  {
    private World world;


    public StockMarketController(World w) {
        world = w;
        System.out.println(world.isPaused());
    }













//    public void openAdditionMenu(ActionEvent event) throws IOException {
//        // I initialize and load new window here
//        Stage stage = new Stage();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddStockMarket_view.fxml"));
//        fxmlLoader.setControllerFactory(c -> {
//            return new MainController(world);
//        });
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Add Market");
//        stage.setScene(scene);
//        stage.show();
//    }






}
