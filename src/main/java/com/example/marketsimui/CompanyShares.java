package com.example.marketsimui;


/**
 * number of released shares = Asset.n_on_market
 */
public class CompanyShares extends Asset {


    public CompanyShares(String name, Market market, float price, int n_on_market, int available_to_buy, float investment_risk) {
        super(name, market, price, n_on_market, available_to_buy, investment_risk);
    }

    @Override
    public void update(float value) {
        available_to_buy -= value;
        if (value >= 0) {
            price *= (1.02 + World.random.nextFloat(0, (float)0.1));
        } else {
            price *= (0.98 - World.random.nextFloat(0, (float)0.1));
        }
        price_history.add(new Asset.Record(World.time, price));
    }

    public float getTotalValueOnMarket(){
        return price * n_on_market;
    }

}

