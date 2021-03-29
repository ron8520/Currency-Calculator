package W14B_G4_Assignment1;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment1.model.Calculator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CalculatorTest{
    Calculator c = new Calculator("2020-10-01", "2020-10-05", "USD", "INR");

    @Test
    public void testCalculatorConstruction(){
        assertNotNull(c);
    }

    @Test
    public void testAverage(){
        assertEquals(73.28, c.average());
    }

    @Test
    public void testMedian(){
        assertEquals(73.31, c.median());
    }

    @Test
    public void testMaximum(){
        assertEquals(73.37, c.maximum());
    }

    @Test
    public void testMinimum(){
        assertEquals(73.17, c.minimum());
    }

    @Test
    public void testStandardDeviation(){
        assertEquals(0.08, c.stdDeviation());
    }
}
