package W14B_G4_Assignment1.model;

import java.util.*;

public class Calculator {

    private List<Double> rateList = new ArrayList<>();
    private List<String> transactionList = new ArrayList<>();

    public Calculator(String startDate, String endDate, String currencyFrom, String currencyTo){
        JsonReader dataReader = new JsonReader("src/main/java/W14B_G4_Assignment1/resources/ExchangeRates.json");

        String currentDate = startDate;
        while (true){
            HashMap< String, HashMap<String, Double> > currentMap = dataReader.getDateMap(currentDate);
            rateList.add(currentMap.get(currencyFrom).get(currencyTo));
            transactionList.add(String.format("%-15s%8.3f", currentDate, currentMap.get(currencyFrom).get(currencyTo)));

            if (currentDate.equals(endDate)){
                break;
            }

            currentDate = increaseDate(currentDate);
        }
    }

    public String increaseDate(String date){
        String[] splitResult = date.split("-");
        int dateInt = Integer.parseInt(splitResult[2]) + 1;
        String dateString = String.valueOf(dateInt);
        return String.format(splitResult[0] + "-" + splitResult[1] + "-" + "%2s", dateString).replace(' ', '0');
    }

    public double average(){
        double sum = 0.0;
        for (int i = 0;i < this.rateList.size();i++){
            sum += this.rateList.get(i);
        }

        return Double.parseDouble(String.format("%.2f", sum / this.rateList.size()));
    }

    public double median(){
        Collections.sort(this.rateList);

        if (this.rateList.size() % 2 == 1){
            return this.rateList.get(this.rateList.size() / 2);
        }else{
            double result = (this.rateList.get(this.rateList.size() / 2) + this.rateList.get(this.rateList.size() / 2 - 1)) / 2;
            return Double.parseDouble(String.format("%.2f", result));
        }
    }

    public double maximum(){
        double max = 0.0;
        for (int i = 0;i < this.rateList.size();i++){
            if (this.rateList.get(i) > max){
                max = this.rateList.get(i);
            }
        }

        return max;
    }

    public double minimum(){
        double min = this.rateList.get(0);
        for (int i = 0;i < this.rateList.size();i++){
            if (this.rateList.get(i) < min){
                min = this.rateList.get(i);
            }
        }

        return min;
    }

    public double stdDeviation(){
        double avr = this.average();
        double sd = 0.0;
        for (int i = 0;i < this.rateList.size();i++){
            sd += Math.pow(this.rateList.get(i) - avr, 2);
        }

        return Double.parseDouble(String.format("%.2f", Math.sqrt(sd/this.rateList.size())));
    }

    public List<String> getTransactions(){
        return this.transactionList;
    }

}
