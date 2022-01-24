package com.example.marketsimui.simulation;


import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

/**
 *
 * To model price changes more accurately, I added a factor that simulates the behaviour af a given company:
 * CEO makes good, prospective decision --> investors are more likely to support the idea and buy shares
 * CEO smokes dope on some on-air podcast --> Investors see this as a threat and want to sell shares --> share price drops
 */
public class Company extends Thread{
    Random rand = new Random();
    private final String companyName;
    private Country country;
    private Market market;
    private CompanyShares shares;
    private int n_shares;           // describes on how many shares the company is divided (not close to the real world)
    private int n_shares_sold;   // fixme -- take care of that value sos that its not greater than released
    private int n_shares_released;

    // todo -- implement all of these::
//    private float profit;   // profit is: revenue - expenses
//    private float revenue;
//
//    private Date ipo_date;
//    private float total_value;
//    private float ipo_share_value;
//    private float opening_price;
//
//    private float minimal_price;  // minimal price in history
//    private float maximal_price;   // maximal price in history
//
//    private int trading_volume; // how many times the equity was bought, sold
//    private ArrayList<Float> total_sales;
//
//    private float currentBehaviour;
//    private float currentRisk;

    public Company(String name, Country country) {
        this.companyName = name;
        this.country = country;
    }
    public Company(String name, Market market, float totalValue, float percentIssued){
        this.companyName = name;
        this.market = market;
        this.country = ((StockMarket)market).getCountry();
        n_shares = World.random.nextInt((int) totalValue / 1000) + 500;
        n_shares_released = (int) (n_shares * percentIssued);
        n_shares_sold = 0;
        float share_price = totalValue / n_shares;

        shares = new CompanyShares(name, market, share_price, n_shares_released, n_shares_released, (float)0.5);
        market.addAsset(shares);
        World.addAsset(shares);
    }

    public void setIndexes(List<Index> indexes) {

    }

    public void issueShare(){

        shares.issueOne();
    }

    public void generateRevenue() {
        // I could have modeled it in more realistic way, but here I assume that given operation
        // just increases the value of shares
        shares.setPrice(shares.getPrice() + Math.max((float)World.random.nextGaussian(shares.getPrice()/10000, 100), 100));
    }
//        //TODO - give away some part of a company, create share
//        // and add it to the given market
//        //to solve:
//        // how share release influences the stock price??
//
//        // put share into the given stock market
//        // company is the initial owner of a share (as long as some investor buys the share)
//        Share new_share = new Share(this);
//        market.addAsset(new_share);


//    }

//    private void evaluateBehaviour(){
//        this.currentBehaviour = rand.nextFloat();
//    }
//
//
//    private void evaluateRisk(){
//        this.currentRisk = rand.nextFloat();
//    }


    public Market getMarket() {
        return market;
    }

    public CompanyShares getShares() {return shares;}
    public void setMarket(Market market) {
        this.market = market;
    }

    public String getCompanyName() {
        return companyName;
    }

    /**
     * method to assign all the necessary values at random
     */
    public void initialAction(Market m) {
        Random rand = new Random();
        float share_price = max(Math.abs((float) rand.nextGaussian(3000, 2000)),(float)0.01);
        n_shares = Math.abs((int) rand.nextGaussian(1000 + (int) share_price, 1000 ));
        n_shares_released = rand.nextInt(n_shares);
        n_shares_sold = 0;
        shares = new CompanyShares(companyName, m, share_price, n_shares_released,n_shares_released, (float)0.5 );

    }

    @Override
    public void run() {
        // todo - when I switch to multi-thread version
        while (!Thread.interrupted()) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            generateRevenue();
            if(World.random.nextFloat() < 0.05){
                issueShare();
            }
        }
    }
}
