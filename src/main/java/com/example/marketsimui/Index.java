package com.example.marketsimui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Index is just an aggregator, it does not hold any specific information
 * A company can belong to many indexes
 * We're not interested in any specific company
 */
public class Index {
    String name;
    Market market;
    ObservableList<CompanyShares> shares;

    public Index(String name, Market m){
        market = m;
        shares = FXCollections.observableArrayList();
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

    public void addCompanyShares(CompanyShares s){
        if (shares.contains(s)){
            return;
        } else {
            shares.add(s);
        }
    }

    public String getName() {
        return name;
    }
}
