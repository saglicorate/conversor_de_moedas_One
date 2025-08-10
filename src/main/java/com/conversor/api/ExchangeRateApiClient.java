package com.conversor.api;

import com.conversor.model.ExchangeRateResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Cliente para a API ExchangeRate-API
 */
public class ExchangeRateApiClient {
    
    private static final String API_KEY = "b2631255ac4cefb5334dea2f";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private static final String ENDPOINT = "/latest/";
    
    private final Gson gson;
    
    public ExchangeRateApiClient() {
        this.gson = new Gson();
    }
    
    /**
     * Obtém as taxas de câmbio para uma moeda base
     * @param baseCurrency código da moeda base (ex: "USD", "EUR")
     * @return resposta da API com as taxas de conversão
     * @throws IOException se houver erro na conexão
     * @throws ApiException se houver erro na API
     */
    public ExchangeRateResponse getExchangeRates(String baseCurrency) throws IOException, ApiException {
        String urlString = BASE_URL + API_KEY + ENDPOINT + baseCurrency;
        
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = readResponse(connection);
                return parseResponse(response);
            } else {
                String errorResponse = readErrorResponse(connection);
                throw new ApiException("Erro na API: " + responseCode + " - " + errorResponse);
            }
            
        } catch (IOException e) {
            throw new IOException("Erro ao conectar com a API: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lê a resposta de sucesso da API
     */
    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
    
    /**
     * Lê a resposta de erro da API
     */
    private String readErrorResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
    
    /**
     * Faz o parse da resposta JSON da API
     */
    private ExchangeRateResponse parseResponse(String jsonResponse) throws ApiException {
        try {
            return gson.fromJson(jsonResponse, ExchangeRateResponse.class);
        } catch (JsonSyntaxException e) {
            throw new ApiException("Erro ao fazer parse da resposta JSON: " + e.getMessage());
        }
    }
    
    /**
     * Exceção personalizada para erros da API
     */
    public static class ApiException extends Exception {
        public ApiException(String message) {
            super(message);
        }
        
        public ApiException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
