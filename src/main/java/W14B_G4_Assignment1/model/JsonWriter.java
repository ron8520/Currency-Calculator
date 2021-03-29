package W14B_G4_Assignment1.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import W14B_G4_Assignment1.model.*;

public class JsonWriter{
    private String targetFile;
    private JSONArray dateList;

    @SuppressWarnings("unchecked")
    public JsonWriter(String filename){
        this.targetFile = filename;

        JSONParser jsonParser = new JSONParser();

        try{
            FileReader reader = new FileReader(filename);
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            //dateList = (JSONArray) obj;
            this.dateList = (JSONArray) obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void writeCurrency(String date,String symbol, HashMap<String, Double> rateDetails, int currencyID, String currencyName){
        JSONObject currencyObj = createCurrencyDetails(date, symbol, rateDetails, currencyID, currencyName);
        int index = 0;
        JSONObject sub = new JSONObject();

        int i = 0;
        for (Object o:this.dateList){
            JSONObject obj = (JSONObject) o;
            JSONObject dateObj = (JSONObject) obj.get("Date");
            String dateStr = (String) dateObj.get("date");
            if (dateStr.equals(date)){
                index = i;
                for (Object k:currencyObj.keySet()){
                    String key = (String) k;
                    JSONObject details = (JSONObject) currencyObj.get(key);
                    dateObj.put(key, details);
                }
                sub.put("Date", dateObj);
                break;
            }
            i++;
        }

        this.dateList.remove(index);
        this.dateList.add(sub);

        try{
            FileWriter file = new FileWriter(this.targetFile);
            PrintWriter pw = new PrintWriter(file);
            pw.print("");
            pw.close();

            file = new FileWriter(this.targetFile);
            file.write(this.dateList.toJSONString());
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return;
    }

    @SuppressWarnings("unchecked")
    public JSONObject createCurrencyDetails(String date,String symbol, HashMap<String, Double> rateDetails, int currencyID, String currencyName){
        JSONObject currencyDetails = new JSONObject();

        JSONArray rateList = new JSONArray();
        for (Object k : rateDetails.keySet()){
            String key = (String) k;

            JSONObject rate = new JSONObject();
            rate.put(key, String.valueOf(rateDetails.get(key)));
            rateList.add(rate);
        }

        currencyDetails.put("name", currencyName);
        currencyDetails.put("symbol", symbol);
        currencyDetails.put("rate", rateList);

        JSONObject curObj = new JSONObject();
        String tmp = "Currency" + String.valueOf(currencyID);
        curObj.put(tmp, currencyDetails);

        return curObj;
    }

    @SuppressWarnings("unchecked")
    public void editCurrencyDetails(String date, String currencyFrom, String currencyTo, double finalRate){
        for (Object o:this.dateList){
            JSONObject obj = (JSONObject) o;
            JSONObject dateObj = (JSONObject) obj.get("Date");
            if (date.equals( (String) dateObj.get("date"))){
                for (int i = 1;i <= Currency.getCurrencyCount();i++){
                    JSONObject curObj = (JSONObject) dateObj.get("Currency" + String.valueOf(i));
                    if (currencyFrom.equals( (String) curObj.get("name"))){
                        JSONArray rateList = (JSONArray) curObj.get("rate");
                        int index = 0;
                        int j = 0;
                        JSONObject sub = new JSONObject();
                        for (Object rate:rateList){
                            JSONObject rateObj = (JSONObject) rate;
                            String key = "";
                            for (Object k:rateObj.keySet()){
                                key = (String) k;
                            }
                            if (currencyTo.equals(key)){
                                index = j;
                                sub.put(currencyTo, String.valueOf(finalRate));
                                break;
                            }
                            j++;
                        }
                        rateList.remove(index);
                        rateList.add(sub);
                        curObj.put("rate", rateList);
                        break;
                    }
                }
                break;
            }
        }

        try{
            FileWriter file = new FileWriter(this.targetFile);
            PrintWriter pw = new PrintWriter(file);
            pw.print("");
            pw.close();

            file = new FileWriter(this.targetFile);
            file.write(this.dateList.toJSONString());
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return;
    }

}
