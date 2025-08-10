package com.conversor.service;

import com.conversor.api.ExchangeRateApiClient;
import com.conversor.api.ExchangeRateApiClient.ApiException;
import com.conversor.model.CurrencyConversion;
import com.conversor.model.ExchangeRateResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Serviço principal para conversão de moedas
 */
public class CurrencyConverterService {
    
    private final ExchangeRateApiClient apiClient;
    private final ConversionHistoryService historyService;
    
    // Mapa de opções de conversão pré-definidas
    private final Map<Integer, ConversionOption> conversionOptions;
    
    public CurrencyConverterService() {
        this.apiClient = new ExchangeRateApiClient();
        this.historyService = new ConversionHistoryService();
        this.conversionOptions = initializeConversionOptions();
    }
    
    /**
     * Inicializa as opções de conversão disponíveis
     */
    private Map<Integer, ConversionOption> initializeConversionOptions() {
        Map<Integer, ConversionOption> options = new HashMap<>();
        
        options.put(1, new ConversionOption("USD", "BRL", "Dólar Americano → Real Brasileiro"));
        options.put(2, new ConversionOption("EUR", "BRL", "Euro → Real Brasileiro"));
        options.put(3, new ConversionOption("BRL", "USD", "Real Brasileiro → Dólar Americano"));
        options.put(4, new ConversionOption("BRL", "EUR", "Real Brasileiro → Euro"));
        options.put(5, new ConversionOption("USD", "EUR", "Dólar Americano → Euro"));
        options.put(6, new ConversionOption("EUR", "USD", "Euro → Dólar Americano"));
        options.put(7, new ConversionOption("GBP", "BRL", "Libra Esterlina → Real Brasileiro"));
        options.put(8, new ConversionOption("JPY", "BRL", "Iene Japonês → Real Brasileiro"));
        
        return options;
    }
    
    /**
     * Retorna as opções de conversão disponíveis
     */
    public Map<Integer, ConversionOption> getConversionOptions() {
        return new HashMap<>(conversionOptions);
    }
    
    /**
     * Realiza uma conversão de moeda
     * @param optionNumber número da opção de conversão
     * @param amount valor a ser convertido
     * @return resultado da conversão
     * @throws IOException se houver erro na conexão
     * @throws ApiException se houver erro na API
     */
    public ConversionResult convertCurrency(int optionNumber, double amount) throws IOException, ApiException {
        ConversionOption option = conversionOptions.get(optionNumber);
        if (option == null) {
            throw new IllegalArgumentException("Opção de conversão inválida: " + optionNumber);
        }
        
        // Obtém as taxas de câmbio da API
        ExchangeRateResponse response = apiClient.getExchangeRates(option.getFromCurrency());
        
        // Obtém a taxa de conversão para a moeda de destino
        Double rate = response.getConversionRate(option.getToCurrency());
        if (rate == null) {
            throw new ApiException("Taxa de conversão não encontrada para " + option.getToCurrency());
        }
        
        // Calcula o valor convertido
        double convertedAmount = amount * rate;
        
        // Cria o objeto de conversão
        CurrencyConversion conversion = new CurrencyConversion(
            option.getFromCurrency(),
            option.getToCurrency(),
            amount,
            convertedAmount,
            rate
        );
        
        // Adiciona ao histórico
        historyService.addConversion(conversion);
        
        return new ConversionResult(conversion, response);
    }
    
    /**
     * Realiza uma conversão personalizada
     * @param fromCurrency moeda de origem
     * @param toCurrency moeda de destino
     * @param amount valor a ser convertido
     * @return resultado da conversão
     * @throws IOException se houver erro na conexão
     * @throws ApiException se houver erro na API
     */
    public ConversionResult convertCustomCurrency(String fromCurrency, String toCurrency, double amount) 
            throws IOException, ApiException {
        
        // Obtém as taxas de câmbio da API
        ExchangeRateResponse response = apiClient.getExchangeRates(fromCurrency);
        
        // Obtém a taxa de conversão para a moeda de destino
        Double rate = response.getConversionRate(toCurrency);
        if (rate == null) {
            throw new ApiException("Taxa de conversão não encontrada para " + toCurrency);
        }
        
        // Calcula o valor convertido
        double convertedAmount = amount * rate;
        
        // Cria o objeto de conversão
        CurrencyConversion conversion = new CurrencyConversion(
            fromCurrency,
            toCurrency,
            amount,
            convertedAmount,
            rate
        );
        
        // Adiciona ao histórico
        historyService.addConversion(conversion);
        
        return new ConversionResult(conversion, response);
    }
    
    /**
     * Retorna o serviço de histórico
     */
    public ConversionHistoryService getHistoryService() {
        return historyService;
    }
    
    /**
     * Classe interna para representar uma opção de conversão
     */
    public static class ConversionOption {
        private final String fromCurrency;
        private final String toCurrency;
        private final String description;
        
        public ConversionOption(String fromCurrency, String toCurrency, String description) {
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.description = description;
        }
        
        public String getFromCurrency() { return fromCurrency; }
        public String getToCurrency() { return toCurrency; }
        public String getDescription() { return description; }
    }
    
    /**
     * Classe interna para representar o resultado de uma conversão
     */
    public static class ConversionResult {
        private final CurrencyConversion conversion;
        private final ExchangeRateResponse apiResponse;
        
        public ConversionResult(CurrencyConversion conversion, ExchangeRateResponse apiResponse) {
            this.conversion = conversion;
            this.apiResponse = apiResponse;
        }
        
        public CurrencyConversion getConversion() { return conversion; }
        public ExchangeRateResponse getApiResponse() { return apiResponse; }
    }
}
