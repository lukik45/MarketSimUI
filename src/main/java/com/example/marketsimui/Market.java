package com.example.marketsimui;

import java.util.*;


/**
 * There is no need to create separate classes for stock, commodity and currency
 * markets, since they all behave the same
 */
public class Market {
    private String name;
    private Country country;
    private String type;
    private HashMap<String, Asset> assets;

    public Market(String name, String type, Country country) {
        assets = new HashMap<>();
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
        assets.put(a.getName(),a);
    }

//    public ArrayList<Asset> listAssets() {
//        return new ArrayList<Asset>(assets);
//    }

    public String getType() {
        return type;
    }

    public Country getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Asset> getAssets() {
        return assets;
    }

    /**
     * Used to print market's assets to stdout
     */
    public void printAssets(){
        System.out.println("Assets in " + this.name);
        for( Asset a : assets.values()){
            a.printInfo();
        }
    }
}

