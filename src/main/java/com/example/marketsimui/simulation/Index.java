package com.example.marketsimui.simulation;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        this.name = name;
        market = m;
        shares = FXCollections.observableArrayList();
    }

    private float round (float value){
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
    /**
     * Sum up all shares that companies in the index issue to the market
     * @return
     */
    public String getTotalValue(){
        float totalValue = 0;
        for (CompanyShares s : shares){
            totalValue += s.getTotalValueOnMarket();
        }

        return String.format("%,d", (int) totalValue);
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
