package com.example.marketsimui.simulation;

import java.util.Set;

public class InvestmentFund extends Trader {
    private Investor manager;
    private Set<Investor> members;


    public InvestmentFund(float b) {
        super(b);
    }
}
