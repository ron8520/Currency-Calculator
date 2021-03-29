package W14B_G4_Assignment1;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment1.model.*;

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

public class JsonWriterTest{
    static JsonWriter writer;

    @BeforeAll
    public static void JsonWriterSetUp(){
        String filename = "src/test/java/W14B_G4_Assignment1/JsonTest.json";
        writer = new JsonWriter(filename);
        CurrencyCopy aud = new CurrencyCopy("AUD", "\u0024");
        CurrencyCopy usd = new CurrencyCopy("USD", "\u0024", 0, 0.72);
    }

    @Test
    public void testJsonWriterConstruction(){
        assertNotNull(writer);
    }

    @Test
    public void testCreateCurrencyDetails(){
        String date = "2020-10-01";
        HashMap <String,Double> rateDetails = new HashMap<>();
        rateDetails.put("USD",0.72);
        rateDetails.put("AUD", 10.0);
        CurrencyCopy currency = new CurrencyCopy("JPY","\u00A5", 0, 10.0);
        JSONObject obj = writer.createCurrencyDetails(date, "\u00A5", rateDetails, 3, "JPY");
        assertNotNull(obj);
    }

    @Test
    public void testWriteCurrency(){
        String date = "2020-10-01";
        HashMap <String,Double> rateDetails = new HashMap<>();
        rateDetails.put("USD",0.72);
        rateDetails.put("AUD", 10.0);
        CurrencyCopy currency = new CurrencyCopy("JPY","\u00A5", 0, 10.0);
        JSONObject obj = writer.createCurrencyDetails(date, "\u00A5", rateDetails, 3, "JPY");
        assertNotNull(obj);
        writer.writeCurrency(date, "\u00A5", rateDetails, 3, "JPY");
    }

    @Test
    public void testeditCurrencyDetail(){
        String date = "2020-10-01";
        String from = "AUD";
        String to = "USD";
        double rate = 0.73;
        writer.editCurrencyDetails(date,from, to, rate);
    }
}
