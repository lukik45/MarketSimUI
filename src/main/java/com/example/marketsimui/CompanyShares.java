package com.example.marketsimui;


/**
 * number of released shares = Asset.n_on_market
 */
public class CompanyShares extends Asset {


    public CompanyShares(String name, Market market, float price, int n_on_market, int available_to_buy, float investment_risk) {
        super(name, market, price, n_on_market, available_to_buy, investment_risk);
    }

    @Override
    public synchronized void update(float number) {
        setAvailable_to_buy(getAvailable_to_buy() - (int) number) ;
        if (number >= 0) {
            setPrice((float) (getPrice() * (1.02 + World.random.nextFloat(0, (float)0.1))));
        } else {
            setPrice((float) (getPrice() * (0.90 - World.random.nextFloat(0, (float)0.1))));
        }
        //System.out.println("adding record");
        addPriceRecord(World.time, getPrice());
    }



    public synchronized float getTotalValueOnMarket(){
        return getPrice() * getN_on_market();
    }

}

