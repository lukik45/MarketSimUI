package com.example.marketsimui.simulation;

import com.example.marketsimui.simulation.Asset;

public class Commodity extends Asset {

    public Commodity(String curr_name, float exchange_rate){

        super(curr_name, null, exchange_rate, Integer.MAX_VALUE, Integer.MAX_VALUE, (float) 0.5);

    }

    @Override
    public void update(float value) {
//        if (value >= 0) {
//            price *= (1.002 + World.random.nextFloat(0, (float)0.001));
//        } else {
//            price *= (0.0098 - World.random.nextFloat(0, (float)0.001));
//        }
//        price_history.add(new Asset.Record(World.time, price));
    }
}
