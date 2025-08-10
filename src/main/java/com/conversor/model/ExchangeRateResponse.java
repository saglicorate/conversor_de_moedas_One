package com.conversor.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Classe que representa a resposta da API ExchangeRate-API
 */
public class ExchangeRateResponse {
    
    @SerializedName("base_code")
    private String baseCode;
    
    @SerializedName("conversion_rates")
    private Map<String, Double> conversionRates;
    
    @SerializedName("time_last_update_utc")
    private String timeLastUpdateUtc;
    
    @SerializedName("time_next_update_utc")
    private String timeNextUpdateUtc;
    
    // Construtores
    public ExchangeRateResponse() {}
    
    public ExchangeRateResponse(String baseCode, Map<String, Double> conversionRates) {
        this.baseCode = baseCode;
        this.conversionRates = conversionRates;
    }
    
    // Getters e Setters
    public String getBaseCode() {
        return baseCode;
    }
    
    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }
    
    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }
    
    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }
    
    public String getTimeLastUpdateUtc() {
        return timeLastUpdateUtc;
    }
    
    public void setTimeLastUpdateUtc(String timeLastUpdateUtc) {
        this.timeLastUpdateUtc = timeLastUpdateUtc;
    }
    
    public String getTimeNextUpdateUtc() {
        return timeNextUpdateUtc;
    }
    
    public void setTimeNextUpdateUtc(String timeNextUpdateUtc) {
        this.timeNextUpdateUtc = timeNextUpdateUtc;
    }
    
    /**
     * Obtém a taxa de conversão para uma moeda específica
     * @param currencyCode código da moeda (ex: "BRL", "EUR")
     * @return taxa de conversão ou null se não encontrada
     */
    public Double getConversionRate(String currencyCode) {
        return conversionRates != null ? conversionRates.get(currencyCode) : null;
    }
    
    @Override
    public String toString() {
        return "ExchangeRateResponse{" +
                "baseCode='" + baseCode + '\'' +
                ", conversionRates=" + conversionRates +
                ", timeLastUpdateUtc='" + timeLastUpdateUtc + '\'' +
                ", timeNextUpdateUtc='" + timeNextUpdateUtc + '\'' +
                '}';
    }
}
