package com.example.marketsimui.simulation;

public class Investor extends Trader {
    private String f_name;
    private String l_name;
    private int trading_id;  // TODO - create thread-safe unique id


    public Investor(float b, String f, String l, int i) {
        super(b);
        f_name = f;
        l_name = l;
        trading_id = i;
    }


}
