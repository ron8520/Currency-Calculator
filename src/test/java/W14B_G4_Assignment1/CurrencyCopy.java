package W14B_G4_Assignment1.model;

import java.util.*;

public class CurrencyCopy {

    static int currentCurrencyCount = 0;
    static List<CurrencyCopy> currencies = new ArrayList<>();
    static List<Integer> popularCurrencies = new ArrayList<>();
    static String date = "";

    private int currencyID;
    private String name;
    private String symbol;
    private List<Double> currencyRate;
    private boolean isPopular;

    /**
     * Create an new currency
     * @param currencyName the name of the new currency
     * @param relateCurrencyID the currencyID of an old currency used for automatic calculation
     * @param exchangeRate the exchange rate of the new currency to an old currency upon
     */
    public CurrencyCopy(String currencyName,String symbol, int relateCurrencyID, double exchangeRate){
        this.name = currencyName;
        this.symbol = symbol;
        this.currencyID = increaseCurrentCurrencyCount();
        this.currencyRate = new ArrayList<>();
        this.expandCurrencyList(relateCurrencyID,exchangeRate);
        this.isPopular = false;
        String filePath = "src/test/java/W14B_G4_Assignment1/JsonTest.json";
        JsonWriter writer = new JsonWriter(filePath);
        HashMap<String,Double> rate = new HashMap<>();
    }

    public CurrencyCopy(String currencyName, String symbol){
        this.symbol = symbol;
        this.name = currencyName;
        this.currencyID = increaseCurrentCurrencyCount();
        this.currencyRate = new ArrayList<>();
        this.isPopular = false;
        currencies.add(this);
    }

    /**
     * Automatic calculate and Create the exchange rate list for the new currency, and expand the exchange rate list of all other currencies
     * @param relateCurrencyID the currencyID of an old currency used for calculation
     * @param exchangeRate the exchange rate of the new currency to an old currency
     */
    public void expandCurrencyList(int relateCurrencyID, double exchangeRate){
        if(currentCurrencyCount!=1){
            List<Double> relateCurrencyRate = currencies.get(relateCurrencyID).getCurrencyRateList();
            for(int i = 0; i < relateCurrencyRate.size(); i++){         //create convert table for the new list
                Double resultNumber = Double.valueOf(exchangeRate*relateCurrencyRate.get(i));
                this.currencyRate.add(resultNumber);
            }
            for(int i = 0; i < this.currencyRate.size(); i++){          //add convert rate of the new currency to other currencies
                Double resultNumber = Double.valueOf(1/this.currencyRate.get(i));
                currencies.get(i).currencyRate.add(resultNumber);
            }
        }
        currencies.add(this);
        this.currencyRate.add(Double.valueOf(1));
    }

    /**
     * Get the currency exchange rate to another currency
     * @param relateCurrencyID the currencyID of another currency
     * @return the exchange rate
     */
    public Double getCurrencyRate(int relateCurrencyID){
        return this.currencyRate.get(relateCurrencyID);
    }

    /**
     * Return the currency exchange rate list of the currency
     * @return list of currency exchange rate
     */
    public List<Double> getCurrencyRateList(){
        return this.currencyRate;
    }
    public int getCurrencyID(){return this.currencyID;}

    public void setExchangeRateFromJSON(String currencyName, Double rate){
        for(int i = 0; i < currencies.size(); i ++){

        }
    }

    /**
     * This is a function for initialize the currencies, it will set the currency to popular when there are less than 4 popular currencies
     * @return true if success, false if fail
     */
    public boolean setPopular(){
        if (popularCurrencies.size() < 4){
            this.isPopular = true;
            popularCurrencies.add(this.currencyID);
            return true;
        }
        return false;
    }


    public static String getName(int ID){
        return getCurrency(ID).name;
    }
    public static String getSymbol(int ID){
        return getCurrency(ID).symbol;
    }

    /**
     * Replace a popular currency
     * @param setPopularID the currency you want to set to popular
     * @param replaceID the popular currency you want to replace
     * @return
     */
    public static boolean setPopular(int setPopularID, int replaceID){
        if (popularCurrencies.size() == 4){
            popularCurrencies.remove(replaceID);
            getCurrency(replaceID).isPopular = false;
            popularCurrencies.add(setPopularID);
            getCurrency(setPopularID).isPopular = false;
            return true;
        }
        return false;
    }

    public static CurrencyCopy getCurrency(int currencyID){
        return currencies.get(currencyID);
    }
    public static CurrencyCopy getCurrency(String currencyName){
        for(int i = 0; i < currencies.size();i++){
            if(getCurrency(i).name == currencyName){
                return getCurrency(i);
            }
        }
        return null;
    }
    public static int getCurrencyCount(){return currentCurrencyCount;}

    /**
     * Return current number of total currencies, and increase the number by 1
     * @return the number of total currencies before increase
     */
    public static int increaseCurrentCurrencyCount() {
        int returnNumber = currentCurrencyCount;
        currentCurrencyCount ++;
        return returnNumber;
    }

    public static void setupFromJSON(String filename, String datename){
        date = datename;
        currencies.clear();
        popularCurrencies.clear();
        currentCurrencyCount = 0;
        JsonReader dataReader = new JsonReader(filename);
        HashMap< String, HashMap<String, Double>> data = dataReader.getDateMap(datename);
        List<CurrencyCopy> curList = new ArrayList<>();
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            new CurrencyCopy((String)pair.getKey(),dataReader.getSymbol(datename,(String)pair.getKey()));
        }
        for(int i = 0; i < currencies.size(); i++){
            HashMap<String, Double> rate = data.get(currencies.get(i).name);
            for(int j = 0; j < currencies.size(); j++){
                if(i != j){
                    currencies.get(i).currencyRate.add(Double.valueOf(rate.get(currencies.get(j).name)));
                }else{
                    currencies.get(i).currencyRate.add(Double.valueOf(1));
                }

            }
            currencies.get(i).setPopular();
        }

    }

    public static List<Integer> getPopularCurrencies(){
        return popularCurrencies;
    }

    public static String getStatus(int fromID, int toID, String date){
        String status = " ";
        String curFrom = getName(fromID);
        String curTo = getName(toID);
        String filePath = "src/main/java/W14B_G4_Assignment1/resources/ExchangeRates.json";
        Integer day = Integer.parseInt(date.substring(8,10));
        day -= 1;
        String yesterday = date.substring(0,8);
        if(day<10){
            yesterday = yesterday + "0" + day.toString();
        }else{
            yesterday = yesterday + day.toString();
        }
        JsonReader reader = new JsonReader(filePath);
        if(reader.getDateMap(yesterday).size()>1){
            HashMap< String, HashMap<String, Double> > yesterdayMap = reader.getDateMap(yesterday);
            HashMap< String, HashMap<String, Double> > todayMap = reader.getDateMap(date);
            Double yesterdayRate = yesterdayMap.get(curFrom).get(curTo);
            Double todayRate = todayMap.get(curFrom).get(curTo);
            if(yesterdayRate != null && todayRate != null){
                if(yesterdayRate > todayRate){
                    status = "\u2193";
                }else if(yesterdayRate < todayRate){
                    status = "\u2191";
                }

            }
        }
        return status;

    }

    public static void changeExchangeRate(int fromID, int toID, Double newValue){
        currencies.get(fromID).currencyRate.set(toID, newValue);
        String filePath = "src/main/java/W14B_G4_Assignment1/resources/ExchangeRates.json";
        JsonWriter writer = new JsonWriter(filePath);
        writer.editCurrencyDetails(date,getName(fromID),getName(toID),newValue);
    }



}
