package com.leorodriguezs.conversordemonedas;

import com.leorodriguezs.conversordemonedas.api.CurrencyApiClient;

import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CurrencyApiClient currencyApiClient = new CurrencyApiClient();
        double resultado;
        int opcion = 1;

        System.out.println("Welcome to the Currency Converter!");
        System.out.println("Loading available currencies...");

        while (opcion == 1) {
            // Obtener las monedas válidas desde la API cada vez
            Set<String> validCurrencies = currencyApiClient.getValidCurrencies();

            if (validCurrencies.isEmpty()) {
                System.out.println("Error: Could not retrieve available currencies from API.");
                System.out.println("Please check your internet connection and API key.");
                break;
            }

            System.out.println("Available currencies: " + validCurrencies);

            System.out.print("Enter the local currency: ");
            String localCurrency = scanner.nextLine().toUpperCase();

            if (!validCurrencies.contains(localCurrency)) {
                System.out.println("Invalid local currency code. Please try again.");
                continue;
            }

            System.out.print("Enter the destination currency: ");
            String destinationCurrency = scanner.nextLine().toUpperCase();

            if (!validCurrencies.contains(destinationCurrency)) {
                System.out.println("Invalid destination currency code. Please try again.");
                continue;
            }

            System.out.print("Enter the amount: ");
            String amountInput = scanner.nextLine().trim().replace(",", ".");
            double amount;

            try {
                amount = Double.parseDouble(amountInput);
                if (amount <= 0) {
                    System.out.println("Please enter a positive amount.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a number like 1234.56");
                continue;
            }

            resultado = currencyApiClient.convertCurrencies(localCurrency, destinationCurrency, amount);

            if (resultado == -1) {
                System.out.println("Conversion failed. Please try again.");
            } else {
                System.out.printf("%.2f %s = %.2f %s%n",
                        amount, localCurrency, resultado, destinationCurrency);
            }

            System.out.print("Do you want to perform another conversion? (1 = Yes, 0 = No): ");
            String input = scanner.nextLine().trim();

            try {
                opcion = Integer.parseInt(input);
                if (opcion != 0 && opcion != 1) {
                    System.out.println("Invalid option. Please enter 1 for Yes or 0 for No.");
                    opcion = 1; // Continue the loop
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Exiting.");
                break;
            }

            System.out.println();
        }

        scanner.close();
        System.out.println("Thank you for using the Currency Converter!");
    }
}