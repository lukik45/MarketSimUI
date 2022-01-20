package com.example.marketsimui;

import java.util.HashMap;

public class StockMarket extends Market{
    Country country;
    HashMap<String, Index> indexes;
    public StockMarket(String name, String type, Country country) {
        super(name, type);
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}
