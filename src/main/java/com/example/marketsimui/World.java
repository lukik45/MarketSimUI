package com.example.marketsimui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.max;
import static java.lang.Thread.sleep;

/**
 * World
 * The container of all the data in  the simulation
 *
 */
public class World extends Thread {
    // settings
    private static int timeout = 180;
    private static float abs_unit = 1;
    public static int time = 0;
    public static Random random;


    // for synchronization
    private static volatile boolean paused;
    private static volatile boolean finished;
    private static Currency currentCurrency;

    // data containers
    private static HashMap<String, Market> markets;
    private static HashMap<String, StockMarket> markets_by_countries;

    private static HashMap<String, Country> countries;
    private static HashMap<String, Currency> currencies;
    private static HashMap<String, Company> companies;
    private static HashMap<String, Asset> allAssets;
    private Set<Trader> traders;

    private static class IndexNameGenerator{
        private final static AtomicInteger nextId = new AtomicInteger();

        private static int getId() {
            return nextId.incrementAndGet();
        }
        public static String getUniqueName() {
            return "Index" + String.valueOf(getId());
        }
    }

    public static class IdGenerator {
        private final static AtomicInteger nextId = new AtomicInteger();

        public static int getId(){
            return nextId.incrementAndGet();
        }
    }


    public World() throws IOException {
        markets = new HashMap<String, Market>();
        markets_by_countries = new HashMap<String, StockMarket>();
        countries = new HashMap<String, Country>();
        currencies = new HashMap<String, Currency>();
        companies = new HashMap<String, Company>();
        traders = new HashSet<>();
        allAssets = new HashMap<>();
        random = new Random();



        loadWorld();
    }

    private void loadWorld() throws IOException {
        String[] line;
        String name;
        String cur;

        // create currency market
        Market currencyMarket = new Market("Currency Market", "currency");
        markets.put("Currency Market",currencyMarket);

        // create commodity market
        Market commodityMarket = new Market("Commodity Market", "commodity");
        markets.put("Commodity Market",commodityMarket);

        // create objects of commodities
        try(Scanner in = new Scanner(new File("./sample_data/commodities.txt"))) {
            while (in.hasNextLine()) {
                line = in.nextLine().split(";");
                String comm_name = line[0];
                float price = Float.parseFloat(line[1]);
                Currency newCommodity = new Currency(comm_name, price);
                allAssets.put(comm_name, newCommodity);
                newCommodity.setMarket(commodityMarket);
                commodityMarket.addAsset(newCommodity);
            }
        }


        // create objects of currencies
        System.out.println(System.getProperty("user.dir"));
        try(Scanner in = new Scanner(new File("./sample_data/currencies.txt"))) {
            while (in.hasNextLine()) {
                line = in.nextLine().split(";");
                String cur_name = line[0];
                float exch_rate = Float.parseFloat(line[1]);
                Currency newCurrency = new Currency(cur_name, exch_rate);
                currencies.put(cur_name, newCurrency);
                allAssets.put(cur_name, newCurrency);
                newCurrency.setMarket(currencyMarket);
                currencyMarket.addAsset(newCurrency);
            }
        }
        //  get random currency
        currentCurrency = (Currency) currencies.values().toArray()[0];

        // create objects of countries
        try(Scanner in = new Scanner(new File("./sample_data/countries.txt"))) {
            while (in.hasNextLine()) {
                line = in.nextLine().split(";");
                name = line[0];
                cur = line[1];
                countries.put(name, new Country(name, currencies.get(cur)));
            }
        }

        // create stock markets
        try(Scanner in = new Scanner(new File("./sample_data/stock_markets.txt"))) {
            while(in.hasNextLine()) {
                line = in.nextLine().split(";");
                name = line[0];
                String country_name = line[1];
                StockMarket new_market = new StockMarket(name, "stock", countries.get(country_name));
                markets.put(name, new_market);
                markets_by_countries.put(country_name, new_market);

                // for each market, create a few indexes
                for (int i = 0; i < random.nextInt(4) + 2; i++) {
                    Index newIndex = new Index(IndexNameGenerator.getUniqueName(), new_market);
                    new_market.addIndex(newIndex);
                }

            }
        }


        //  create companies, add their shares to markets
        try(Scanner in = new Scanner(new File("./sample_data/companies.txt"))) {
            while(in.hasNextLine()) {
                line = in.nextLine().split(";");
                try {
                    name = line[0];
                    String country_name = line[1];
                    Company new_company = new Company(name, countries.get(country_name));
                    companies.put(name , new_company);
                    // add a company shares to a proper market
                    StockMarket m = markets_by_countries.get(country_name);
                    new_company.setMarket(m);
                    new_company.initialAction(m);
                    m.addAsset(new_company.getShares());
                    allAssets.put(new_company.getName(), new_company.getShares());

                    // add the company shares to one or more indexes
                    for (int i = 0; i < random.nextInt(3)+1; i++) {
                        Index index = m.getIndexes().get(random.nextInt(m.getIndexes().size()));
                        index.addCompanyShares(new_company.getShares());
                    }

                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println(ex.getMessage() + "\n" +
                            Arrays.toString(line));
                    System.exit(-5);
                }
            }
        }

        // randomly generate investors
        List<String> first_names = new ArrayList<>();
        List<String> last_names = new ArrayList<>();
        String flname;
        try(Scanner in = new Scanner(new File("./sample_data/first_names.txt"))) {
            while(in.hasNextLine()) {
                flname = in.nextLine();
                first_names.add(flname);
            }
        }
        try(Scanner in = new Scanner(new File("./sample_data/surnames.txt"))) {
            while(in.hasNextLine()) {
                flname = in.nextLine();
                last_names.add(flname);
            }
        }
        Random rand = new Random();
        float budget;
        String fname;
        String lname;

        for (int i = 0; i < Math.pow(currencies.size(), 2)/2; i++) {
            budget = max((float)rand.nextGaussian(2_000_000, 1_000_000), (float) 15);
            fname = first_names.get(rand.nextInt(first_names.size()));
            lname = last_names.get(rand.nextInt(last_names.size()));
            traders.add(new Investor(budget, fname, lname, IdGenerator.getId()));
        }

        for(Trader t : traders){
            for (int i = 0; i < 10; i++) {
                t.goForShopping(markets);
            }
        }






    }

    /**
     * Simulate transactions made by investors
     */
    public void simulate1sec() {
        System.out.println("time: " + time);
        for (Trader t: traders) {
            t.sellSomeStuff();
            t.goForShopping(markets);
        }
    }


    public void runWorld() {
        while (time < timeout && !Thread.interrupted()) {
            if (paused){
                while(paused) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
            if (finished){
                break;
            }
            simulate1sec();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
            time +=1;
        }
    }
    @Override
    public void run() {
        runWorld();
    }




    // actions to be performed by the user
    public synchronized void pause() {
        paused = true;
    }
    public synchronized void resume_() {
        paused = false;
    }
    public synchronized void finish() {
        finished = true;
    }


    public static void addStockMarket(String name, String countryName, String type ) {
        StockMarket newMarket = new StockMarket(name, type, countries.get(countryName)); // fixme- country validation
        markets.put(name, newMarket);
        if (Objects.equals(type, "stock")) {
            markets_by_countries.put(countryName, newMarket);
        }
    }
    public static void addCountry(String name, String currencyName) {
        Country newCountry = new Country(name, currencies.get(currencyName));
        countries.put(name ,newCountry);
    }

    public static void addCompany(String name, String countryName) {
        Company newCompany = new Company(name, countries.get(countryName));
        companies.put(name, newCompany);
        Market m = markets_by_countries.get(countryName);
        newCompany.setMarket(m);
        newCompany.initialAction(m);
        m.addAsset(newCompany.getShares());

    }

    public static void addCurrency(String name, float price){
        Currency c = new Currency(name, price);
        currencies.put(name, c);
        markets.get("Currency Market").addAsset(c);
        c.setMarket(markets.get("Currency Market"));
    }

    public static void addCommodity(String name, float price){
        Commodity c = new Commodity(name, price);

        markets.get("Commodity Market").addAsset(c);
        c.setMarket(markets.get("Commodity Market"));
    }

    public boolean isPaused() {
        return paused;
    }

    public static HashMap<String, Country> getCountries() {
        return countries;
    }


// getters setters


    public static Currency getCurrentCurrency() {
        return currentCurrency;
    }

    public static void setCurrentCurrency(String currentCurrencyId) {
        World.currentCurrency = currencies.get(currentCurrencyId);
    }

    public static HashMap<String, Asset> getAllAssets() {
        return allAssets;
    }
    public static HashMap<String, Currency> getCurrencies() {
        return currencies;
    }

    public static HashMap<String, Asset> getCommodities() {
        return markets.get("Commodity Market").getAssets();
    }


    public static float exchangeForCurrentCurrency(float value){
        if (currentCurrency == null)
            return value;
        return value / currentCurrency.getPrice();
    }
    public static float getCurrExchRate() {
        return currentCurrency.price;
    }

    public static ObservableList<String> getCountryNames() {
        ObservableList<String> countryNames = FXCollections.observableArrayList(
                countries.keySet()
        );
        return countryNames;
    }


}

