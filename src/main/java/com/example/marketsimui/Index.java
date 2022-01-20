package com.example.marketsimui;


import javafx.collections.ObservableList;

/**
 * Index is just an aggregator, it does not hold any specific information
 * A company can belong to many indexes
 * We're not interested in any specific company
 */
public class Index {
    Market market;
    ObservableList<CompanyShares> shares;

    public Index(Market m){
        market = m;
    }

    /**
     * Sum up all shares that companies in the index issue to the market
     * @return
     */
    public float getTotalValue(){
        float totalValue = 0;
        for(CompanyShares s : shares){
            totalValue += s.getTotalValueOnMarket();
        }
        return totalValue;
    }
}
