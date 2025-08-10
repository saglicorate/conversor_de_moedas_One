package com.conversor.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa uma conversão de moeda
 */
public class CurrencyConversion {
    
    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double convertedAmount;
    private double exchangeRate;
    private LocalDateTime timestamp;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    public CurrencyConversion(String fromCurrency, String toCurrency, double amount, 
                           double convertedAmount, double exchangeRate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters e Setters
    public String getFromCurrency() {
        return fromCurrency;
    }
    
    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    
    public String getToCurrency() {
        return toCurrency;
    }
    
    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getConvertedAmount() {
        return convertedAmount;
    }
    
    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
    
    public double getExchangeRate() {
        return exchangeRate;
    }
    
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * Retorna a conversão formatada para exibição
     */
    public String getFormattedConversion() {
        return String.format("%.2f %s = %.2f %s (Taxa: %.4f)", 
                           amount, fromCurrency, convertedAmount, toCurrency, exchangeRate);
    }
    
    /**
     * Retorna o timestamp formatado
     */
    public String getFormattedTimestamp() {
        return timestamp.format(FORMATTER);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s → %s: %.2f %s = %.2f %s (Taxa: %.4f)",
                           getFormattedTimestamp(), fromCurrency, toCurrency,
                           amount, fromCurrency, convertedAmount, toCurrency, exchangeRate);
    }
}
