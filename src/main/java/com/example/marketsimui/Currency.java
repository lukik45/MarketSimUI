package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.Inflater;

public class Currency extends Asset{
    private String curr_name;
    private Set<Country> legal_in;
    private final int n_on_market = Integer.MAX_VALUE;
//    private float exchange_rate;  // according to abs_unit  // this is price

    public Currency(String curr_name, float exchange_rate){

        super(curr_name, null, exchange_rate, Integer.MAX_VALUE, Integer.MAX_VALUE, (float) 0.5);
        legal_in = new HashSet<>();

    }


    private float valueIn(Currency other) {
        return 0; //TODO
    }

    public ObservableList<String> getValidCountries() {
        ObservableList<String> validCountries = FXCollections.observableArrayList();
        for(Country c : legal_in){
            validCountries.add(c.getName());
        }
        return validCountries;
    }

    @Override
    public void update(float value) {
//        if (value >= 0) {
//            price *= (1.02 + World.random.nextFloat(0, (float)0.1));
//        } else {
//            price *= (0.98 - World.random.nextFloat(0, (float)0.1));
//        }
//        price_history.add(new Asset.Record(World.time, price));
    }

    public void addLegalCountry(Country c){
        legal_in.add(c);
    }
}

