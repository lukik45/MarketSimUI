package com.example.marketsimui.simulation;

import com.example.marketsimui.simulation.Asset;

import java.util.*;


/**
 * There is no need to create separate classes for stock, commodity and currency
 * markets, since they all behave the same
 */
public class Market {
    private String name;

    private String type;
    private HashMap<String, Asset> assets;

    public Market(String name, String type) {
        assets = new HashMap<>();
        this.name = name;
        this.type = type;

    }

    public void addAsset(Asset a) {
        assets.put(a.getName(),a);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getType() {
        return type;
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

