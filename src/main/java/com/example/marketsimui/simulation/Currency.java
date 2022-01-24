package com.example.marketsimui.simulation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.Set;

public class Currency extends Asset {
    private String curr_name;
    private Set<Country> legal_in;
    private final int n_on_market = Integer.MAX_VALUE;
//    private float exchange_rate;  // according to abs_unit  // this is price

    public Currency(String curr_name, float exchange_rate){

        super(curr_name, null, exchange_rate, Integer.MAX_VALUE, Integer.MAX_VALUE, (float) 0.5);
        legal_in = new HashSet<>();

    }

    @Override
    public synchronized void update(float number) {
        setAvailable_to_buy(getAvailable_to_buy() - (int) number) ;
        if (number >= 0) {
            setPrice((float) (getPrice() * (1.02 + World.random.nextFloat(0, (float)0.1))));
        } else {
            setPrice((float) (getPrice() * (0.97 - World.random.nextFloat(0, (float)0.1))));
        }
        //System.out.println("adding record");
        addPriceRecord(World.time, getPrice());
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


    public void addLegalCountry(Country c){
        legal_in.add(c);
    }
}

