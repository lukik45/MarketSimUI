package com.example.marketsimui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * the instance of a class that implements Asset is not "single gold bar"
 *                                                   but gold as a whole
 * then, traders can buy some amount of an asset(if is fully divisible)
 *      or a specified part of an asset (if it is not divisible)
 */
public abstract class Asset {
    protected String name;
    protected Market market;
    protected float price;
    protected int n_on_market;
    protected int available_to_buy;
    protected float investment_risk; //todo TOTO
    protected List<Record> price_history;       // charts will be built based on this field


    protected class Record {
        int time;
        float price;

        protected Record(int t, float p) {
            time = t;
            price = p;
        }
        public int getTime() {
            return time;
        }
        public float getPrice() {
            return price;
        }
    }

    public Asset(){}

    public Asset(String name, Market market, float price, int n_on_market, int available_to_buy, float investment_risk) {
        this.name = name;
        this.market = market;
        this.price = price;
        this.n_on_market = n_on_market;
        this.available_to_buy = available_to_buy;
        this.investment_risk = investment_risk;
        this.price_history = new ArrayList<>();
        price_history.add(new Record(0, price));
    }

    public float getPrice(){return price;}
    public abstract void update(float value);

    public int getN_on_market() {
        return n_on_market;
    }
    public void setN_on_market(int n_on_market) {
        this.n_on_market = n_on_market;
    }

    public String getName() {
        return name;
    }

    public float getInvestment_risk() {
        return investment_risk;
    }

    public int getAvailable_to_buy() {
        return available_to_buy;
    }

    public void setAvailable_to_buy(int available_to_buy) {
        this.available_to_buy = available_to_buy;
    }

    public void printInfo() {
        //System.out.println(name + "price: " + price + "av. to buy: " + available_to_buy);
        System.out.format("%-20s | price: %-8.2f | av. buy: %-7d |", name, price, available_to_buy);
    }

    /**
     * This method uses @price_history to gener
     */
    private void getDataForChart() {

    }
}
