package com.example.marketsimui;

import java.util.HashSet;

public class Commodity extends Asset{

    public Commodity(String curr_name, float exchange_rate){

        super(curr_name, null, exchange_rate, Integer.MAX_VALUE, Integer.MAX_VALUE, (float) 0.5);

    }

    @Override
    public void update(float value) {

    }
}
