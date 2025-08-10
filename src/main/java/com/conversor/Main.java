package com.conversor;

import com.conversor.service.CurrencyConverterService;
import com.conversor.service.CurrencyConverterService.ConversionResult;
import com.conversor.service.CurrencyConverterService.ConversionOption;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe principal do Conversor de Moedas
 */
public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final CurrencyConverterService converterService = new CurrencyConverterService();
    
    public static void main(String[] args) {
        System.out.println("=== CONVERSOR DE MOEDAS ===");
        System.out.println("Bem-vindo ao conversor de moedas em tempo real!");
        System.out.println("Utilizando a API ExchangeRate-API para cotações atualizadas.\n");
        
        try {
            runMainLoop();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\nObrigado por usar o Conversor de Moedas!");
        }
    }
    
    /**
     * Loop principal da aplicação
     */
    private static void runMainLoop() {
        boolean running = true;
        
        while (running) {
            try {
                displayMainMenu();
                int choice = getUserChoice();
                
                switch (choice) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        performConversion(choice);
                        break;
                    case 9:
                        performCustomConversion();
                        break;
                    case 10:
                        showHistory();
                        break;
                    case 11:
                        showStatistics();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
                
                if (running && choice != 0) {
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Exibe o menu principal
     */
    private static void displayMainMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("Escolha uma opção de conversão:");
        
        Map<Integer, ConversionOption> options = converterService.getConversionOptions();
        for (Map.Entry<Integer, ConversionOption> entry : options.entrySet()) {
            System.out.printf("%d. %s\n", entry.getKey(), entry.getValue().getDescription());
        }
        
        System.out.println("9. Conversão personalizada");
        System.out.println("10. Ver histórico de conversões");
        System.out.println("11. Ver estatísticas");
        System.out.println("0. Sair");
        System.out.print("\nDigite sua escolha: ");
    }
    
    /**
     * Obtém a escolha do usuário
     */
    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, digite um número válido: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consome a quebra de linha
        return choice;
    }
    
    /**
     * Realiza uma conversão pré-definida
     */
    private static void performConversion(int optionNumber) {
        try {
            System.out.print("Digite o valor a ser convertido: ");
            double amount = getAmountFromUser();
            
            System.out.println("\nConectando à API para obter cotações em tempo real...");
            ConversionResult result = converterService.convertCurrency(optionNumber, amount);
            
            displayConversionResult(result);
            
        } catch (IOException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro na conversão: " + e.getMessage());
        }
    }
    
    /**
     * Realiza uma conversão personalizada
     */
    private static void performCustomConversion() {
        try {
            System.out.print("Digite o código da moeda de origem (ex: USD, EUR, BRL): ");
            String fromCurrency = scanner.nextLine().toUpperCase();
            
            System.out.print("Digite o código da moeda de destino (ex: USD, EUR, BRL): ");
            String toCurrency = scanner.nextLine().toUpperCase();
            
            System.out.print("Digite o valor a ser convertido: ");
            double amount = getAmountFromUser();
            
            System.out.println("\nConectando à API para obter cotações em tempo real...");
            ConversionResult result = converterService.convertCustomCurrency(fromCurrency, toCurrency, amount);
            
            displayConversionResult(result);
            
        } catch (IOException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro na conversão: " + e.getMessage());
        }
    }
    
    /**
     * Obtém o valor do usuário
     */
    private static double getAmountFromUser() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Por favor, digite um valor numérico válido: ");
            scanner.next();
        }
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consome a quebra de linha
        return amount;
    }
    
    /**
     * Exibe o resultado da conversão
     */
    private static void displayConversionResult(ConversionResult result) {
        System.out.println("\n=== RESULTADO DA CONVERSÃO ===");
        System.out.println(result.getConversion().getFormattedConversion());
        System.out.println("\nInformações da API:");
        System.out.println("Moeda base: " + result.getApiResponse().getBaseCode());
        System.out.println("Última atualização: " + result.getApiResponse().getTimeLastUpdateUtc());
        System.out.println("Próxima atualização: " + result.getApiResponse().getTimeNextUpdateUtc());
    }
    
    /**
     * Exibe o histórico de conversões
     */
    private static void showHistory() {
        System.out.println("\n" + converterService.getHistoryService().getFormattedHistory());
    }
    
    /**
     * Exibe as estatísticas
     */
    private static void showStatistics() {
        System.out.println("\n" + converterService.getHistoryService().getStatistics());
    }
}
