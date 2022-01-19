package com.example.marketsimui;


import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;

/**
 *
 * To model price changes more accurately, I added a factor that simulates the behaviour af a given company:
 * CEO makes good, prospective decision --> investors are more likely to support the idea and buy shares
 * CEO smokes dope on some on-air podcast --> Investors see this as a threat and want to sell shares --> share price drops
 */
public class Company implements Runnable{
    Random rand = new Random();
    private final String name;
    private Country country;
    private Market market;
    private CompanyShares shares;
    private int n_shares;           // describes on how many shares the company is divided (not close to the real world)
    private int n_shares_sold;
    private int n_shares_released;

//    private Date ipo_date;
//    private float total_value;
//    private float ipo_share_value;
//    private float opening_price;

//    private float minimal_price;
//    private float maximal_price;
//    private float percent_per_share;
//    private float percentage_given_away;
//    private float profit;
//    private float revenue;
//    private ArrayList<Float> trading_volume; // how many times the equity was bought, sold
//    private ArrayList<Float> total_sales;
//
//    private float currentBehaviour;
//    private float currentRisk;

    public Company(String name, Country country) {
        this.name = name;
        this.country = country;
    }

//    public void issueShare(Market market){
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

    /**
     * For now, we assign all the necessary values at random
     */
    public void initialAction(Market m) {
        Random rand = new Random();
        float share_price = max(Math.abs((float) rand.nextGaussian(3000, 2000)),(float)0.01);
        n_shares = Math.abs((int) rand.nextGaussian(1000 + (int) share_price, 1000 ));
        n_shares_released = rand.nextInt(n_shares);
        n_shares_sold = 0;
        shares = new CompanyShares(name, m, share_price, n_shares_released,n_shares_released, (float)0.5 );

    }

    @Override
    public void run() {
        // todo - when I switch to multithread version
        // sth like:
//        while (alive) {
//            generateRevenue();
//            issueMoreShares();
//            buyOutShares();
//            changePolicy();
//            sleep();
//        }
    }
}
