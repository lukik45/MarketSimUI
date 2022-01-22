package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class StockMarket extends Market{
    Country country;
    ObservableList<Index> indexes;
    public StockMarket(String name, String type, Country country) {
        super(name, type);
        this.country = country;
        indexes = FXCollections.observableArrayList();
    }

    public Country getCountry() {
        return country;
    }

    public ObservableList<Index> getIndexes() {
        return indexes;
    }

    public void addIndex(Index i) {
        indexes.add( i);

    }
}
