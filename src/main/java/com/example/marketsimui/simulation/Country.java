package com.example.marketsimui.simulation;

public class Country {
    private String name;
    private Currency currency; //Fixme
    private String currencyName;

    public Country(String name, Currency currency){
        this.name = name;
        this.currency = currency;
        currencyName = currency.getName();

        currency.addLegalCountry(this);
    }

    public String getName() {
        return name;
    }
    public String getCurrencyName() {
        return currencyName;
    }
}
