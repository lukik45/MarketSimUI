package com.example.marketsimui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * There is no need to create separate classes for stock, commodity and currency
 * markets, since they all behave the same
 */
public class Market {
    private String name;
    private Country country;
    private String type;
    private List<Asset> assets;

    public Market(String name, String type, Country country) {
        assets = new ArrayList<>();
        this.name = name;
        this.type = type;
        this.country = country;
    }

    /**
     * Update all assets in a market
     */
    private void update(){
        // todo
    }

    public void addAsset(Asset a) {
        assets.add(a);
    }

//    public ArrayList<Asset> listAssets() {
//        return new ArrayList<Asset>(assets);
//    }

    public String getName() {
        return name;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    /**
     * Used to print market's assets to stdout
     */
    public void printAssets(){
        System.out.println("Assets in " + this.name);
        for( Asset a : assets){
            a.printInfo();
        }
    }
}

