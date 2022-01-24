package com.example.marketsimui.simulation;

import com.example.marketsimui.simulation.Country;
import com.example.marketsimui.simulation.Index;
import com.example.marketsimui.simulation.Market;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StockMarket extends Market {
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
