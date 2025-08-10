package com.conversor.service;

import com.conversor.model.CurrencyConversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ConversionHistoryServiceTest {
    
    private ConversionHistoryService historyService;
    private CurrencyConversion conversion1;
    private CurrencyConversion conversion2;
    private CurrencyConversion conversion3;
    
    @BeforeEach
    void setUp() {
        historyService = new ConversionHistoryService();
        
        conversion1 = new CurrencyConversion("USD", "BRL", 100.0, 486.65, 4.8665);
        conversion2 = new CurrencyConversion("EUR", "BRL", 50.0, 245.80, 4.916);
        conversion3 = new CurrencyConversion("BRL", "USD", 200.0, 41.10, 0.2055);
    }
    
    @Test
    void testAddConversion() {
        assertEquals(0, historyService.getTotalConversions());
        
        historyService.addConversion(conversion1);
        assertEquals(1, historyService.getTotalConversions());
        
        historyService.addConversion(conversion2);
        assertEquals(2, historyService.getTotalConversions());
    }
    
    @Test
    void testGetAllConversions() {
        historyService.addConversion(conversion1);
        historyService.addConversion(conversion2);
        
        List<CurrencyConversion> allConversions = historyService.getAllConversions();
        assertEquals(2, allConversions.size());
        assertTrue(allConversions.contains(conversion1));
        assertTrue(allConversions.contains(conversion2));
    }
    
    @Test
    void testGetRecentConversions() {
        historyService.addConversion(conversion1);
        historyService.addConversion(conversion2);
        historyService.addConversion(conversion3);
        
        List<CurrencyConversion> recentConversions = historyService.getRecentConversions(2);
        assertEquals(2, recentConversions.size());
        
        // As conversões mais recentes devem vir primeiro
        assertEquals(conversion3, recentConversions.get(0));
        assertEquals(conversion2, recentConversions.get(1));
    }
    
    @Test
    void testGetConversionsByCurrency() {
        historyService.addConversion(conversion1); // USD → BRL
        historyService.addConversion(conversion2); // EUR → BRL
        historyService.addConversion(conversion3); // BRL → USD
        
        List<CurrencyConversion> usdConversions = historyService.getConversionsByCurrency("USD");
        assertEquals(2, usdConversions.size());
        
        List<CurrencyConversion> brlConversions = historyService.getConversionsByCurrency("BRL");
        assertEquals(3, brlConversions.size());
        
        List<CurrencyConversion> eurConversions = historyService.getConversionsByCurrency("EUR");
        assertEquals(1, eurConversions.size());
    }
    
    @Test
    void testGetTotalConversions() {
        assertEquals(0, historyService.getTotalConversions());
        
        historyService.addConversion(conversion1);
        assertEquals(1, historyService.getTotalConversions());
        
        historyService.addConversion(conversion2);
        assertEquals(2, historyService.getTotalConversions());
    }
    
    @Test
    void testClearHistory() {
        historyService.addConversion(conversion1);
        historyService.addConversion(conversion2);
        assertEquals(2, historyService.getTotalConversions());
        
        historyService.clearHistory();
        assertEquals(0, historyService.getTotalConversions());
        assertTrue(historyService.getAllConversions().isEmpty());
    }
    
    @Test
    void testGetFormattedHistoryEmpty() {
        String formatted = historyService.getFormattedHistory();
        assertEquals("Nenhuma conversão realizada ainda.", formatted);
    }
    
    @Test
    void testGetFormattedHistoryWithConversions() {
        historyService.addConversion(conversion1);
        historyService.addConversion(conversion2);
        
        String formatted = historyService.getFormattedHistory();
        assertTrue(formatted.contains("=== HISTÓRICO DE CONVERSÕES ==="));
        assertTrue(formatted.contains("1. "));
        assertTrue(formatted.contains("2. "));
        assertTrue(formatted.contains("USD → BRL"));
        assertTrue(formatted.contains("EUR → BRL"));
    }
    
    @Test
    void testGetStatisticsEmpty() {
        String statistics = historyService.getStatistics();
        assertEquals("Nenhuma conversão para gerar estatísticas.", statistics);
    }
    
    @Test
    void testGetStatisticsWithConversions() {
        historyService.addConversion(conversion1); // 100.0
        historyService.addConversion(conversion2); // 50.0
        historyService.addConversion(conversion3); // 200.0
        
        String statistics = historyService.getStatistics();
        assertTrue(statistics.contains("=== ESTATÍSTICAS ==="));
        assertTrue(statistics.contains("Total de conversões: 3"));
        assertTrue(statistics.contains("Valor total convertido: 350.00"));
        assertTrue(statistics.contains("Moeda mais utilizada:"));
    }
    
    @Test
    void testGetRecentConversionsWithLimit() {
        historyService.addConversion(conversion1);
        historyService.addConversion(conversion2);
        
        List<CurrencyConversion> recentConversions = historyService.getRecentConversions(5);
        assertEquals(2, recentConversions.size());
        
        List<CurrencyConversion> limitedConversions = historyService.getRecentConversions(1);
        assertEquals(1, limitedConversions.size());
    }
}
