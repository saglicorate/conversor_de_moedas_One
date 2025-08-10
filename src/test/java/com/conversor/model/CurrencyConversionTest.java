package com.conversor.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class CurrencyConversionTest {
    
    private CurrencyConversion conversion;
    
    @BeforeEach
    void setUp() {
        conversion = new CurrencyConversion("USD", "BRL", 100.0, 486.65, 4.8665);
    }
    
    @Test
    void testConstructor() {
        assertNotNull(conversion);
        assertEquals("USD", conversion.getFromCurrency());
        assertEquals("BRL", conversion.getToCurrency());
        assertEquals(100.0, conversion.getAmount());
        assertEquals(486.65, conversion.getConvertedAmount());
        assertEquals(4.8665, conversion.getExchangeRate());
        assertNotNull(conversion.getTimestamp());
    }
    
    @Test
    void testGettersAndSetters() {
        conversion.setFromCurrency("EUR");
        assertEquals("EUR", conversion.getFromCurrency());
        
        conversion.setToCurrency("USD");
        assertEquals("USD", conversion.getToCurrency());
        
        conversion.setAmount(200.0);
        assertEquals(200.0, conversion.getAmount());
        
        conversion.setConvertedAmount(183.2);
        assertEquals(183.2, conversion.getConvertedAmount());
        
        conversion.setExchangeRate(0.916);
        assertEquals(0.916, conversion.getExchangeRate());
        
        LocalDateTime newTimestamp = LocalDateTime.now();
        conversion.setTimestamp(newTimestamp);
        assertEquals(newTimestamp, conversion.getTimestamp());
    }
    
    @Test
    void testGetFormattedConversion() {
        String formatted = conversion.getFormattedConversion();
        assertTrue(formatted.contains("100.00 USD"));
        assertTrue(formatted.contains("486.65 BRL"));
        assertTrue(formatted.contains("4.8665"));
    }
    
    @Test
    void testGetFormattedTimestamp() {
        String formatted = conversion.getFormattedTimestamp();
        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}"));
    }
    
    @Test
    void testToString() {
        String result = conversion.toString();
        assertTrue(result.contains("USD → BRL"));
        assertTrue(result.contains("100.00 USD"));
        assertTrue(result.contains("486.65 BRL"));
        assertTrue(result.contains("4.8665"));
        assertTrue(result.contains("Taxa:"));
    }
    
    @Test
    void testTimestampIsSetOnCreation() {
        LocalDateTime beforeCreation = LocalDateTime.now();
        CurrencyConversion newConversion = new CurrencyConversion("EUR", "USD", 50.0, 54.55, 1.091);
        LocalDateTime afterCreation = LocalDateTime.now();
        
        assertTrue(newConversion.getTimestamp().isAfter(beforeCreation) || 
                  newConversion.getTimestamp().isEqual(beforeCreation));
        assertTrue(newConversion.getTimestamp().isBefore(afterCreation) || 
                  newConversion.getTimestamp().isEqual(afterCreation));
    }
}
