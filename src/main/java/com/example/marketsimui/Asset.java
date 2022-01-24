package com.example.marketsimui;

import javafx.scene.chart.XYChart;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * the instance of a class that implements Asset is not "single gold bar"
 *                                                   but gold as a whole
 * then, traders can buy some amount of an asset(if is fully divisible)
 *      or a specified part of an asset (if it is not divisible)
 */
public abstract class Asset {
    private Semaphore buySemaphore;
    private String name;
    private Market market;
    private String market_name;
    private float price;
    private int n_on_market;
    private int available_to_buy;
    private float investment_risk; //todo TOTO
    private List<Record> price_history;       // charts will be built based on this field
    private float tendency = 0;


    public boolean tryLockForBuy() throws InterruptedException {
        return buySemaphore.tryAcquire();
    }
    public void unlockAfterBuy() {
        buySemaphore.release();
    }

    private class Record {
        int time;
        float price;

        private Record(int t, float p) {
            time = t;
            price = p;
        }
        public int getTime() {
            return time;
        }
        public synchronized float getPrice() {
            return price;
        }

    }

    public Asset(){}

    public Asset(String name, Market market, float price, int n_on_market, int available_to_buy, float investment_risk) {
        this.name = name;
        this.market = market;
        if (market != null)
            this.market_name = market.getName();
        this.price = price;
        this.n_on_market = n_on_market;
        this.available_to_buy = available_to_buy;
        this.investment_risk = investment_risk;
        this.price_history = new ArrayList<>();
        price_history.add(new Record(0, price));
        buySemaphore = new Semaphore(1);

    }

    public void updateRelativePrice(){}

    public void addPriceRecord(int time, float price){
        price_history.add(new Record(time, price));
    }

    public Market getMarket() {
        return market;
    }
    public void setMarket(Market m) {
        market = m;
        market_name = m.getName();
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    /**
     * Used by GUI to show price in current currency
     */
    public float getRelativePrice() {
        return World.exchangeForCurrentCurrency(getPrice());
    }

    public String getMarket_name() {
        return market_name;
    }

    public List<Record> getPrice_history() {
        return price_history;
    }

    public float getTendency() {
        return tendency;
    }


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


    public XYChart.Series<String, Float> getChartCoords(){
        System.out.println("hello");
        System.out.println(World.getCurrentCurrency().getName());
        XYChart.Series<String, Float> series = new XYChart.Series<>();
        Record record;
        Record next_record;
        int pointer = 0;
        int pos_in_list = 1;
        int last_rec;
        int next_rec;
        float last_val;
        float next_val;

        record = price_history.get(0);
        last_val = World.exchangeForCurrentCurrency(record.price);
        last_rec = record.time;

        try {
            next_record = price_history.get(1);
            next_val = World.exchangeForCurrentCurrency(next_record.price);
            next_rec = next_record.time;
        } catch (IndexOutOfBoundsException ex) {
            series.getData().add((new XYChart.Data<>(String.valueOf(pointer), last_val)));
            return series;
        }

        while (pointer <= World.time){
            while (pointer < next_rec){
                series.getData().add(new XYChart.Data<>(String.valueOf(pointer), last_val ));
                pointer +=1;
            } // pointer = next_val
            last_rec = next_rec;
            last_val = next_val;
            pos_in_list+=1;
            if (price_history.size() > pos_in_list) {// if there is next record
                next_record = price_history.get(pos_in_list);
                next_rec = next_record.time;
                next_val = World.exchangeForCurrentCurrency(next_record.price);
            } else {
                next_rec = World.time +1;
            }
        }
        return series;
    }


    public void printInfo() {
        //System.out.println(name + "price: " + price + "av. to buy: " + available_to_buy);
        System.out.format("%-20s | price: %-8.2f | av. buy: %-7d |", name, price, available_to_buy);
    }

    @Override
    public String toString(){
        return name;
    }
}
