package com.example.marketsimui;

public class Country {
    private String name;
    private Currency currency; //Fixme

    public Country(String name, Currency currency){
        this.name = name;
        this.currency = currency;
        currency.addLegalCountry(this);
    }

    public String getName() {
        return name;
    }
}
