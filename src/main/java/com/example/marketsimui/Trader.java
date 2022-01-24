package com.example.marketsimui;

import java.util.*;

public class Trader extends Thread{
    private HashMap<String, Property> properties; // can't have part of an asset
    private float budget;
    private Random mind;

    public Trader (float b){
        budget = b;
        properties = new HashMap<>();
        mind = new Random();
    }


    @Override
    public void run() {
        System.out.println("trader is running");
        while (!Thread.interrupted()){
            sellSomeStuff();
            goForShopping();
            try {
                sleep(mind.nextInt(700) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * For now, trader is interested in buying some asset a.
     * He has to check if he can afford it, and if he can, then how many
     *
     * @param asset
     *
     * To ensure safety of the operation, I need to lock the asset so that other traders cannot
     * buy the same asset concurrently
     */
    private void buy(Asset asset) {
        try {
            if (!asset.tryLockForBuy())
                return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        float price = asset.getPrice();
        int available = asset.getAvailable_to_buy();
        // todo - manipulate the price of an asset
        // todo - currencies and commodities can be divided
        // not necessary to buy whole units
        // fixme - all operations must be thread-safe
        float price_ratio = budget/price;
        if (price_ratio >= 2 && available >= 1) { // "If you can't buy it twice, you can't afford it" ~ Jay-Z
            float part_of_budget_to_spend = (float) Math.abs(Math.min(mind.nextGaussian(0,0.2), 0.5));
            float operational_budget = budget * part_of_budget_to_spend;
            int entities_to_buy = Math.min(available, (int)(operational_budget/price));
            // finalize transaction
            if (properties.containsKey(asset.getName())) {
                Property property = properties.get(asset.getName());
                property.update(entities_to_buy);

            } else { // if no units of a given asset in the wallet
                properties.put(asset.getName(), new Property(asset, (float)entities_to_buy));
                asset.update((float)entities_to_buy);
            }
            budget -= entities_to_buy*asset.getPrice();
        }
        asset.unlockAfterBuy();
    }

    /**
     * Some asset is drawn and chosen to be sold to the market
     * @param property
     *
     * Selling procedure is threadsafe from traders'  point
     * the synchronization takes place in the Asset.update() method
     */
    private void sell(Property property){

        if (mind.nextFloat() < 0.4) { // sell all you've got
            float entities_to_sell = property.amount;
            property.subject.update(-entities_to_sell);
            properties.remove(property.subject.getName());
            budget += entities_to_sell*property.subject.getPrice();
        } else {
            int entities_to_sell = mind.nextInt((int) property.amount);
            property.update(-entities_to_sell);
            budget += entities_to_sell*property.subject.getPrice();
        }

    }
    private void increaseBudget(){
        //TODO - there is some probability to increase the
        // budget at a random moment in time
    }



    //TODO - I will state later,who is the observer, and how to
    // implement the communication between threads (I assume it will
    // be easier to do when I get familiar with threads


    /**
     * Trader trades on markets, but for the simulation it does not matter if
     * the trader chooses asset to buy from  markets or from general list of all
     * assets, so for simplicity ...
     *
     */
    public void goForShopping() {
        do {
            if (!World.reportTransaction())  // cant buy wnything if the number of transactions per second's been reached
                return;
            Asset assetToBuy = World.getAllAssets().get(mind.nextInt(World.getAllAssets().size()));
            buy(assetToBuy);
        } while (mind.nextDouble() < 0.7);
    }

    public void sellSomeStuff() {
        Object[] listedKeys = properties.keySet().toArray();
        while (mind.nextFloat() < 0.75) {
            if(!World.reportTransaction())
                return;
            if (properties.size() > 0) {
                try {
                    String key = (String) listedKeys[mind.nextInt(listedKeys.length)];

                    try {
                        this.sell(properties.get(key));
                    } catch (NullPointerException ex) {
                        continue;
                    }
                } catch (IllegalArgumentException ex) {
                    break;
                }
            } else {
                break;
            }
        }
    }

//    private void buyAt(Market next_market) {
//        // choose an asset to buy
//        List<Asset> promising_assets = new ArrayList<>(next_market.getAssets().values());
//        if(promising_assets.size() == 0)
//            return;
//        try {
//            // buy some
//            while (mind.nextFloat() < 0.6) {
//                this.buy(promising_assets.get(mind.nextInt(promising_assets.size())));
//            }
//        } catch (IllegalArgumentException ex) {
//            ex.printStackTrace();
//            System.out.println(ex.getMessage() + "\n" +
//                    promising_assets.toString() + "\n" + next_market.getName());
//            System.exit(-9);
//        }
//    }


    /**
     * This is a utility class to manage traders' properties
     */
    protected class Property {
        private Asset subject;
        private float amount;

        protected Property(Asset s, float a ){
            subject = s;
            amount = a;
        }

        /**
         * update asset
         */
        protected void update(float value){
            amount += value;
            subject.update(value);
        }
    }
}

