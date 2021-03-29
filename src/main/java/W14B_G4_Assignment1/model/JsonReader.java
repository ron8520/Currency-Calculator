package W14B_G4_Assignment1.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {

    private JSONArray dateList;

    @SuppressWarnings("unchecked")
    public JsonReader(String filename){

        JSONParser jsonParser = new JSONParser();
        try {

            FileReader reader = new FileReader(filename);
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            //dateList = (JSONArray) obj;
            dateList = (JSONArray) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public List<String> getDate() {  // param should be the structure of "yyyy-mm-dd"

        List<String> dates = new ArrayList<>();
        // forEach loop iterates over date array
        for (Object date : dateList) {

            JSONObject dateObject = (JSONObject) date;
            dateObject = (JSONObject) dateObject.get("Date");

            // get date
            String dateStr = (String) dateObject.get("date");
            dates.add(dateStr);
        }
        return dates;
    }


    public HashMap< String, HashMap<String, Double> > getDateMap(String dateTime){  // param should be the structure of "yyyy-mm-dd"

        HashMap< String, HashMap<String, Double> > currencyMap = new HashMap<>();
        // forEach loop iterates over date array
        for(Object date: dateList){

            JSONObject dateObject = (JSONObject) date;
            dateObject = (JSONObject) dateObject.get("Date");

            // get date
            String dateStr = (String) dateObject.get("date");

            if(!dateStr.equals(dateTime)){
                continue;
            }

            // get currency name
            for (int i = 1;i <= 6;i++){
                String tmp = "Currency" + String.valueOf(i);
                JSONObject curObj = (JSONObject) dateObject.get(tmp);
                String currencyName = (String) curObj.get("name");
                JSONArray rates = (JSONArray) curObj.get("rate");

                HashMap<String, Double> inner = new HashMap<>();
                for(Object rate: rates){
                    JSONObject rateObj = (JSONObject) rate;

                    String keyStr = "";
                    for (Object key:rateObj.keySet()){
                        keyStr = (String) key;
                    }

                    Double rateNum = Double.parseDouble((String) rateObj.get(keyStr));
                    inner.put(keyStr, rateNum);
                }
                currencyMap.put(currencyName, inner);
            }
            break;
        }
        return currencyMap;

    }
    public String getSymbol(String dateTime, String name){
        String symbol = "";
        for(Object date: dateList) {

            JSONObject dateObject = (JSONObject) date;
            dateObject = (JSONObject) dateObject.get("Date");

            // get date
            String dateStr = (String) dateObject.get("date");

            if (!dateStr.equals(dateTime)) {
                continue;
            }

            // get currency name
            for (int i = 1; i <= 6; i++) {
                String tmp = "Currency" + String.valueOf(i);
                JSONObject curObj = (JSONObject) dateObject.get(tmp);
                String currencyName = (String) curObj.get("name");
                if (!currencyName.equals(name)) {
                    continue;
                }
                symbol = (String) curObj.get("symbol");
            }
        }
        return symbol;
    }

}
