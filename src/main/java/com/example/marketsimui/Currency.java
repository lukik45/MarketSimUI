package com.example.marketsimui;

import java.util.HashMap;
import java.util.Set;
import java.util.zip.Inflater;

public class Currency extends Asset{
    private String curr_name;
    private Set<Country> legal_in;
    private final int n_on_market = Integer.MAX_VALUE;
//    private float exchange_rate;  // according to abs_unit  // this is price

    public Currency(String curr_name, float exchange_rate){

        super(curr_name, null, exchange_rate, Integer.MAX_VALUE, Integer.MAX_VALUE, (float) 0.5);


    }


    private float valueIn(Currency other) {
        return 0; //TODO
    }


    @Override
    public void update(float value) {

    }
}

