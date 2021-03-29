package W14B_G4_Assignment1;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment1.model.JsonReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JsonReaderTest{
    JsonReader readerTest;

    @BeforeEach
    public void JsonReaderTestSetup(){
        this.readerTest = new JsonReader("src/main/java/W14B_G4_Assignment1/resources/ExchangeRates.json");
    }

    @Test
    public void testJsonReaderConstruction(){
        assertNotNull(this.readerTest);
    }

    @Test
    public void testCurrencyMapNotNull(){
        assertNotNull(this.readerTest.getDateMap("2020-10-01"));
    }

    @Test
    public void testCurrencyMapInnerNotNull(){
        HashMap< String, HashMap<String, Double> > testMap = this.readerTest.getDateMap("2020-10-01");
        assertNotNull(testMap.get("AUD"));
        assertNotNull(testMap.get("USD"));
        assertNotNull(testMap.get("EUR"));
        assertNotNull(testMap.get("GBP"));
        assertNotNull(testMap.get("INR"));
        assertNotNull(testMap.get("CAD"));
    }

    @Test
    public void testConversionRate(){
        HashMap< String, HashMap<String, Double> > testMap = this.readerTest.getDateMap("2020-10-01");
        HashMap<String, Double> AUDmap = testMap.get("AUD");
        assertTrue(0.95 == AUDmap.get("CAD"));
        assertTrue(0.61 == AUDmap.get("EUR"));
        assertTrue(0.56 == AUDmap.get("GBP"));
        assertTrue(52.54 == AUDmap.get("INR"));
        assertTrue(0.72 == AUDmap.get("USD"));
    }
}
