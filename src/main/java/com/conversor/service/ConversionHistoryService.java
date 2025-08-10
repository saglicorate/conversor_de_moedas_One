package com.conversor.service;

import com.conversor.model.CurrencyConversion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar o histórico de conversões
 */
public class ConversionHistoryService {
    
    private final List<CurrencyConversion> conversionHistory;
    
    public ConversionHistoryService() {
        this.conversionHistory = new ArrayList<>();
    }
    
    /**
     * Adiciona uma nova conversão ao histórico
     * @param conversion conversão a ser adicionada
     */
    public void addConversion(CurrencyConversion conversion) {
        conversionHistory.add(conversion);
    }
    
    /**
     * Retorna todo o histórico de conversões
     * @return lista de todas as conversões
     */
    public List<CurrencyConversion> getAllConversions() {
        return new ArrayList<>(conversionHistory);
    }
    
    /**
     * Retorna as últimas N conversões
     * @param limit número máximo de conversões a retornar
     * @return lista das últimas conversões
     */
    public List<CurrencyConversion> getRecentConversions(int limit) {
        return conversionHistory.stream()
                .sorted((c1, c2) -> c2.getTimestamp().compareTo(c1.getTimestamp()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * Retorna conversões de uma moeda específica
     * @param currencyCode código da moeda
     * @return lista de conversões da moeda especificada
     */
    public List<CurrencyConversion> getConversionsByCurrency(String currencyCode) {
        return conversionHistory.stream()
                .filter(c -> c.getFromCurrency().equals(currencyCode) || 
                           c.getToCurrency().equals(currencyCode))
                .collect(Collectors.toList());
    }
    
    /**
     * Retorna o total de conversões realizadas
     * @return número total de conversões
     */
    public int getTotalConversions() {
        return conversionHistory.size();
    }
    
    /**
     * Limpa todo o histórico
     */
    public void clearHistory() {
        conversionHistory.clear();
    }
    
    /**
     * Retorna o histórico formatado para exibição
     * @return string formatada com o histórico
     */
    public String getFormattedHistory() {
        if (conversionHistory.isEmpty()) {
            return "Nenhuma conversão realizada ainda.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== HISTÓRICO DE CONVERSÕES ===\n");
        
        List<CurrencyConversion> recentConversions = getRecentConversions(10);
        for (int i = 0; i < recentConversions.size(); i++) {
            CurrencyConversion conversion = recentConversions.get(i);
            sb.append(String.format("%d. %s\n", i + 1, conversion.toString()));
        }
        
        if (conversionHistory.size() > 10) {
            sb.append(String.format("\n... e mais %d conversões anteriores.\n", 
                                 conversionHistory.size() - 10));
        }
        
        return sb.toString();
    }
    
    /**
     * Retorna estatísticas das conversões
     * @return string com estatísticas formatadas
     */
    public String getStatistics() {
        if (conversionHistory.isEmpty()) {
            return "Nenhuma conversão para gerar estatísticas.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTATÍSTICAS ===\n");
        sb.append(String.format("Total de conversões: %d\n", getTotalConversions()));
        
        // Moeda mais convertida
        String mostConvertedCurrency = conversionHistory.stream()
                .flatMap(c -> List.of(c.getFromCurrency(), c.getToCurrency()).stream())
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet().stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(e -> e.getKey())
                .orElse("N/A");
        
        sb.append(String.format("Moeda mais utilizada: %s\n", mostConvertedCurrency));
        
        // Valor total convertido
        double totalAmount = conversionHistory.stream()
                .mapToDouble(CurrencyConversion::getAmount)
                .sum();
        
        sb.append(String.format("Valor total convertido: %.2f\n", totalAmount));
        
        return sb.toString();
    }
}
