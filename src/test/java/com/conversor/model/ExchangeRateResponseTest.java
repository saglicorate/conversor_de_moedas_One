package com.conversor.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class ExchangeRateResponseTest {
    
    private ExchangeRateResponse response;
    private Map<String, Double> conversionRates;
    
    @BeforeEach
    void setUp() {
        conversionRates = new HashMap<>();
        conversionRates.put("BRL", 4.8665);
        conversionRates.put("EUR", 0.916);
        conversionRates.put("GBP", 0.789);
        
        response = new ExchangeRateResponse("USD", conversionRates);
        response.setTimeLastUpdateUtc("2024-01-01 12:00:00");
        response.setTimeNextUpdateUtc("2024-01-01 18:00:00");
    }
    
    @Test
    void testConstructor() {
        assertNotNull(response);
        assertEquals("USD", response.getBaseCode());
        assertEquals(conversionRates, response.getConversionRates());
    }
    
    @Test
    void testDefaultConstructor() {
        ExchangeRateResponse defaultResponse = new ExchangeRateResponse();
        assertNotNull(defaultResponse);
        assertNull(defaultResponse.getBaseCode());
        assertNull(defaultResponse.getConversionRates());
    }
    
    @Test
    void testGettersAndSetters() {
        response.setBaseCode("EUR");
        assertEquals("EUR", response.getBaseCode());
        
        Map<String, Double> newRates = new HashMap<>();
        newRates.put("USD", 1.091);
        response.setConversionRates(newRates);
        assertEquals(newRates, response.getConversionRates());
        
        response.setTimeLastUpdateUtc("2024-01-02 12:00:00");
        assertEquals("2024-01-02 12:00:00", response.getTimeLastUpdateUtc());
        
        response.setTimeNextUpdateUtc("2024-01-02 18:00:00");
        assertEquals("2024-01-02 18:00:00", response.getTimeNextUpdateUtc());
    }
    
    @Test
    void testGetConversionRate() {
        assertEquals(4.8665, response.getConversionRate("BRL"));
        assertEquals(0.916, response.getConversionRate("EUR"));
        assertEquals(0.789, response.getConversionRate("GBP"));
        assertNull(response.getConversionRate("INVALID"));
    }
    
    @Test
    void testGetConversionRateWithNullRates() {
        ExchangeRateResponse nullResponse = new ExchangeRateResponse();
        assertNull(nullResponse.getConversionRate("USD"));
    }
    
    @Test
    void testToString() {
        String result = response.toString();
        assertTrue(result.contains("USD"));
        assertTrue(result.contains("4.8665"));
        assertTrue(result.contains("0.916"));
        assertTrue(result.contains("0.789"));
    }
}
